package com.example.timechart.utils;

public class MathUtils {

    public static int calcMedian(int[] values) {
        int size = values.length;
        if (size % 2 == 0) { // even
            return (values[size/2-1] + values[size/2])/2;
        } else { // odd
            return values[size/2];
        }
    }

    public static int calcInterquartileRange(int[] values) {
        int size = values.length;
        int[] firstHalf = new int[size/2];
        int[] secondHalf = new int[size/2];
        for (int i=0; i<size; ++i) {
            if (i<size/2) {
                firstHalf[i] = values[i];
            } else if (size % 2 != 1 || i != size/2) {
                secondHalf[i-size/2-size%2] = values[i];
            }
        }
        return calcMedian(secondHalf) - calcMedian(firstHalf);
    }
}