/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ball;

import java.util.Vector;

/**
 *
 * @author cjmcmill
 */
public class ColorMap {

    private int width;
    private int height;
    private ColorMapNode[][] matrix;

    public ColorMap(int w, int h) {
        this.width = w;
        this.height = h;
        matrix = new ColorMapNode[w][h];
        float xStep = 0.5f / (float) width;
        float yStep = 0.5f / (float) height;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                matrix[x][y] = new ColorMapNode(x, y, x + width, y + height, 3);
            }
        }
    }

    // Returns the ColorMapNode at the given point(x,y)
    public ColorMapNode getNode(int x, int y) {
        return matrix[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Finds the best matching unit for the given inputVector
     * @param inputVector
     * @return
     */
    public ColorMapNode getBMU(Vector<Double> inputVector) {
        // Start out assuming that 0,0 is our best matching unit
        ColorMapNode bmu = matrix[0][0];
        double bestDist = bmu.getDistance(inputVector);
        double curDist;

        // Now Step through the entire matrix and check the euclidean distance
        // between the input vector and the given node
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                curDist = matrix[x][y].getDistance(inputVector);
                if(curDist<bestDist)
                {
                    // If the distance from the current node to the input vector
                    // is less than the distance to our current BMU, we have a
                    // new BMU
                    bmu = matrix[x][y];
                    bestDist = curDist;
                }
            }
        }
        return bmu;
    }
}
