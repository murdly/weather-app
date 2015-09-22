package com.example.arkadiuszkarbowy.weatherapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.ApiService;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;
import com.example.arkadiuszkarbowy.weatherapp.widget.IconMatcher;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CurrentFragment extends Fragment implements WeatherFragment {
    private static final String ARG_CITY = "city";

    private String mCity;
    private WeatherBriefController mWeatherBrief;
    private OnFragmentDataListener mListener;

    public static CurrentFragment newInstance(String city) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    public CurrentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = getArguments().getString(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        WeatherBriefView briefView = (WeatherBriefView) view.findViewById(R.id.weather_brief);
        mWeatherBrief = new WeatherBriefController(getActivity(), briefView);
        mWeatherBrief.setOnDaySelectedListener(mOnDaySelectedListener);

        return view;
    }

    private DayItemView.OnClickListener mOnDaySelectedListener = new DayItemView.OnClickListener(){
        @Override
        public void onClick(View v) {
            mWeatherBrief.setSelected((DayItemView) v);
            mListener.onChartUpdate(mWeatherBrief.collectTempsOfSelectedDay());
            mListener.onWeatherInfoUpdate(mWeatherBrief.createWeatherObject());
        }
    };

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentDataListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentDataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateDataFor(String city) {
        Log.d("CurrentFragment", city);
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
            mWeatherBrief.setCurrentTemp(w.getCurrentTemp());
            mWeatherBrief.setCurrentIcon(IconMatcher.getDrawableId(w.getIconCode()));
            mListener.onWeatherInfoUpdate(w);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("CurrentFragment", " forecast failure");
        }
    };

    Callback<Forecast> mForecast = new Callback<Forecast>() {
        @Override
        public void onResponse(Response<Forecast> response) {
            Log.d("CurrentFragment", response.raw().toString());
            Forecast3 dailyForecast = new Forecast3(response.body());
            mWeatherBrief.assignForecast(dailyForecast);
            mListener.onChartUpdate(dailyForecast.getDay1().collectDailyTemps());
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("CurrentFragment", "forecast failure");
        }
    };

    public interface OnFragmentDataListener {
        void onWeatherInfoUpdate(Weather w);
        void onChartUpdate(Map<String,Float> temps);
    }
}
