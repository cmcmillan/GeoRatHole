package com.ball;

import com.ai.geneticalgorithm.GA;
import com.ball.math.ReversePolishNotation;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main(String[] args)
    {

//        Stack<Integer> iStack = new Stack<Integer>();
//
//        System.out.println(iStack.size() + " : "+ iStack.empty());
//        iStack.push(5);
//        System.out.println(iStack.size() + " : "+ iStack.empty());
//        iStack.push(5);
//        System.out.println(iStack.size() + " : "+ iStack.empty());
//        iStack.pop();
//        System.out.println(iStack.size() + " : "+ iStack.empty());
//        iStack.push(5);
//        iStack.pop();
//        System.out.println(iStack.size() + " : "+ iStack.empty());
//        iStack.pop();
//        System.out.println(iStack.size() + " : "+ iStack.empty());

        ReversePolishNotation rpn = new ReversePolishNotation();
//        int val = rpn.calculate("2 8 5 9 0 ");
//        System.out.println(val);
        long totalGenerations = 0;
        int iterations = 100;
        int val = 42;//GA.rnd.nextInt(100);
        for (int i = 0; i < iterations; i++)
        {
            // Run the Genetic Algorithm
            totalGenerations += GA.doIt(val);
        }
        System.out.println("Average Generations: " + totalGenerations / iterations);
    }
}
