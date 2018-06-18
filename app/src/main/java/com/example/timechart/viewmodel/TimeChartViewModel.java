package com.example.timechart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.timechart.entity.TimeUnit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TimeChartViewModel extends ViewModel {

    private MutableLiveData<List<TimeUnit>> timeChart = new MutableLiveData<>();

    public void load() {
        List<TimeUnit> list = new ArrayList<>();
        long endTime = new Date().getTime();
        Random rand = new Random(100);
        for (long i = endTime-1000; i <= endTime; i += 100) {
            list.add(new TimeUnit(i, rand.nextInt(100)));
        }
        timeChart.setValue(list);
    }

    public LiveData<List<TimeUnit>> getTimeChart() {
        return timeChart;
    }
}