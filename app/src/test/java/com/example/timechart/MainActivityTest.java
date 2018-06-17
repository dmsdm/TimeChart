package com.example.timechart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
}