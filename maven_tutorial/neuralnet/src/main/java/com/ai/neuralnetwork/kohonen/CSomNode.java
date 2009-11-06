/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.neuralnetwork.kohonen;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author cjmcmill
 */
public class CSomNode {

    // This node's weights
    private Vector<Double> mWeights;
    // Its position within the lattice
    private double mdX;
    private double mdY;
    // The edges of this node's cell. Each node, when draw to the client
    // area, is represented as a rectangular cell. The color of the cell
    // is set to the RGB value its weights represent.
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    public CSomNode(int left, int top, int right, int bottom, int numWeights) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;

        Random rnd = new Random();

        // initialize the weights to small random variables
        for (int w = 0; w < numWeights; w++) {
            mWeights.add((double) rnd.nextInt(256));
        }

        // calculate the node's center
        mdX = mLeft + (double) (mRight - mLeft) / 2;
        mdY = mTop + (double) (mBottom - mTop) / 2;
    }

    /**
     * Returns the euclidean distance (squared) between the node's weights
     * and the input vector
     * @param inputVector
     * @return
     */
    public double calculateDistance(Vector<Double> inputVector) {
        double distance = 0;
        for (int i = 0; i < mWeights.size(); i++) {
            distance += (inputVector.elementAt(i) - mWeights.elementAt(i)) *
                    (inputVector.elementAt(i) - mWeights.elementAt(i));
        }
        return distance;
    }

    /**
     * Given a learning rate and a target vector this function adjusts
     * the node's weights accordingly
     * @param target
     * @param learningRate
     * @param influence
     */
    public void adjustWeights(Vector<Double> target, double learningRate, double influence) {
        for (int w = 0; w < target.size(); w++) {
            Double currWeight = mWeights.elementAt(w);
            mWeights.setElementAt(currWeight + (learningRate * influence * (target.elementAt(w) - currWeight)), w);
        }
    }

    public double getX() {
        return mdX;
    }

    public double getY() {
        return mdY;
    }
}
