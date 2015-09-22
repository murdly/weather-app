package com.example.arkadiuszkarbowy.weatherapp.rest.model;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class City {
    private final int id;
    private final String name;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
