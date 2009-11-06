/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.geneticalgorithm;

import java.util.Stack;

/**
 *
 * @author cjmcmill
 */
public class GAMath
{

    private static final String[] genes =
    {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "+", "-", "*", "/"
    };

    public static int decode(String chromosome)
    {
        Stack<Integer> bits = new Stack<Integer>();
        for (int i = 0; i <= chromosome.length() - 4; i += 4)
        {
            String gene = chromosome.substring(i, i + 4);
            int geneIndex = Integer.parseInt(gene, 2);
            if (geneIndex >= genes.length)
            {
                // skip this "gene" we don"t know what to do with it
                continue;
            }
            else if (geneIndex < 10)
            {
                // Add the number to the stack
                bits.push(Integer.parseInt(genes[geneIndex]));
            }
            else
            {
                // Do simple arithmatic operation
                int a = bits.pop();
                int b = bits.pop();
                if (genes[geneIndex].equals("+"))
                {
                    bits.push(a + b);
                }
                else if (genes[geneIndex].equals("-"))
                {
                    bits.push(a - b);
                }
                else if (genes[geneIndex].equals("*"))
                {
                    bits.push(a * b);
                }
                else if (genes[geneIndex].equals("/"))
                {
                    bits.push(a / b);
                }
            }
        }
        return bits.pop();
    }

    public double getFitness(String chromosome, int target)
    {
        int decodedValue = decode(chromosome);
        if (decodedValue == target)
        {
            return 9999;
        }
        return Math.abs((double) 1 / (target - decodedValue));
    }
}
