package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arkadiuszkarbowy on 14/09/15.
 */
public class Weather {
    public final String name;
    public final int id;
    public final Main main;
    public final Wind wind;

    @SerializedName("weather")
    public final List<Description> description;

    public Weather(String name, int id, Main main, Wind wind, List<Description> description) {
        this.name = name;
        this.id = id;
        this.main = main;
        this.wind = wind;
        this.description = description;
    }


    public int getCurrentTemp(){
        return (int) Math.round(main.temp);
    }

    public String getIconCode(){
        return description.get(0).icon;
    }
}
