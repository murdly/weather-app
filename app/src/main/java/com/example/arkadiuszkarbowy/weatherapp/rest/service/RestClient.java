package com.example.arkadiuszkarbowy.weatherapp.rest.service;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by arkadiuszkarbowy on 14/09/15.
 */
public class RestClient {
    private static final String API_URL = "http://api.openweathermap.org";
    private ApiService apiService;

    public RestClient()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService()
    {
        return apiService;
    }
}
