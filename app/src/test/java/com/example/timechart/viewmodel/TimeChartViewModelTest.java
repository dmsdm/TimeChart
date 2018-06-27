package com.example.timechart.viewmodel;

import com.example.timechart.LiveDataTestUtil;
import com.example.timechart.entity.Statistics;
import com.example.timechart.entity.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowAsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class TimeChartViewModelTest {

    private TimeChartViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TimeChartViewModel();
    }

    @Test
    @Config(shadows = {ShadowTimeChartLoader.class})
    public void load() throws Exception {
        viewModel.load();

        List<TimeUnit> list = LiveDataTestUtil.getValue(viewModel.getTimeChart());
        Statistics statistics = LiveDataTestUtil.getValue(viewModel.getCalculatedValues());

        assertNotNull(list);
        assertNotNull(statistics);
    }

    @Test
    public void getStartTime() throws Exception {
        viewModel.setStartDate(2018, 5, 15);
        viewModel.setStartTime(12, 00);

        long actualTime = LiveDataTestUtil.getValue(viewModel.getStartTime()) / 1000;
        long expectedTime = new Date(118, 5, 15, 12, 00, 00).getTime() / 1000;
        assertEquals(expectedTime, actualTime);
    }

    @Test
    public void getEndTime() throws Exception {
        viewModel.setEndDate(2018, 5, 15);
        viewModel.setEndTime(12, 00);

        long actualTime = LiveDataTestUtil.getValue(viewModel.getEndTime()) / 1000;
        long expectedTime = new Date(118, 5, 15, 12, 00, 00).getTime() / 1000;
        assertEquals(expectedTime, actualTime);
    }

    @Implements(TimeChartLoader.class)
    public static class ShadowTimeChartLoader extends ShadowAsyncTask<Long, Void, TimeChartLoader.Result> {
        @Implementation
        protected TimeChartLoader.Result doInBackground(Long... longs) {
            TimeChartLoader.Result result = new TimeChartLoader.Result();
            result.timeUnits = new ArrayList<>();
            result.statistics = new Statistics();
            return result;
        }
    }
}