import java.util.jar.*;
import java.util.*;
import java.util.zip.ZipEntry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.FileOutputStream;
/**
 * This class  would extract information 
 * from a Java Archive File and write information
 * about the classes into an Excel Sheet.
 * The key information generated are the following <br>
 * a) The class Name in First Column <br>
 * b) The Class Member Fields in second column <br>
 * c) The Declared Methods in Third column <br>
 * The Java archive file and the Output File Location
 * must be passed in as parameters
 *
 * @author  Elango Sundaram
 * @version 1.0
 */
public class JavaExtract {
    
    /** Creates a new instance of JavaExtract */
    public JavaExtract() {
    }
    /** Name of the input Jar File */
    public String jarFile=null;
    /** Where the ouput XL should be written */
    public String outputFile=null;
    /** The XL Document */
    HSSFWorkbook workbook = new HSSFWorkbook();
    /** The XL Sheet */
    HSSFSheet sheet = null;
    
    /* Creates an instance and Initializes the same  */
    public static void main(String argv[]){
        JavaExtract jE = new JavaExtract();
        // Make sure you pass the
        jE.jarFile= System.getProperty("JAR_FILE");
        jE.outputFile = System.getProperty("OUTPUT_FILE");
        // Make sure that jarFile param is available
        if(jE.jarFile == null){
            System.out.println("The JAR_FILE parameter is not passed as a -DJAR_FILE ..");
        }
        // Make sure that output file name param is available
        if(jE.outputFile == null){
            System.out.println("The OUTPUT_FILE parameter is not passed as a -DOUTPUT_FILE ..");
        }
        // Create the workbook..
        jE.createWorkBookAndSheet();
        // Unzip and start writing
        jE.unZipAndWrite();
        // Create headers in the Excel Book
        jE.headers();
        // Write the XL Document to file..
        jE.writeToFile();
        System.out.println("Done.." );
    }
    
    /**
     * Creates an excel workbook & sheet and does column
     * formatting
     */
    public  void createWorkBookAndSheet(){
        try{
            // Create a New XL Document
            workbook = new HSSFWorkbook();
            // Make a worksheet in the XL document created
            // Give a Name to the worksheet
            sheet = workbook.createSheet("Java Class Info");
            System.out.println("Work Sheet Created " );
            // Set the first three columns with
            //  The width is in units of 1/256th of a character width
            sheet.setColumnWidth((short)0,(short)10000 );
            sheet.setColumnWidth((short)1,(short)10000 );
            sheet.setColumnWidth((short)2,(short)10000 );
        }catch(Exception e) {
            System.out.println("!!FAILURE!! createWorkBookAndSheet() : " + e );
        }
    }
    /**
     * This method will unzip the Zip file
     * and start writing in to excel..
     * The key information gererated are the following
     * a) The class Name in First Column <br>
     * b) The Class Member Fields in second column <br>
     * c) The Declared Methods in Third column <br>
     */
    public void unZipAndWrite(){
        try{
            JarFile jF = new JarFile(jarFile);
            Enumeration enum = jF.entries();
            ZipEntry zN = null;
            String  fN = null;
            String clazName = null;
            String classLoadName = null;
            Class clz = null;
            short rowNum  = 1;
            short cellNum = 0;
            while(enum.hasMoreElements()){
			try
			{
                zN = (ZipEntry)enum.nextElement();
                fN = zN.getName();
                if(fN.indexOf(".class")!= -1){
                    // The class name contains /. Butto load class we need the
                    // path to be separated by dot. So do the replace
                    clazName = fN.replace('/', '.');
                    // To Load class, we dont need the extension.. so
                    // remove the .class from the classname
                    classLoadName = clazName.substring(0,clazName.length() - 6);
                    System.out.println("Process The Class --> " + classLoadName );
                    // Create a Row
                    HSSFRow row = sheet.createRow((short)rowNum);
                    HSSFCell cell = row.createCell((short) cellNum);
                    // Lets make the cell a string type
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    
                    // Write the Name of the Class in the first
                    // column
                    cell.setCellValue(classLoadName);
                    
                    // Begin Wringing the Declared classes
                    // in the second coloumn
                    cell = row.createCell((short) (cellNum+1) );
                    // Load the class..
					clz  = Class.forName(classLoadName, false , this.getClass().getClassLoader());
                    //clz = Class.forName(classLoadName);
                    
                    Field[] fL = clz.getDeclaredFields();
                    // Go Over the Fields and get the Names
                    for(int i=0; i< fL.length;i++){
                        // Keep appending the Name of the Field
                        if(i==0){
                            cell.setCellValue(fL[i].getName());
                        }else {
                            cell.setCellValue(cell.getStringCellValue()+ ", "+ fL[i].getName());
                        }
                    }
                    
                    cell = row.createCell((short) (cellNum+2) );
                    // Lets make the cell a string type
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    
                    Method[] method = clz.getDeclaredMethods();
                    for(int i=0; i< method.length;i++){
                        // Keep appending the Name of the Field
                        if(i==0){
                            cell.setCellValue(method[i].getName());
                        }else {
                            cell.setCellValue(cell.getStringCellValue()+ ", "+ method[i].getName());
                        }
                    }
                    // Go to the next line
                    rowNum++;
                }// close if
                
				}catch (Exception innerEx){
					System.out.println("FAILURE!! unZipAndWrite() .. In proceessing file : " + innerEx );
				}
            }// close while

			
        }catch(Exception e) {
            System.out.println("!!FAILURE!! unZipAndWrite() : " + e );
        }
    }
    
    
    /**
     * Writes headers in the output file
     */
    public  void headers(){
        try{
            HSSFRow row = sheet.createRow((short)0);
            // Set the correct font
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFFont.COLOR_RED);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            // Set the correct Style
            HSSFCellStyle cellStyle= workbook.createCellStyle();
            cellStyle.setFont(font);
            // Set the cell style for Class Name Heading Cell 
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("Class Name ");
            // Set the cell style for Declared Fields Heading Cell 
            cell = row.createCell((short) 1);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("Declared Fields");
             // Set the cell style for Declared Methods Heading Cell 
            cell = row.createCell((short) 2);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("Declared Methods");
        }catch(Exception e) {
            System.out.println("!!FAILURE!! headers() : " + e );
        }
        
    }
    /**
     * Write the created excel document in to a file
     */
    public  void writeToFile(){
        try{
            System.out.println("Write Output" );
            // The Output file is where the xls will be created
            FileOutputStream fOut = new FileOutputStream(outputFile);
            // Write the XL sheet
            workbook.write(fOut);
            fOut.flush();
            // Done Deal..Close it
            fOut.close();
            System.out.println("Done Writing" );
        }catch(Exception e) {
            System.out.println("!!FAILURE!! writeToFile() : " + e );
        }
        
    }
}
