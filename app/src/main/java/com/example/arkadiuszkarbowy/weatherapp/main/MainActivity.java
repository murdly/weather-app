package com.example.arkadiuszkarbowy.weatherapp.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.widget.Widget;

import java.util.Date;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity implements CurrentFragment.OnFragmentDataListener {
    public static final String CURRENT_CITY = "current_city";
    private TempChart mChart;
    private String mCity = Cities.DEFAULT_CITY;
    private Spinner mCitiesAvailable;
    WeatherFragment fragment;
    private TextView mTempMin, mTempMax, mWind, mPressure, mHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCity = prefs.getString(MainActivity.CURRENT_CITY, Cities.DEFAULT_CITY);
        Log.d("MainActivity", "city: " + mCity);

        FragmentManager fm = getFragmentManager();
        fragment = CurrentFragment.newInstance(mCity);
        fm.beginTransaction().add(R.id.container, (Fragment) fragment).commit();

        mCitiesAvailable = (Spinner) findViewById(R.id.cities_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Cities
                .getNames());
        mCitiesAvailable.setAdapter(adapter);
        mCitiesAvailable.setOnItemSelectedListener(onCityChangedListener);
        mCitiesAvailable.setSelection(getCityPosition());

        mChart = new TempChart(this, (LineChartView) findViewById(R.id.chart));

        createInfoView();
    }

    private void createInfoView() {
        mTempMin = (TextView) findViewById(R.id.temp_min);
        mTempMax = (TextView) findViewById(R.id.temp_max);
        mWind = (TextView) findViewById(R.id.wind);
        mPressure = (TextView) findViewById(R.id.pressure);
        mHumidity = (TextView) findViewById(R.id.humidity);
    }

    private int getCityPosition() {
        for (int i = 0; i < mCitiesAvailable.getCount(); i++) {
            String value = (String) mCitiesAvailable.getItemAtPosition(i);
            if (value.equals(mCity)) return i;
        }
        return -1;
    }

    AdapterView.OnItemSelectedListener onCityChangedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mCity = (String) parent.getSelectedItem();
            updateViews();
            notifyWidget();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void updateViews() {
        fragment.updateDataFor(mCity);

    }

    private void notifyWidget() {
        Intent updateLocation = new Intent(getApplicationContext(), Widget.class);
        updateLocation.setAction(Widget.ACTION_UPDATE_LOCATION);
        updateLocation.putExtra(Widget.DATA_CITY_NAME, mCity);
        PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, updateLocation, PendingIntent
                .FLAG_CANCEL_CURRENT);
        try {
            pending.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void onWeatherInfoUpdate(Weather w) {
        String celcius = getResources().getString(R.string.celcius);
        mTempMax.setText(w.main.tempMax + " " + celcius);
        mTempMin.setText(w.main.tempMin + " " + celcius);
        mWind.setText(w.wind.speed + " " + getResources().getString(R.string.ms));
        mPressure.setText(w.main.pressure + " " + getResources().getString(R.string.hpa));
        mHumidity.setText(w.main.humidity + " " + getResources().getString(R.string.perc));
    }

    @Override
    public void onChartUpdate(Map<String, Float> temps) {
        if (temps.size() != 0) {
            mChart.setData(temps);
            mChart.show();
        } else Log.d("MainActivity", "empty set");

    }
}
