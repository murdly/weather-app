package com.example.arkadiuszkarbowy.weatherapp.main;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.db.chart.view.LineChartView;
import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.widget.Widget;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnFragmentDataListener {
    public static final String CURRENT_CITY = "current_city";
    private static int DATA_TYPE = 0;

    private TempChart mChart;
    private String mCity = Cities.DEFAULT_CITY;
    private Spinner mCitiesAvailable;
    private WeatherFragment fragment;
    private TextView mTempMin, mTempMax, mWind, mPressure, mHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCity = prefs.getString(MainActivity.CURRENT_CITY, Cities.DEFAULT_CITY);
        setUpCitiesSpinner();
        setCurrentDataAsDefault();
        createInfoView();
        mChart = new TempChart(this, (LineChartView) findViewById(R.id.chart));
    }

    private void setCurrentDataAsDefault() {
        DATA_TYPE = WeatherFragment.ACTION_CURRENT_DATA;
        FragmentManager fm = getFragmentManager();
        fragment = new CurrentFragment();
        fm.beginTransaction().add(R.id.container, fragment).commit();
    }

    private void setUpCitiesSpinner() {
        mCitiesAvailable = (Spinner) findViewById(R.id.cities_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Cities
                .getNames());
        mCitiesAvailable.setAdapter(adapter);
        mCitiesAvailable.setOnItemSelectedListener(onCitySelectedListener);
        mCitiesAvailable.setSelection(getCityPosition());
    }

    private int getCityPosition() {
        for (int i = 0; i < mCitiesAvailable.getCount(); i++) {
            String value = (String) mCitiesAvailable.getItemAtPosition(i);
            if (value.equals(mCity)) return i;
        }
        return -1;
    }

    private void createInfoView() {
        mTempMin = (TextView) findViewById(R.id.temp_min);
        mTempMax = (TextView) findViewById(R.id.temp_max);
        mWind = (TextView) findViewById(R.id.wind);
        mPressure = (TextView) findViewById(R.id.pressure);
        mHumidity = (TextView) findViewById(R.id.humidity);
    }

    AdapterView.OnItemSelectedListener onCitySelectedListener = new AdapterView.OnItemSelectedListener() {
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
    public void onWeatherInfoUpdate(Weather w) {
        String celsius = getResources().getString(R.string.celsius);
        mTempMax.setText(w.getMain().getTempMax() + " " + celsius);
        mTempMin.setText(w.getMain().getTempMin() + " " + celsius);
        mWind.setText(w.getWind().getSpeed() + " " + getResources().getString(R.string.ms));
        mPressure.setText(w.getMain().getPressure() + " " + getResources().getString(R.string.hpa));
        mHumidity.setText(w.getMain().getHumidity() + " " + getResources().getString(R.string.perc));
    }

    @Override
    public void onChartUpdate(Map<String, Float> temps) {
        if (temps.size() != 0) {
            mChart.setData(temps);
            mChart.show();
        } else {
            mChart.dismiss();
        }
    }

    @Override
    public void showViews() {
        findViewById(R.id.content).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViews() {
        findViewById(R.id.content).setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_history) {
            switch (DATA_TYPE) {
                case WeatherFragment.ACTION_CURRENT_DATA:
                    item.setTitle(getResources().getString(R.string.data_current));
                    DATA_TYPE = WeatherFragment.ACTION_HISTORY_DATA;
                    break;
                case WeatherFragment.ACTION_HISTORY_DATA:
                    item.setTitle(getResources().getString(R.string.data_history));
                    DATA_TYPE = WeatherFragment.ACTION_CURRENT_DATA;
                    break;
            }
            switchFragmentAccordingToDataType();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchFragmentAccordingToDataType() {
        if (DATA_TYPE == WeatherFragment.ACTION_CURRENT_DATA)
            fragment = new CurrentFragment();
        else
            fragment = new HistoryFragment();

        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        updateViews();
    }
}