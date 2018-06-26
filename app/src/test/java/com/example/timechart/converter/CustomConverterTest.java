package com.example.timechart.converter;

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
import retrofit2.http.GET;

import static org.junit.Assert.assertArrayEquals;

public class CustomConverterTest {

    interface Service {
        @GET("/")
        Call<List<TimeUnit>> exampleJson();
    }

    Service service;

    @Before
    public void setUp() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody("1000:100,1234:150"));
        mockWebServer.start();
        HttpUrl url = mockWebServer.url("/");
        System.out.println("url = " + url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new ConverterFactory())
                .build();
        service = retrofit.create(Service.class);
    }

    @Test
    public void convert() throws Exception {
        Response<List<TimeUnit>> response = service.exampleJson().execute();

        TimeUnit[] actual = response.body().toArray(new TimeUnit[2]);
        TimeUnit[] expected = new TimeUnit[]{new TimeUnit(1000, 100),
                new TimeUnit(1234, 150)};
        assertArrayEquals(expected, actual);
    }
}