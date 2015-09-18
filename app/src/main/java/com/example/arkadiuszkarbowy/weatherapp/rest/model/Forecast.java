package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import java.util.List;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Forecast {
    public final City city;
    public final List<Detail> list;

    public Forecast(City city, List<Detail> list) {
        this.city = city;
        this.list = list;
    }
}
