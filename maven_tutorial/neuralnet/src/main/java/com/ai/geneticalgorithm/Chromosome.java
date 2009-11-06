/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.geneticalgorithm;

import com.ball.math.ReversePolishNotation;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

/**
 * Genetic Algorithm Node
 * @author cjmcmill
 */
public class Chromosome
{
    // Static info

    public static final char[] geneTable =
    {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/'
    };
    public static final int chromosomeLength = 10;
    public static final double crossoverRate = .8;
    public static final double mutationRate = .001;
    public static final Random rnd = new Random();
    private static final ReversePolishNotation rpn = new ReversePolishNotation();
    // Instance info
    private StringBuffer chromosome = new StringBuffer(chromosomeLength * 4);
    private StringBuffer decodedChromosome = new StringBuffer(chromosomeLength * 4);
    private double score;
    private int total;
    private boolean valid = false;

    public Chromosome(int target)
    {
        for (int chromoCount = 0; chromoCount < chromosomeLength; chromoCount++)
        {
            // Generate a random binary integer
            String binaryString = Integer.toBinaryString(rnd.nextInt(geneTable.length));
            int fillLen = 4 - binaryString.length();
            // Fill to 4
            for (int i = 0; i < fillLen; i++)
            {
                chromosome.append('0');
            }
            // Append the binary string
            chromosome.append(binaryString);
        }

        // Score the new chromosome
        scoreChromosome(target);
    }

    public Chromosome(StringBuffer chromosome)
    {
        this.chromosome = chromosome;
    }

    /**
     * Decode the chromosome string
     * @return
     */
    public String decodeChromosome()
            throws IllegalArgumentException
    {
        // clear out the decoded chromosome
        decodedChromosome.setLength(0);
        for (int i = 0; i <= chromosome.length() - 4; i += 4)
        {
            String gene = chromosome.substring(i, i + 4);
            int geneIndex = Integer.parseInt(gene, 2);
            if (geneIndex >= geneTable.length)
            {
                // skip this "gene" we don't know what to do with it
                continue;
            }
            else
            {
                decodedChromosome.append(geneTable[geneIndex] + " ");
            }
        }
        if (decodedChromosome.length() == 0)
        {
            throw new IllegalArgumentException("Invalid chromosome: " + chromosome.toString());
        }
        return decodedChromosome.toString();
    }

    private final int addUp()
    {
        String decoded = decodeChromosome();
        return rpn.calculate(decoded);
    }

    /**
     * Score this chromosome
     * @param target Target total
     */
    public void scoreChromosome(int target)
    {
        try
        {
            total = addUp();
            if (total == target)
            {
                score = 0;
            }
            else
            {
                score = Math.abs((double) 1 / (target - total));
            }
            valid = true;
        }
        catch (ArithmeticException ex)
        {
            valid = false;
            //System.out.println("Invalid ArithmeticException");
        }
        catch (IllegalArgumentException ex)
        {
            valid = false;
            //System.out.println("Invalid IllegalArgumentException");
        }
    }

    /**
     * Crossover the bits
     * @param other
     */
    public void crossOver(Chromosome other)
    {
        // Should we cross over?
        if (rnd.nextDouble() > crossoverRate)
        {
            return;
        }

        // Generate a random position
        int pos = rnd.nextInt(chromosome.length());

        // Swap all chars after that position
        for (int i = pos; i < chromosome.length(); i++)
        {
            // Get our character
            char tmp = chromosome.charAt(i);

            // Swap the characters
            chromosome.setCharAt(i, other.chromosome.charAt(i));
            other.chromosome.setCharAt(i, tmp);
        }
    }

    /**
     * Mutation
     */
    public final void mutate()
    {
        for (int i = 0; i < chromosome.length(); i++)
        {
            if (rnd.nextDouble() <= mutationRate)
            {
                chromosome.setCharAt(i, (chromosome.charAt(i) == '0' ? '1' : '0'));
            }
        }
    }

    public double getScore()
    {
        return score;
    }

    public int getTotal()
    {
        return total;
    }

    public boolean isValid()
    {
        return valid;
    }
}
