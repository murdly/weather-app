package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Main {
    public final double temp;
    @SerializedName("temp_max")
    public final double tempMax;
    @SerializedName("temp_min")
    public final double tempMin;
    public final double pressure;
    public final int humidity;

    public Main(double temp, double tempMax, double tempMin, double pressure, int humidity) {
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
