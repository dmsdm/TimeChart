package com.example.timechart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.timechart.R;
import com.example.timechart.entity.TimeUnit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChartView extends View {

    private static final String TAG = "ChartView";
    private Paint textPaint;
    private int width;
    private int height;
    private List<TimeUnit> data;
    private int maxValue;
    private String maxText;
    private String startDate;
    private String endDate;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setData(List<TimeUnit> list) {
        this.data = list;
        calcMaxValue(list);
        setDates(list);
        invalidate();
    }

    private void calcMaxValue(List<TimeUnit> list) {
        maxValue = 0;
        for (TimeUnit timeUnit : list) {
            maxValue = Math.max(maxValue, timeUnit.getValue());
        }
        maxText = String.valueOf(maxValue);
    }

    private void setDates(List<TimeUnit> list) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        startDate = simpleDateFormat.format(new Date(list.get(0).getTime()));
        endDate = simpleDateFormat.format(new Date(list.get(list.size()-1).getTime()));
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(25);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (data == null || data.isEmpty()) {
            drawNoData(canvas);
        } else {
            drawAxis(canvas);
            drawChart(canvas);
            drawValues(canvas);
        }
    }

    private void drawNoData(Canvas canvas) {
        canvas.drawText(getContext().getString(R.string.no_data), width / 2, height / 2, textPaint);
    }

    private void drawAxis(Canvas canvas) {
        canvas.drawLine(50, height-50, width, height-50, textPaint);
        canvas.drawLine(50, 0, 50, height-50, textPaint);
    }

    private void drawChart(Canvas canvas) {
        long time0 = data.get(0).getTime();
        long time1;
        float dt = data.get(data.size()-1).getTime() - time0;
        float dx = dt > 0 ? (width-50) / dt : width;
        float dy = (height-50) / maxValue;
        int x0 = 50, x1 = 50;
        int y0 = height - 50 - (int) (dy*data.get(0).getValue()), y1;
        for (int i = 1, size = data.size(); i < size; ++i) {
            time1 = data.get(i).getTime();
            x1 += (int) ((time1-time0) * dx);
            y1 = height - 50 - (int) (data.get(i).getValue() * dy);
            Log.d(TAG, "drawLine: " + x0 + ", " + y0 + ", " + x1 + ", " + y1);
            canvas.drawLine(x0, y0, x1, y1, textPaint);
            x0 = x1;
            y0 = y1;
            time0 = time1;
        }
    }

    private void drawValues(Canvas canvas) {
        canvas.drawText(maxText, 0, 50, textPaint);
        canvas.drawText(startDate, 0, height, textPaint);
        canvas.drawText(endDate, width - 200, height, textPaint);
    }
}