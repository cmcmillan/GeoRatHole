/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ball.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Reverse Polish Notation Calculator
 * @author cjmcmill
 */
public class ReversePolishNotation
{

    private Map<String, Integer> operators = new HashMap<String, Integer>();

    public ReversePolishNotation()
    {
        operators.put("+", 2);
        operators.put("-", 2);
        operators.put("*", 2);
        operators.put("/", 2);
    }

    public int calculate(String expression)
            throws ArithmeticException
    {
        Pattern spaces = Pattern.compile("\\s+");

        String[] tokens = spaces.split(expression);
        Stack<Integer> stack = new Stack<Integer>();
        for (int x = 0; x < tokens.length; x++)
        {
            String token = tokens[x];
            if (operators.containsKey(token))
            {
                // Current token is an operator
                if (stack.size() < operators.get(token))
                {
                    throw new ArithmeticException(
                            "Expression has insufficient values to evaluate " +
                            token + " at index " + x + ".\n" + expression);
                }
                int[] inputs = new int[operators.get(token)];
                for (int i = inputs.length - 1; i >= 0; i--)
                {
                    inputs[i] = stack.pop();
                }
                if (token.equals("+"))
                {
                    int val = inputs[0] + inputs[1];
                    stack.push(val);
                }
                if (token.equals("-"))
                {
                    int val = inputs[0] - inputs[1];
                    stack.push(val);
                }
                if (token.equals("*"))
                {
                    int val = inputs[0] * inputs[1];
                    stack.push(val);
                }
                if (token.equals("/"))
                {
                    int val = inputs[0] / inputs[1];
                    stack.push(val);
                }
            }
            else
            {
                try
                {
                    stack.push(Integer.parseInt(token));
                }
                catch (NumberFormatException numberFormatException)
                {
                    System.out.println("Expression: " + expression);
                    numberFormatException.printStackTrace();
                }
            }
        }

        if (stack.size() == 1)
        {
            return stack.pop();
        }
        throw new ArithmeticException("Expression has too many values: " + expression);
    }
}
