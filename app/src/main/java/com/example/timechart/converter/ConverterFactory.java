package com.example.timechart.converter;

import android.support.annotation.Nullable;

import com.example.timechart.entity.TimeUnit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, List<TimeUnit>> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new CustomConverter();
    }
}