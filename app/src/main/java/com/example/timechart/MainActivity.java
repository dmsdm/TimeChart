package com.example.timechart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.timechart.viewmodel.TimeChartViewModel;
import com.example.timechart.views.ChartView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @VisibleForTesting TimeChartViewModel viewModel;
    @VisibleForTesting Button loadButton;
    @VisibleForTesting ChartView chartView;

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
    }

    private void setViewModel() {
        viewModel = ViewModelProviders.of(this).get(TimeChartViewModel.class);
    }

    private void subscribeUi() {
        loadButton.setOnClickListener(this);
        viewModel.getTimeChart().observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> objects) {
                chartView.setData("loaded: " + objects.size());
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