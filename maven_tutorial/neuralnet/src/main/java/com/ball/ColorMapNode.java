/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ball;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author cjmcmill
 */
public class ColorMapNode {

    // This node's weights (i.e. R,G,B values)
    private Vector<Double> weights;
    // Its position within the lattice
    private double dX;
    private double dY;
    // The edges of this node's cell.
    // Each node, when draw to the client area,
    // is represented as a rectangular cell.
    // The color of the cell is set to the RGB value its weights represent.
    private int iLeft;
    private int iTop;
    private int iRight;
    private int iBottom;

    public ColorMapNode(int left, int top, int right, int bottom, int numWeights) {
        this.iLeft = left;
        this.iTop = top;
        this.iRight = right;
        this.iBottom = bottom;

        // initialize the weights to small random variables
        for (int w = 0; w < numWeights; w++) {
            weights.setElementAt(generateColor(), w);
        }

        // Calculate the node's center
        dX = iLeft + (double) (iRight - iLeft) / 2;
        dY = iTop + (double) (iBottom - iTop) / 2;
    }

    private Double generateColor() {
        Random rnd = new Random();
        return (double) rnd.nextInt(256);
    }

    /**
     * Calculate Euclidean distance
     * @param inputVector
     * @return
     */
    public double getDistance(Vector<Double> inputVector) {
        double distance = 0;
        for (int i = 0; i < weights.size(); i++) {
            distance += Math.pow(inputVector.elementAt(i) - weights.elementAt(i), 2);
        }
        return Math.sqrt(distance);
    }
}
