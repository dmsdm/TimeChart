package com.example.timechart.viewmodel;

import com.example.timechart.LiveDataTestUtil;
import com.example.timechart.entity.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class TimeChartViewModelTest {

    private TimeChartViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TimeChartViewModel();
    }

    @Test
    public void load() throws InterruptedException {
        viewModel.load();

        List<TimeUnit> list = LiveDataTestUtil.getValue(viewModel.getTimeChart());
        for (TimeUnit timeUnit : list) {
            System.out.println(timeUnit.getTime() + " : " + timeUnit.getValue());
        }
        assertNotNull(list);
    }
}