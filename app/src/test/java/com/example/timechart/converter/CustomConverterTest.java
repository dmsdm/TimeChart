package com.example.timechart.converter;

import com.example.timechart.entity.TimeUnit;
import com.example.timechart.viewmodel.WebService;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.assertArrayEquals;

public class CustomConverterTest {

    WebService service;

    @Before
    public void setUp() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody("1000:100,1234:150"));
        mockWebServer.start();
        HttpUrl url = mockWebServer.url("/");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new ConverterFactory())
                .build();
        service = retrofit.create(WebService.class);
    }

    @Test
    public void convert() throws Exception {
        Response<List<TimeUnit>> response = service.getTimeUnitList(1000, 1234).execute();

        TimeUnit[] actual = response.body().toArray(new TimeUnit[2]);
        TimeUnit[] expected = new TimeUnit[]{new TimeUnit(1000, 100),
                new TimeUnit(1234, 150)};
        assertArrayEquals(expected, actual);
    }
}