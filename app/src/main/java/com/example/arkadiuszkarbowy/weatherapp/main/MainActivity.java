package com.example.arkadiuszkarbowy.weatherapp.main;

import android.appwidget.AppWidgetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;

import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    private LineChartView mTempChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTempChart = (LineChartView) findViewById(R.id.linechart);
        final LineSet data = new LineSet();
        data.addPoint("13:00", 22.5f);
        data.addPoint("16:00", -4.1f);
        data.addPoint("18:00", 18f);

//        data.setFill(Color.BLUE);
        data.setDotsColor(Color.GREEN);
        data.setSmooth(true);
        mTempChart.addData(data);
        mTempChart.setStep(5);
        mTempChart.setXAxis(false);
        mTempChart.setYAxis(false);
        mTempChart.setYLabels(AxisController.LabelPosition.OUTSIDE);
        mTempChart.setThresholdLine(0f, new Paint());
        mTempChart.show();
        mTempChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int i, int i1, Rect rect) {
                Log.d("OnEntryClickListener", i1 + " " + data.getEntry(i1));
            }
        });
    }


    Callback<Weather> mCb = new Callback<Weather>() {
        @Override
        public void onResponse(Response<Weather> response) {
            Weather w = response.body();
            Log.d("Weather", response.raw().toString());
            Log.d("Weather", "id: " + w.id + " name: " + w.name + " wind speed: " + w.wind.speed);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("Weather", "failure");
        }
    };

    Callback<Forecast> mForecast = new Callback<Forecast>() {
        @Override
        public void onResponse(Response<Forecast> response) {
            Forecast f = response.body();
            Log.d("Forecast", response.raw().toString());
            String first = new Date(f.list.get(0).dt * 1000).toString();
            String last = new Date(f.list.get(36).dt * 1000).toString();

            Log.d("Forecast", "id: " + f.city.id + " name: " + f.city.name + " dt: " + first + " do " + last);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("Forecast", "failure");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
