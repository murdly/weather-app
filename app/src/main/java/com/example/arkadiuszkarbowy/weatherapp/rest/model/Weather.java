package com.example.arkadiuszkarbowy.weatherapp.rest.model;

/**
 * Created by arkadiuszkarbowy on 14/09/15.
 */
public class Weather {
    public final String name;
    public final int id;
    public final Main main;

    public Weather(String name, int id, Main main) {
        this.name  = name;
        this.id = id;
        this.main = main;
    }
}
