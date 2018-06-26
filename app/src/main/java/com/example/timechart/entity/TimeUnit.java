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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeUnit) {
            return ((TimeUnit) obj).time == this.time
                    && ((TimeUnit) obj).value == this.value;
        }
        return super.equals(obj);
    }
}