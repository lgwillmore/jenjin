package com.binarymonks.jj.utils;

import java.util.Random;

public class JJMathUtil {

    static Random rand = new Random(System.currentTimeMillis());

    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
