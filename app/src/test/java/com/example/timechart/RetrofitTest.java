package com.example.timechart;

import com.example.timechart.entity.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RetrofitTest {

    static class Result {
        public List<TimeUnit> list;
    }
    interface Service {
        @GET("/")
        Call<Result> exampleJson();
    }

    private Service service;

    @Before
    public void setUp() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody("{\"list\":[{\"time\":\"value\"}, {1000:100}, {1234:150]}}"));
        // Start the server.
        mockWebServer.start();
        HttpUrl url = mockWebServer.url("/");
        System.out.println("url = " + url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);


    }

    @Test
    public void response() throws Exception {

        Response<Result> response = service.exampleJson().execute();
        System.out.println(response.body().list);
    }
}