package com.example.arkadiuszkarbowy.weatherapp.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.ApiService;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CurrentFragment extends WeatherFragment {
    private BriefController mBriefController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        BriefView briefView = (BriefView) view.findViewById(R.id.weather_brief);
        mBriefController = new BriefController(getActivity(), briefView);
        mBriefController.setOnDayViewItemSelectedListener(mOnDaySelectedListener);
        return view;
    }

    private DayItemView.OnClickListener mOnDaySelectedListener = new DayItemView.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBriefController.setDayItemSelected((DayItemView) v);
            mListener.onChartUpdate(mBriefController.collectTempsOfSelectedDay());
            mListener.onWeatherInfoUpdate(mBriefController.createWeatherObject());
        }
    };

    @Override
    public void updateDataFor(String city) {
        RestClient rest = new RestClient();
        Call<Weather> call = rest.getApiService().weather(Cities.getId(city), ApiService.UNITS);
        call.enqueue(mCurrentWeather);

        Call<Forecast> call3 = rest.getApiService().forecast(Cities.getId(city), ApiService.UNITS);
        call3.enqueue(mForecast);
    }

    Callback<Weather> mCurrentWeather = new Callback<Weather>() {
        @Override
        public void onResponse(Response<Weather> response) {
            Log.d("CurrentFragment", response.raw().toString());
            Weather w = response.body();
            mBriefController.assignWeatherModel(w);
            mBriefController.updateWeatherView();
            mBriefController.showView();
            mListener.onWeatherInfoUpdate(w);
            mListener.showViews();
        }

        @Override
        public void onFailure(Throwable t) {
            mBriefController.hideView();
            mListener.hideViews();
            Toast.makeText(getActivity(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    };

    Callback<Forecast> mForecast = new Callback<Forecast>() {
        @Override
        public void onResponse(Response<Forecast> response) {
            Log.d("CurrentFragment", response.raw().toString());
            Forecast3 f = new Forecast3(response.body());
            mBriefController.assignForecastModel(f);
            mBriefController.updateForecastView();
            mListener.onChartUpdate(f.getDay1().collectDailyTemps());
            mBriefController.showView();
            mListener.onChartUpdate(mBriefController.collectTempsOfSelectedDay());
            mListener.showViews();
        }

        @Override
        public void onFailure(Throwable t) {
            mBriefController.hideView();
            mListener.hideViews();
            Toast.makeText(getActivity(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    };
}