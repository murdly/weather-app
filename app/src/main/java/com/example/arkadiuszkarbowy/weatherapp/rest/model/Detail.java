package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Detail {
    public final long dt;
    public final Main main;
    public final Wind wind;

    @SerializedName("weather")
    public final List<Description> description;

    public Detail(long dt, Main main, Wind wind, List<Description> description) {
        this.dt = dt;
        this.main = main;
        this.wind = wind;
        this.description = description;
    }

    public int getNumericDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dt*1000);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getDayName() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dt*1000);
        String[] days = new String[]{"nd", "pn", "wt", "sr", "cz", "pt", "so"};
        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public String getIconCode() {
        return description.get(0).icon;
    }

    public int getTemp(){
        return (int) main.temp;
    }
}
