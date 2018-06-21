package com.example.timechart.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathUtilsTest {

    @Test
    public void calcMedianExtreme() {
        assertEquals(0, MathUtils.calcMedian(null));
        assertEquals(0, MathUtils.calcMedian(new int[]{}));
        assertEquals(1, MathUtils.calcMedian(new int[]{1}));
    }

    @Test
    public void calcMedianEven() {
        int[] values = new int[] {1, 1, 4, 6, 8, 10};

        assertEquals(5, MathUtils.calcMedian(values));
    }

    @Test
    public void calcMedianOdd() {
        int[] values = new int[] {1, 1, 4, 6, 8, 10, 11};

        assertEquals(6, MathUtils.calcMedian(values));
    }

    @Test
    public void calcInterquartileRangeExtreme() {
        assertEquals(0, MathUtils.calcInterquartileRange(null));
        assertEquals(0, MathUtils.calcInterquartileRange(new int[]{}));
        assertEquals(0, MathUtils.calcInterquartileRange(new int[]{1}));
    }

    @Test
    public void calcInterquartileRangeEven() {
        int[] values = new int[] {1, 1, 4, 8, 10, 11};

        assertEquals(9, MathUtils.calcInterquartileRange(values));
    }

    @Test
    public void calcInterquartileRangeOdd() {
        int[] values = new int[] {1, 2, 4, 6, 8, 10, 11};

        assertEquals(8, MathUtils.calcInterquartileRange(values));
    }
}