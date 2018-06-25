package com.example.timechart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.timechart.entity.Statistics;
import com.example.timechart.entity.TimeUnit;

import java.util.Date;
import java.util.List;

public class TimeChartViewModel extends ViewModel implements TimeChartLoader.OnLoadFinishedListener {

    private static final String TAG = "TimeChartViewModel";
    private MutableLiveData<List<TimeUnit>> timeChart = new MutableLiveData<>();
    private MutableLiveData<Long> startTime = new MutableLiveData<>();
    private MutableLiveData<Long> endTime = new MutableLiveData<>();
    private MutableLiveData<Statistics> statistics = new MutableLiveData<>();

    public TimeChartViewModel() {
        long endDate = new Date().getTime();
        long startDate = endDate - 1000*60*60*24*15;
        startTime.setValue(startDate);
        endTime.setValue(endDate);
    }

    public void load() {
        new TimeChartLoader(this).execute(startTime.getValue(), endTime.getValue());
    }

    @Override
    public void onLoadFinished(TimeChartLoader.Result result) {
        timeChart.setValue(result.timeUnits);
        statistics.setValue(result.statistics);
    }

    public LiveData<List<TimeUnit>> getTimeChart() {
        return timeChart;
    }

    public LiveData<Long> getStartTime() {
        return startTime;
    }

    public LiveData<Long> getEndTime() {
        return endTime;
    }

    public LiveData<Statistics> getCalculatedValues() {
        return statistics;
    }

    public void setStartDate(int year, int month, int dayOfMonth) {
        startTime.setValue(getDateWithSameTime(startTime.getValue(), year, month, dayOfMonth));
    }

    public void setEndDate(int year, int month, int dayOfMonth) {
        endTime.setValue(getDateWithSameTime(endTime.getValue(), year, month, dayOfMonth));
    }

    private long getDateWithSameTime(long dateTime, int year, int month, int dayOfMonth) {
        Date date = new Date(dateTime);
        date.setYear(year-1900);
        date.setMonth(month);
        date.setDate(dayOfMonth);
        return date.getTime();
    }

    public void setStartTime(int hourOfDay, int minute) {
        startTime.setValue(getTimeWithSameDate(startTime.getValue(), hourOfDay, minute));
    }

    public void setEndTime(int hourOfDay, int minute) {
        endTime.setValue(getTimeWithSameDate(endTime.getValue(), hourOfDay, minute));
    }

    private long getTimeWithSameDate(long dateTime, int hourOfDay, int minute) {
        Date date = new Date(dateTime);
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        return date.getTime();
    }
}