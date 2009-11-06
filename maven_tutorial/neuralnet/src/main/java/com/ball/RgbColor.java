/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ball;

import java.util.Random;

/**
 *
 * @author cjmcmill
 */
public class RgbColor {

    private int r;
    private int g;
    private int b;

    /**
     * Create a Random RgbColor
     */
    public RgbColor() {
        Random rnd = new Random();

        setR(rnd.nextInt(256));
        setG(rnd.nextInt(256));
        setB(rnd.nextInt(256));
    }

    /**
     * Create a new RgbColor with the provided information
     * @param r
     * @param g
     * @param b
     */
    public RgbColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = validColor(b);
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = validColor(g);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = validColor(r);
    }

    /**
     * Validate colorVal to constrain the value between 0 and 255
     * @param colorVal
     * @return
     */
    private int validColor(int colorVal) {
        if (colorVal > 255) {
            return 255;
        } else if (colorVal < 0) {
            return 0;
        } else {
            return colorVal;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RgbColor other = (RgbColor) obj;
        if (this.r != other.r) {
            return false;
        }
        if (this.g != other.g) {
            return false;
        }
        if (this.b != other.b) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.r;
        hash = 43 * hash + this.g;
        hash = 43 * hash + this.b;
        return hash;
    }
}
