package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadiuszkarbowy on 14/09/15.
 */
public class Weather {
    private final String name;
    private final int id;
    private final Main main;
    private final Wind wind;

    @SerializedName("weather")
    public final List<Description> description;

    public Weather(String name, int id, Main main, Wind wind, List<Description> description) {
        this.name = name;
        this.id = id;
        this.main = main;
        this.wind = wind;
        this.description = description;
    }

    public Weather(double tempMax, double tempMin, double speed, double pressure, int humidity) {
        name = null;
        id = -1;
        main = new Main(tempMax, tempMax, tempMin, pressure, humidity);
        wind = new Wind(speed);
        description = null;
    }


    public int getCurrentTemp() {
        return (int) Math.round(main.getTemp());
    }

    public String getIconCode() {
        return description.get(0).getIcon();
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }
}
