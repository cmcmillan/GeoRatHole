package com.saxman.textdiff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.saxman.util.SSString;

public class TextDiffTester2 extends TestCase
{

    public void testBothEmpty()
    {
        TextDiff lTest = new TextDiff();
        Report lReport = runCompare( new String[0], new String[0] );
        lReport.print();
        assertEquals( 0, lReport.size() );
    }

    public void testNewEmpty()
    {
        TextDiff lTest = new TextDiff();
        Report lReport = runCompare( new String[] { "one" }, new String[0] );
        lReport.print();
        assertEquals( 1, lReport.size() );
        assertEquals( "Delete", lReport.getCommand( 0 ).command );
    }

    public void testOldEmpty()
    {
        TextDiff lTest = new TextDiff();
        Report lReport = runCompare( new String[0], new String[] { "one" } );
        lReport.print();
        assertEquals( 1, lReport.size() );
        assertEquals( "Append", lReport.getCommand( 0 ).command );
    }

    public void testMatch()
    {
        Report lReport = runCompare( new String[] { "one", "two", "three", "four" }, new String[] { "one", "two",
                "three", "four" } );
        lReport.print();
        assertEquals( 1, lReport.size() );
        EditCommand lCmd = lReport.getCommand( 0 );
        assertEquals( "Match", lCmd.command );
        assertEquals( 4, lCmd.oldLines.lines.length );
    }

    private Report runCompare(String[] aOld, String[] aNew)
    {
        System.out.println( "\nOld=" + SSString.arrayToString( aOld ) );
        System.out.println( "New=" + SSString.arrayToString( aNew ) );
        return new TextDiff().compare( aOld, aNew );
    }

    public void testChange()
    {
        Report lReport = runCompare( new String[] { "one", }, new String[] { "two", } );
        lReport.print();
        assertEquals( 1, lReport.size() );
        EditCommand lCmd = lReport.getCommand( 0 );
        assertEquals( "Change", lCmd.command );

        lReport = runCompare( new String[] { "one", "two" }, new String[] { "one", "three" } );
        lReport.print();
        assertEquals( 2, lReport.size() );
        lCmd = lReport.getCommand( 0 );
        assertEquals( "Match", lCmd.command );
        lCmd = lReport.getCommand( 1 );
        assertEquals( "Change", lCmd.command );

        lReport = runCompare( new String[] { "one", "two", "three" }, new String[] { "one", "arf", "three" } );
        lReport.print();
        assertEquals( 3, lReport.size() );
        lCmd = lReport.getCommand( 0 );
        assertEquals( "Match", lCmd.command );
        lCmd = lReport.getCommand( 1 );
        assertEquals( "Change", lCmd.command );
        lCmd = lReport.getCommand( 2 );
        assertEquals( "Match", lCmd.command );
    }

    public void testDelete()
    {
        Report lReport = runCompare( new String[] { "one", "two", "three", "four" }, new String[] { "one", "four" } );
        lReport.print();
        assertEquals( 3, lReport.size() );
        assertEquals( "Match", lReport.getCommand( 0 ).command );
        assertEquals( "Delete", lReport.getCommand( 1 ).command );
        assertEquals( "Match", lReport.getCommand( 2 ).command );
    }

    public void testInsert()
    {
        Report lReport = runCompare( new String[] { "one", "four" }, new String[] { "one", "two", "three", "four" } );
        lReport.print();
        assertEquals( 3, lReport.size() );
        assertEquals( "Match", lReport.getCommand( 0 ).command );
        assertEquals( "Insert before", lReport.getCommand( 1 ).command );
        assertEquals( "Match", lReport.getCommand( 2 ).command );
    }

    public void testMove()
    {
        TextDiff lTest = new TextDiff();
        Report lReport = runCompare( new String[] { "a", "b", "c", "d" }, new String[] { "a", "c", "d", "b" } );
        lReport.print();
        assertEquals( 3, lReport.size() );
        assertEquals( "Match", lReport.getCommand( 0 ).command );
        assertEquals( "Move", lReport.getCommand( 1 ).command );
        assertEquals( "Match", lReport.getCommand( 2 ).command );
    }

    public void testMoveAndInsert()
    {
        Report lReport = runCompare( new String[] { "a", "b", "c", "d" }, new String[] { "a", "c", "d", "x", "y", "z",
                "b" } );
        lReport.print();
        assertEquals( 4, lReport.size() );
        assertEquals( "Match", lReport.getCommand( 0 ).command );
        assertEquals( "Move", lReport.getCommand( 1 ).command );
        assertEquals( "Match", lReport.getCommand( 2 ).command );
        assertEquals( "Append", lReport.getCommand( 3 ).command );
    }

    public void testAmbiguous()
    {
        Report lReport = runCompare( new String[] { "a", "b", "a", "b" }, new String[] { "b", "a", "b", "a" } );
        lReport.print();
        assertEquals( 1, lReport.size() );
        assertEquals( "Change", lReport.getCommand( 0 ).command );
    }

    // Reproduced SSWiki defect D2.
    public void testAmbiguousAfterChange()
    {
        Report lReport = runCompare( new String[] { "a", " ", "b", " ", "c", " ", "d" }, new String[] { "a", " ", "b",
                " ", "x", " ", "d" } );
        lReport.print();
        assertEquals( 3, lReport.size() );
        assertEquals( "Match", lReport.getCommand( 0 ).command );
        assertEquals( "Change", lReport.getCommand( 1 ).command );
        assertEquals( "Match", lReport.getCommand( 2 ).command );
    }

    // -------------- Housekeeping -----------
    public TextDiffTester2(java.lang.String testName)
    {
        super( testName );
    }

    public void setUp()
    {
    }

    public static void main(java.lang.String[] args)
    {
        junit.textui.TestRunner.run( suite() );
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite( TextDiffTester2.class );
        return suite;
    }

}
