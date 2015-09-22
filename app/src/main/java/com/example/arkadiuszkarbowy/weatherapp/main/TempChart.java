package com.example.arkadiuszkarbowy.weatherapp.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.example.arkadiuszkarbowy.weatherapp.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by arkadiuszkarbowy on 18/09/15.
 * <p/>
 * jak chcialem po prostu nadpisac LineChartView to sie nie rysowal
 */
public class TempChart {
    private Context mContext;
    private LineChartView mChart;

    public TempChart(Context context, LineChartView mChart) {
        this.mChart = mChart;
        mContext = context;
        setUp();
    }

    private void setUp() {
        mChart.setStep(5);
        mChart.setXAxis(false);
        mChart.setYAxis(false);
        mChart.setYLabels(AxisController.LabelPosition.OUTSIDE);
        mChart.setThresholdLine(0f, new Paint());
    }

    public void setData(Map<String, Float> temps) {
        LineSet data = new LineSet();
        data.setFill(mContext.getResources().getColor(R.color.temp_bg));
        data.setDotsColor(Color.GREEN);
        data.setSmooth(true);

        for (String time : temps.keySet())
            data.addPoint(time, temps.get(time));

        mChart.dismiss();
        mChart.addData(data);
    }

    public void show() {
        mChart.show();
    }
}
