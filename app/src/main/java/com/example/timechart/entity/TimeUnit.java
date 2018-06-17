package com.example.timechart.entity;

public class TimeUnit {

    private long time;
    private int value;

    public TimeUnit(long time, int value) {
        this.time = time;
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public int getValue() {
        return value;
    }
}