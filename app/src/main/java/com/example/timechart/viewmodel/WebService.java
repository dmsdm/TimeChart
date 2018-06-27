package com.example.timechart.viewmodel;

import com.example.timechart.entity.TimeUnit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {
    @GET("/timechart")
    Call<List<TimeUnit>> getTimeUnitList(@Query("start_time") long startTime, @Query("end_time") long endTime);
}