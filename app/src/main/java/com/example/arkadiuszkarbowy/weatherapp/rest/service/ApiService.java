package com.example.arkadiuszkarbowy.weatherapp.rest.service;

import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by arkadiuszkarbowy on 14/09/15.
 */
public interface ApiService {
//     String APPID = "&APPID=b21242744b05bf6ef7aa2fe19a67beb3";

     @GET("/data/2.5/weather")
     Call<Weather> weather(@Query("id") String id);
}
