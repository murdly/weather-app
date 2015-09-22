package com.example.arkadiuszkarbowy.weatherapp.rest.model;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Wind {
    private final double speed;

    public Wind(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }
}
