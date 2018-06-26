package com.example.timechart.converter;

import com.example.timechart.entity.TimeUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomConverter implements Converter<ResponseBody, List<TimeUnit>> {

    @Override
    public List<TimeUnit> convert(ResponseBody value) throws IOException {
        String body = new String(value.bytes());
        List<TimeUnit> list = new ArrayList<>();
        String[] units = body.split(",");
        for (String unit : units) {
            String[] part = unit.split(":");
            if (part.length == 2) {
                long time = Long.valueOf(part[0]);
                int val = Integer.valueOf(part[1]);
                list.add(new TimeUnit(time, val));
            }
        }
        return list;
    }
}