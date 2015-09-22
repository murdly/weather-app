package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Main {
    private final double temp;
    @SerializedName("temp_max")
    private final double tempMax;
    @SerializedName("temp_min")
    private final double tempMin;
    private final double pressure;
    private final int humidity;

    public Main(double temp, double tempMax, double tempMin, double pressure, int humidity) {
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }
}
