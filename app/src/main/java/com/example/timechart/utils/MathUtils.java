package com.example.timechart.utils;

public class MathUtils {

    public static int calcMedian(int[] values) {
        if (values == null || values.length == 0) return 0;
        return calcMedian(values, 0, values.length);
    }

    private static int calcMedian(int[] values, int start, int end) {
        int size = end - start;
        if (size % 2 == 0) { // even
            return (values[start + size/2-1] + values[start + size/2])/2;
        } else { // odd
            return values[start + size/2];
        }
    }

    public static int calcInterquartileRange(int[] values) {
        if (values == null || values.length < 2) return 0;
        int size = values.length;
        return calcMedian(values, size/2+size%2, size) - calcMedian(values, 0, size/2);
    }
}