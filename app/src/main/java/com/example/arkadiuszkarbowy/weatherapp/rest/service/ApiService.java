package com.example.arkadiuszkarbowy.weatherapp.rest.service;

import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by arkadiuszkarbowy on 14/09/15.
 */
public interface ApiService {
     String UNITS = "metric";

     @Headers("x-api-key:b21242744b05bf6ef7aa2fe19a67beb3")
     @GET("/data/2.5/weather")
     Call<Weather> weather(@Query("id") String id, @Query("units") String units);

     @Headers("x-api-key:b21242744b05bf6ef7aa2fe19a67beb3")
     @GET("data/2.5/forecast")
     Call<Forecast> forecast(@Query("id") String id, @Query("units") String units);
}
