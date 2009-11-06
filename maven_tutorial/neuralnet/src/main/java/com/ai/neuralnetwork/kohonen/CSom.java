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
public class CSom
{

    public static final int constWindowWidth = 400;
    public static final int constWindowHeight = 400;
    public static final int constNumCellsAcross = 40;
    public static final int constNumCellsDown = 40;
    //number of weights each node must contain. One for each element of
    //the input vector. In this example it is 3 because a color is
    //represented by its red, green and blue components. (RGB)
    public static final int constSizeOfInputVector = 3;
    //the number of epochs desired for the training
    public static final int constNumIterations = 1000;
    //the value of the learning rate at the start of training
    public static final double constStartLearningRate = 0.1;
    // The neurons representing the Self Organizing Map
    private Vector<CSomNode> mSOM;
    // This holds the address of the winning node from the current iteration
    private CSomNode mWinningNode;
    // this is the topological 'radius' of the feature map
    private double mMapRadius;
    // Used in the calculation of the neighbourhood width of influence
    private double mTimeConstant;
    // The number of training iterations
    private int mNumIterations;
    // Keeps track of what iteration the epoch method has reached
    private int mIterationCount;
    // The current width of the winning node's area of influence
    private double mNeighbourhoodRadius;
    // How much the learning rate is adjusted for nodes within the AOI
    private double mInfluence;
    private double mLearningRate;
    // Set true when training is finished
    private boolean mDone;
    // The height and width of the cells that the nodes occupy when
    // rendered into 2D space.
    private double mCellWidth;
    private double mCellHeight;

    public CSom()
    {
        mCellWidth = 0;
        mCellHeight = 0;
        mWinningNode = null;
        mIterationCount = 0;
        mNumIterations = 0;
        mTimeConstant = 0;
        mMapRadius = 0;
        mNeighbourhoodRadius = 0;
        mInfluence = 0;
        mLearningRate = constStartLearningRate;
        mDone = false;
    }

    /**
     * Presents an input vector to each node in the network
     * and calculates the Euclidean distance between the vectors for each
     * node. It returns a pointer to the best performer
     * @param vec
     * @return
     */
    private CSomNode findBestMatchingNode(Vector<Double> vec)
    {
        CSomNode winner =null;
        double lowestDistance = 999999;

        for(int n = 0;n<mSOM.size();n++)
        {
            double dist = mSOM.elementAt(n).calculateDistance(vec);
            if(dist < lowestDistance)
            {
                lowestDistance = dist;
                winner = mSOM.elementAt(n);
            }
        }
        return winner;
    }

    private double gaussian(double dist, double sigma)
    {
        return 0.0;
    }

    public void create(int clientX, int clientY, int cellsUp, int cellsAcross, int numIterations)
    {
        mCellWidth = (double) clientX / (double) cellsAcross;
        mCellHeight = (double) clientY / (double) cellsUp;

        mNumIterations = numIterations;

        // Create all the nodes
        for (int row = 0; row < cellsUp; row++)
        {
            for (int col = 0; col < cellsAcross; col++)
            {
                mSOM.add(new CSomNode((int) Math.round(col * mCellWidth), // Left
                        (int) Math.round(row * mCellHeight), // Top
                        (int) Math.round((col + 1) * mCellWidth), // Right
                        (int) Math.round((row + 1) * mCellHeight), // Bottom
                        numIterations));                                 // Num Weights
            }
        }

        // This is the topological 'radius' of the feature map
        mMapRadius = Math.max(constWindowWidth, constWindowHeight) / 2;

        // Used in the calculation of the neighbourhood width of mInfluence
        mTimeConstant = mNumIterations / Math.log(mMapRadius);
    }

    public void Render()
    {
    }

    /**
     * Given a vector of input vectors this method choses one at random
     * and runs the network through one training epoch
     * @param data
     * @return
     */
    public boolean epoch(Vector<Vector<Double>> data)
    {
        // Make sure the size of the input vector matches the size of each node's
        // weight vector
        if (data.elementAt(0).size() != constSizeOfInputVector)
        {
            return false;
        }

        // Return if the training is complete
        if (mDone)
        {
            return true;
        }

        // Enter the training loop
        if (--mNumIterations > 0)
        {
            Random rnd = new Random();
            // The input vectors are presented to the network at random
            int thisVector = rnd.nextInt(data.size() - 1);

            // present the vector to each node and determine the BMU
            mWinningNode = findBestMatchingNode(data.elementAt(thisVector));

            // Calculate the width of the neighbourhood for this timestep
            mNeighbourhoodRadius = mMapRadius * Math.exp(-(double) mIterationCount / mTimeConstant);

            // Now to adjust the weight vector of the BMU and its neighbours

            // For each node, calculate the mInfluence (Theta from equation 6 in
            // the tutorial. If it is greater than zero adjust the node's weights
            // accordingly
            for (int n = 0; n < mSOM.size(); n++)
            {
                // Calculate the Euclidean distance (squared) to this node from
                // the BMU
                double distToNodeSq =
                        (mWinningNode.getX() - mSOM.elementAt(n).getX()) *
                        (mWinningNode.getX() - mSOM.elementAt(n).getX()) +
                        (mWinningNode.getY() - mSOM.elementAt(n).getY()) *
                        (mWinningNode.getY() - mSOM.elementAt(n).getY());

                double widthSq = mNeighbourhoodRadius * mNeighbourhoodRadius;

                // if within the neighbourhood adjust its weights
                if (distToNodeSq < widthSq)
                {
                    // Calculate by how much its weights are adjusted
                    mInfluence = Math.exp(-(distToNodeSq) / (2 * widthSq));

                    mSOM.elementAt(n).adjustWeights(
                            data.elementAt(thisVector),
                            mLearningRate,
                            mInfluence);
                }
            } // next node

            // reduce the learning rate
            mLearningRate = constStartLearningRate * Math.exp(-(double) mIterationCount / mTimeConstant);

            mIterationCount++;
        }
        else
        {
            mDone = true;
        }
        return true;
    }

    public boolean finishedTraining()
    {
        return mDone;
    }
}
