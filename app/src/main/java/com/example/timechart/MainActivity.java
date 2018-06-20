package com.example.timechart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.timechart.entity.TimeUnit;
import com.example.timechart.viewmodel.TimeChartViewModel;
import com.example.timechart.views.ChartView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @VisibleForTesting TimeChartViewModel viewModel;
    @VisibleForTesting Button loadButton;
    @VisibleForTesting ChartView chartView;
    @VisibleForTesting TextView startTime;
    @VisibleForTesting TextView endTime;

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
        }
    }
}