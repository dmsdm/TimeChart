package com.example.timechart;

import com.example.timechart.viewmodel.TimeChartViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void viewModelSet() {
        assertNotNull(mainActivity.viewModel);
    }

    @Test
    public void hasLoadButton() {
        assertNotNull(mainActivity.loadButton);
    }

    @Test
    public void buttonClick() {
        mainActivity.viewModel = Mockito.mock(TimeChartViewModel.class);

        mainActivity.loadButton.callOnClick();

        Mockito.verify(mainActivity.viewModel).load();
    }

    @Test
    public void hasChartView() {
        assertNotNull(mainActivity.chartView);
    }
}