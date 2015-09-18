package com.example.arkadiuszkarbowy.weatherapp.rest.model;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Main {
    public final double temp;
    public final double pressure;
    public final int humidity;

    public Main(double temp, double pressure, int humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
