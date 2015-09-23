package com.example.arkadiuszkarbowy.weatherapp.main;

import android.content.Context;
import android.view.View;

import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.widget.IconMatcher;

import java.util.Map;

/**
 * Created by arkadiuszkarbowy on 22/09/15.
 */
public class BriefController {
    private BriefView mView;
    private Context mContext;
    private Forecast3 forecastModel;
    private Weather weatherModel;

    public BriefController(Context context, BriefView view) {
        this.mView = view;
        mContext = context;
    }

    public void assignWeatherModel(Weather weather) {
        weatherModel = weather;
    }

    public void assignForecastModel(Forecast3 forecast) {
        forecastModel = forecast;
    }

    public void updateWeatherView() {
        mView.setCurrentTemp(weatherModel.getCurrentTemp() + mContext.getResources().getString(R.string.celsius));
        mView.setCurrentIcon(IconMatcher.getDrawableId(weatherModel.getIconCode()));
    }

    public void updateForecastView() {
        mView.updateDaily(forecastModel.get3Days());
        mView.setFirstItemSelected();
    }

    public void setOnDayViewItemSelectedListener(DayItemView.OnClickListener mOnDaySelectedListener) {
        mView.setDailyItemSelectedListener(mOnDaySelectedListener);
    }

    public void setDayItemSelected(DayItemView dayItem) {
        mView.markSelections(dayItem);
    }

    public Map<String, Float> collectTempsOfSelectedDay() {
        return getForecastOfSelectedDay().collectDailyTemps();
    }

    private Forecast3.Day getForecastOfSelectedDay() {
        int index = mView.indexOfSelectedItem();
        return forecastModel.getDay(index);
    }

    public Weather createWeatherObject() {
        Forecast3.Day day = getForecastOfSelectedDay();
        double tempMax = day.getTempMax();
        double tempMin = day.getTempMin();
        double wind = day.getAvgWindSpeed();
        double pressure = day.getAvgPressure();
        int humidity = day.getAvgHumidity();
        return new Weather(tempMax, tempMin, wind, pressure, humidity);
    }

    public void hideView() {
        mView.setVisibility(View.INVISIBLE);
    }

    public void showView() { mView.setVisibility(View.VISIBLE); }
}