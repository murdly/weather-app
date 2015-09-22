package com.example.arkadiuszkarbowy.weatherapp.rest.model;

import java.util.List;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Forecast {
    private final City city;
    private final List<Detail> list;

    public Forecast(City city, List<Detail> list) {
        this.city = city;
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public List<Detail> getList() {
        return list;
    }
}
