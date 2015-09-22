package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Detail {
    private final long dt;
    @SerializedName("dt_txt")
    private final String dtTxt;
    private final Main main;
    private final Wind wind;
    @SerializedName("weather")
    private final List<Description> description;

    public Detail(long dt, String dtTxt, Main main, Wind wind, List<Description> description) {
        this.dt = dt;
        this.dtTxt = dtTxt;
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

    public String getTimeHm(){
        return new SimpleDateFormat("HH:mm").format(new Date(dt*1000));
    }

    public String getIconCode() {
        return description.get(0).getIcon();
    }

    public int getTemp(){
        return (int) main.getTemp();
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }
}
