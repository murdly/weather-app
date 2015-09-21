package com.example.arkadiuszkarbowy.weatherapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.ApiService;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;
import com.example.arkadiuszkarbowy.weatherapp.widget.IconMatcher;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CurrentFragment extends Fragment implements WeatherFragment {
    private static final String ARG_CITY = "city";

    private String mCity;
    private ImageView mIcon;
    private TextView mTemp;
    private LinearLayout mNext3Days;

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
        mIcon = (ImageView) view.findViewById(R.id.crr_icon);
        mTemp = (TextView) view.findViewById(R.id.crr_temp);
        createNext3DaysView(view, inflater);
        return view;
    }

    private void createNext3DaysView(View view, LayoutInflater inflater) {
        mNext3Days = (LinearLayout) view.findViewById(R.id.next_3_days);
        for (int i = 0; i < 3; i++) {
            View daily = inflater.inflate(R.layout.daily, null);
            daily.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            mNext3Days.addView(daily);
        }
    }

    @Override
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
            Log.d("Weather", response.raw().toString());
            Weather w = response.body();
            mIcon.setImageResource(IconMatcher.getDrawableId(w.getIconCode()));
            mTemp.setText(w.getCurrentTemp() + getResources().getString(R.string.celcius));
            mListener.onWeatherInfoUpdate(w);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("Weather", "failure");
        }
    };

    Callback<Forecast> mForecast = new Callback<Forecast>() {
        @Override
        public void onResponse(Response<Forecast> response) {
            Log.d("Forecast", response.raw().toString());
            Forecast3 f = new Forecast3(response.body());
            buildNext3DaysView(f);
            mListener.onChartUpdate(f.getDay1().collectDailyTemps());
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("Forecast", "failure");
        }
    };

    private void buildNext3DaysView(Forecast3 forecast) {
        ArrayList<Forecast3.Day> days = forecast.get3Days();
        for (int i = 0; i < days.size(); i++) {
            Forecast3.Day day = days.get(i);
            View child = mNext3Days.getChildAt(i);
            ((ImageView) child.findViewById(R.id.icon)).setImageResource(IconMatcher.getSmallDrawableId(day
                    .getIconCode()));
            ((TextView) child.findViewById(R.id.day_name)).setText(day.getDayName());
            String degree = getActivity().getResources().getString(R.string.degree);
            ((TextView) child.findViewById(R.id.temp_max)).setText(day.getTempMax() + degree);
            ((TextView) child.findViewById(R.id.temp_min)).setText(day.getTempMin() + degree);
        }
    }

    public interface OnFragmentDataListener {
        void onWeatherInfoUpdate(Weather w);
        void onChartUpdate(Map<String,Float> temps);
    }
}
