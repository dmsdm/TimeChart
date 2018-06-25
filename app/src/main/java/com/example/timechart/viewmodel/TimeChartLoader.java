package com.example.timechart.viewmodel;

import android.os.AsyncTask;

import com.example.timechart.entity.Statistics;
import com.example.timechart.entity.TimeUnit;
import com.example.timechart.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TimeChartLoader extends AsyncTask<Long, Void, TimeChartLoader.Result> {

    public static class Result {
        public List<TimeUnit> timeUnits;
        public Statistics statistics;
    }

    private final OnLoadFinishedListener listener;

    public TimeChartLoader(OnLoadFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Result doInBackground(Long... time) {
        Result result = new Result();
        result.timeUnits = loadTimeUnits(time[0], time[1]);
        result.statistics = calcStatistics(result.timeUnits);
        return result;
    }

    private List<TimeUnit> loadTimeUnits(long startTime, long endTime) {
        List<TimeUnit> timeUnits = new ArrayList<>();
        if (endTime > startTime) {
            long delta = (endTime - startTime) / 15;
            Random rand = new Random(100);
            for (long i = startTime; i <= endTime; i += delta) {
                timeUnits.add(new TimeUnit(i, rand.nextInt(100)));
            }
        }
        return timeUnits;
    }

    private Statistics calcStatistics(List<TimeUnit> timeUnits) {
        int size = timeUnits.size();
        int[] values = new int[size];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, sum = 0;
        for (int i = 0; i<size; ++i) {
            values[i] = timeUnits.get(i).getValue();
            min = Math.min(min, values[i]);
            max = Math.max(max, values[i]);
            sum += values[i];
        }
        Arrays.sort(values);
        Statistics stat = new Statistics();
        stat.minValue = min == Integer.MAX_VALUE ? 0 : min;
        stat.maxValue = max == Integer.MIN_VALUE ? 0 : max;
        stat.avgValue = size > 0 ? sum/size : 0;
        stat.median = MathUtils.calcMedian(values);
        stat.iqRange = MathUtils.calcInterquartileRange(values);
        return stat;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (listener != null) {
            listener.onLoadFinished(result);
        }
    }

    public interface OnLoadFinishedListener {
        void onLoadFinished(Result result);
    }
}