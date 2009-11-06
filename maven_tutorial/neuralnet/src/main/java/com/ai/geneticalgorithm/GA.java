/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author cjmcmill
 */
public class GA
{
    
    public static final Random rnd = new Random();
    public static final int poolSize = 40;	// Must be even

    public static int doIt(int target)
    {
        int generation = 0;

        // Create the pools
        List<Chromosome> pool = new ArrayList<Chromosome>();
        List<Chromosome> newPool = new ArrayList<Chromosome>();

        // Generate unique chromosomes in the pool
        for (int i = 0; i < poolSize; i++)
        {
            pool.add(new Chromosome(target));
        }

        // Loop until a solution is found
        while (true)
        {
            // Clear the new pool
            newPool.clear();

            // Add to the generations
            generation++;

            // Loop until the pool has been processed
            for (int i = pool.size() - 1; i >= 0; i -= 2)
            {
                // Select two members
                Chromosome n1 = selectMember(pool);
                Chromosome n2 = selectMember(pool);

                // Cross over and mutate
                n1.crossOver(n2);
                n1.mutate();
                n2.mutate();

                // Rescore the nodes
                n1.scoreChromosome(target);
                n2.scoreChromosome(target);

                // Check to see if either is the solution
                if (n1.getTotal()  == target && n1.isValid())
                {
                    System.out.println("Generations: " + generation +
                            "  Solution: " + n1.decodeChromosome());
                    return generation;
                }
                if (n2.getTotal() == target && n2.isValid())
                {
                    System.out.println("Generations: " + generation +
                            "  Solution: " + n2.decodeChromosome());
                    return generation;
                }

                // Add to the new pool
                newPool.add(n1);
                newPool.add(n2);
            }

            // Add the newPool back to the old pool
            pool.addAll(newPool);
        }
    }

    private static Chromosome selectMember(List<Chromosome> pool)
    {
        // Get the total fitness
        double totalFitness = 0.0;
        for (int i = pool.size() - 1; i >= 0; i--)
        {
            double score = pool.get(i).getScore();
            totalFitness += score;
        }

        double slice = totalFitness * rnd.nextDouble();

        // Loop to find the node
        double ttot = 0.0;
        for (int i = pool.size() - 1; i >= 0; i--)
        {
            Chromosome node = pool.get(i);
            ttot += node.getScore();
            if (ttot >= slice)
            {
                pool.remove(i);
                return node;
            }
        }
        // Get the last node
        return pool.remove(pool.size() - 1);
    }
}
