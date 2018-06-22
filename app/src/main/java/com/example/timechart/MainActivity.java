package com.example.timechart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.timechart.dialogs.DatePickerDialogFragment;
import com.example.timechart.dialogs.TimePickerDialogFragment;
import com.example.timechart.entity.TimeUnit;
import com.example.timechart.viewmodel.TimeChartViewModel;
import com.example.timechart.views.ChartView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialogFragment.OnDateSetListener, TimePickerDialogFragment.OnTimeSetListener {

    @VisibleForTesting TimeChartViewModel viewModel;
    @VisibleForTesting Button loadButton;
    @VisibleForTesting ChartView chartView;
    @VisibleForTesting Button startTime;
    @VisibleForTesting Button endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setViewModel();
        subscribeUi();
    }

    private void setViews() {
        loadButton = findViewById(R.id.load_button);
        chartView = findViewById(R.id.chart_view);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
    }

    private void setViewModel() {
        viewModel = ViewModelProviders.of(this).get(TimeChartViewModel.class);
    }

    private void subscribeUi() {
        loadButton.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        viewModel.getTimeChart().observe(this, new Observer<List<TimeUnit>>() {
            @Override
            public void onChanged(@Nullable List<TimeUnit> objects) {
                chartView.setData(objects);
            }
        });
        viewModel.getStartTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String time) {
                startTime.setText(time);
            }
        });
        viewModel.getEndTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String time) {
                endTime.setText(time);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == loadButton) {
            viewModel.load();
        } else if (v == startTime) {
            showStartDateTimeDialog();
        } else if (v == endTime) {
            showEndDateTimeDialog();
        }
    }

    private void showStartDateTimeDialog() {
        DatePickerDialogFragment.newInstance("start").show(getSupportFragmentManager(), "date_picker_dialog");
    }

    private void showEndDateTimeDialog() {
        DatePickerDialogFragment.newInstance("end").show(getSupportFragmentManager(), "date_picker_dialog");
    }

    @Override
    public void onDateSet(String id, int year, int month, int dayOfMonth) {
        if ("start".equals(id)) {
            viewModel.setStartDate(year, month, dayOfMonth);
        } else if ("end".equals(id)) {
            viewModel.setEndDate(year, month, dayOfMonth);
        }
        TimePickerDialogFragment.newInstance(id).show(getSupportFragmentManager(), "time_picker_dialog");
    }

    @Override
    public void onTimeSet(String id, int hourOfDay, int minute) {
        if ("start".equals(id)) {
            viewModel.setStartTime(hourOfDay, minute);
        } else if ("end".equals(id)) {
            viewModel.setEndTime(hourOfDay, minute);
        }
    }
}