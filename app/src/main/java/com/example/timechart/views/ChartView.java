package com.example.timechart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.timechart.entity.TimeUnit;

import java.util.List;

public class ChartView extends View {

    private static final String TAG = "ChartView";
    private Paint textPaint;
    private int width;
    private int height;
    private List<TimeUnit> data;
    private int maxValue;

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
        invalidate();
    }

    private void calcMaxValue(List<TimeUnit> list) {
        maxValue = 0;
        for (TimeUnit timeUnit : list) {
            maxValue = Math.max(maxValue, timeUnit.getValue());
        }
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
            canvas.drawText("no data", width / 2, height / 2, textPaint);
        } else {
            canvas.drawLine(50, height-50, width, height-50, textPaint);
            canvas.drawLine(50, 0, 50, height-50, textPaint);
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
    }
}