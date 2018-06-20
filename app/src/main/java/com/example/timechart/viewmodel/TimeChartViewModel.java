package com.example.timechart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.timechart.entity.TimeUnit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TimeChartViewModel extends ViewModel {

    private MutableLiveData<List<TimeUnit>> timeChart = new MutableLiveData<>();
    private MutableLiveData<String> startTime = new MutableLiveData<>();
    private MutableLiveData<String> endTime = new MutableLiveData<>();

    public TimeChartViewModel() {
        String date = new SimpleDateFormat().format(new Date());
        startTime.setValue(date);
        endTime.setValue(date);
    }

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

    public LiveData<String> getStartTime() {
        return startTime;
    }

    public LiveData<String> getEndTime() {
        return endTime;
    }
}