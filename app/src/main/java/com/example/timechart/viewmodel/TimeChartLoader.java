package com.example.timechart.viewmodel;

import android.os.AsyncTask;

import com.example.timechart.entity.TimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimeChartLoader extends AsyncTask<Long, Void, List<TimeUnit>> {

    private final OnLoadFinishedListener listener;

    public TimeChartLoader(OnLoadFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<TimeUnit> doInBackground(Long... time) {
        List<TimeUnit> list = new ArrayList<>();
        long startTime = time[0];
        long endTime = time[1];
        if (endTime > startTime) {
            long delta = (endTime - startTime) / 15;
            Random rand = new Random(100);
            for (long i = startTime; i <= endTime; i += delta) {
                list.add(new TimeUnit(i, rand.nextInt(100)));
            }
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<TimeUnit> timeUnits) {
        if (listener != null) {
            listener.onLoadFinished(timeUnits);
        }
    }

    public interface OnLoadFinishedListener {
        void onLoadFinished(List<TimeUnit> timeUnits);
    }
}