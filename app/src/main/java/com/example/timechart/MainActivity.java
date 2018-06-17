package com.example.timechart;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.timechart.viewmodel.TimeChartViewModel;

public class MainActivity extends AppCompatActivity {

    @VisibleForTesting TimeChartViewModel viewModel;
    @VisibleForTesting Button loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setUpViewModel();
    }

    private void setViews() {
        loadButton = findViewById(R.id.load_button);
    }

    private void setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(TimeChartViewModel.class);
    }
}