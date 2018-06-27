package com.example.timechart.mock;

import com.example.timechart.converter.ConverterFactory;
import com.example.timechart.entity.TimeUnit;
import com.example.timechart.viewmodel.WebService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class WebServerTest {

    private WebService service;
    private WebServer webServer;

    @Before
    public void setUp() {
        webServer = new WebServer();
        webServer.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServer.getLocalWebServer())
                .addConverterFactory(new ConverterFactory())
                .build();
        service = retrofit.create(WebService.class);
    }

    @After
    public void tearDown() {
        webServer.stop();
    }

    @Test
    public void getTimeChart() throws IOException {
        Response<List<TimeUnit>> response = service.getTimeUnitList(100000, 200000).execute();
        System.out.println(response.toString());

        assertEquals(100000, response.body().get(0).getTime());
    }
}