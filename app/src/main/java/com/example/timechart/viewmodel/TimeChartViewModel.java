package com.example.timechart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.timechart.entity.TimeUnit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeChartViewModel extends ViewModel {

    private MutableLiveData<List<Object>> timeChart = new MutableLiveData<>();

    public void load() {
        List<Object> list = new ArrayList<>();
        list.add(new TimeUnit(new Date().getTime(), 100));
        timeChart.setValue(list);
    }

    public LiveData<List<Object>> getTimeChart() {
        return timeChart;
    }
}