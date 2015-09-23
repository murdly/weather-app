package com.example.arkadiuszkarbowy.weatherapp.main;

import android.content.Context;
import android.view.View;

import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Main;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.widget.IconMatcher;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by arkadiuszkarbowy on 22/09/15.
 */
public class WeatherBriefController {
    private WeatherBriefView view;
    private Context mContext;
    private Forecast3 forecast;
    private DayItemView mSelected;

    public WeatherBriefController(Context context, WeatherBriefView view) {
        this.view = view;
        mContext = context;
    }

    public void setCurrentTemp(int currentTemp) {
        view.setCurrentTemp(currentTemp + mContext.getResources().getString(R.string.celcius));
    }

    public void setCurrentIcon(int drawableId) {
        view.setCurrentIcon(drawableId);
    }

    public void assignForecast(Forecast3 forecast) {
        this.forecast = forecast;
        updateDailyView();
        setSelected(view.getDailyItem(0));
    }

    private void updateDailyView() {
        ArrayList<Forecast3.Day> days = forecast.get3Days();
        for (int i = 0; i < days.size(); i++) {
            Forecast3.Day day = days.get(i);
            DayItemView item = view.getDailyItem(i);
            item.setIcon(IconMatcher.getSmallDrawableId(day
                    .getIconCode()));
            item.setName(day.getDayName());
            String degree = mContext.getResources().getString(R.string.degree);
            item.setTempMax(day.getTempMax() + degree);
            item.setTempMin(day.getTempMin() + degree);
        }
    }

    public void setOnDaySelectedListener(DayItemView.OnClickListener mOnDaySelectedListener) {
        view.setDailyItemSelectedListener(mOnDaySelectedListener);
    }

    public void setSelected(DayItemView dayItemView) {
        view.markSelections(mSelected, dayItemView);
        mSelected = dayItemView;
    }

    public Map<String, Float> collectTempsOfSelectedDay() {
        return getSelectedDayForecast().collectDailyTemps();
    }

    private Forecast3.Day getSelectedDayForecast() {
        int index = view.indexOf(mSelected);
        return forecast.getDay(index);
    }

    public Weather createWeatherObject() {
        Forecast3.Day day = getSelectedDayForecast();
        double tempMax = day.getTempMax();
        double tempMin = day.getTempMin();
        double wind = day.getAvgWindSpeed();
        double pressure = day.getAvgPressure();
        int humidity = day.getAvgHumidity();
        return new Weather(tempMax, tempMin, wind, pressure, humidity);
    }

    public void hide() {
        view.setVisibility(View.INVISIBLE);
    }

    public void show() {
        view.setVisibility(View.VISIBLE);
    }
}
