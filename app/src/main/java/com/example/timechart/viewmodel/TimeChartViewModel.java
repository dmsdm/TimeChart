package com.example.timechart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.timechart.entity.Statistics;
import com.example.timechart.entity.TimeUnit;
import com.example.timechart.utils.MathUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TimeChartViewModel extends ViewModel {

    private static final String TAG = "TimeChartViewModel";
    private MutableLiveData<List<TimeUnit>> timeChart = new MutableLiveData<>();
    private MutableLiveData<String> startTime = new MutableLiveData<>();
    private MutableLiveData<String> endTime = new MutableLiveData<>();
    private MutableLiveData<Statistics> statistics = new MutableLiveData<>();

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
        calcStatistics(list);
    }

    private void calcStatistics(List<TimeUnit> list) {
        int size = list.size();
        int[] values = new int[size];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, sum = 0;
        for (int i = 0; i<size; ++i) {
            values[i] = list.get(i).getValue();
            min = Math.min(min, values[i]);
            max = Math.max(max, values[i]);
            sum += values[i];
        }
        Arrays.sort(values);
        Statistics stat = new Statistics();
        stat.minValue = min;
        stat.maxValue = max;
        stat.avgValue = sum/size;
        stat.median = MathUtils.calcMedian(values);
        stat.iqRange = MathUtils.calcInterquartileRange(values);
        statistics.setValue(stat);
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

    public LiveData<Statistics> getCalculatedValues() {
        return statistics;
    }

    public void setStartDate(int year, int month, int dayOfMonth) {
        startTime.setValue(getDateWithSameTime(startTime.getValue(), year, month, dayOfMonth));
    }

    public void setEndDate(int year, int month, int dayOfMonth) {
        endTime.setValue(getDateWithSameTime(endTime.getValue(), year, month, dayOfMonth));
    }

    private String getDateWithSameTime(String dateTime, int year, int month, int dayOfMonth) {
        Date date;
        try {
            date = new SimpleDateFormat().parse(dateTime);
            date.setYear(year-1900);
            date.setMonth(month);
            date.setDate(dayOfMonth);
        } catch (ParseException ignore) {
            date = new Date(year-1900, month, dayOfMonth);
        }
        return new SimpleDateFormat().format(date);
    }

    public void setStartTime(int hourOfDay, int minute) {
        startTime.setValue(getTimeWithSameDate(startTime.getValue(), hourOfDay, minute));
    }

    public void setEndTime(int hourOfDay, int minute) {
        endTime.setValue(getTimeWithSameDate(endTime.getValue(), hourOfDay, minute));
    }

    private String getTimeWithSameDate(String dateTime, int hourOfDay, int minute) {
        Date date;
        try {
            date = new SimpleDateFormat().parse(dateTime);
        } catch (ParseException ignore) {
            date = new Date();
        }
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        return new SimpleDateFormat().format(date);
    }
}