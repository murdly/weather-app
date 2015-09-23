package com.example.arkadiuszkarbowy.weatherapp.main;

import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;

import java.util.Map;

/**
 * Created by arkadiuszkarbowy on 23/09/15.
 */
public interface OnFragmentDataListener {
    void onWeatherInfoUpdate(Weather w);
    void onChartUpdate(Map<String,Float> temps);
    void showViews();
    void hideViews();
}