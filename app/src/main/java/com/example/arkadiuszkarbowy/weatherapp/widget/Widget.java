package com.example.arkadiuszkarbowy.weatherapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.arkadiuszkarbowy.weatherapp.main.Cities;
import com.example.arkadiuszkarbowy.weatherapp.main.Forecast3;
import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.main.MainActivity;
import com.example.arkadiuszkarbowy.weatherapp.main.WeatherBriefView;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.ApiService;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by arkadiuszkarbowy on 15/09/15.
 */
public class Widget extends AppWidgetProvider {
    public static final String ACTION_UPDATE_LOCATION = "update_location";
    public static final String DATA_CITY_NAME = "city_name";
    private static final int UPDATE_WEATHER = 1;
    private static final int UPDATE_FORECAST = 2;

    private String mCity = Cities.DEFAULT_CITY;
    private Context mContext;
    private Weather mCurrent;
    private Forecast3 mDays3;

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        mContext = context;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mCity = prefs.getString(MainActivity.CURRENT_CITY, Cities.DEFAULT_CITY);

        if (intent.getAction().equals(ACTION_UPDATE_LOCATION))
            mCity = intent.getStringExtra(DATA_CITY_NAME);

        prefs.edit().putString(MainActivity.CURRENT_CITY, mCity).commit();


        RestClient restClient = new RestClient();
        Call<Weather> callCrr = restClient.getApiService().weather(Cities.getId(mCity), ApiService.UNITS);
        callCrr.enqueue(mCurrentWeather);

        Call<Forecast> call3 = restClient.getApiService().forecast(Cities.getId(mCity), ApiService.UNITS);
        call3.enqueue(mForecast3);
    }

    Callback<Weather> mCurrentWeather = new Callback<Weather>() {
        @Override
        public void onResponse(Response<Weather> response) {
            mCurrent = response.body();
            Log.d("Widget", response.raw().toString());
            updateView(mContext, UPDATE_WEATHER);
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(mContext, mContext.getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    };

    Callback<Forecast> mForecast3 = new Callback<Forecast>() {
        @Override
        public void onResponse(Response<Forecast> response) {
            Forecast f = response.body();
            Log.d("Widget", response.raw().toString());
            mDays3 = new Forecast3(f);
            updateView(mContext, UPDATE_FORECAST);
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(mContext, mContext.getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    };

    private void updateView(Context context, int which) {
        RemoteViews updateViews = null;

        switch (which) {
            case UPDATE_WEATHER:
                updateViews = buildWeatherUpdate(context);
                break;
            case UPDATE_FORECAST:
                updateViews = buildForecastUpdate(context);
                break;
        }

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, Widget.class), updateViews);
    }

    private RemoteViews buildWeatherUpdate(Context context) {
        Resources res = context.getResources();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.temp, mCurrent.getCurrentTemp() + res.getString(R.string.celsius));
        views.setImageViewResource(R.id.crr_icon, IconMatcher.getDrawableId(mCurrent.getIconCode()));
        views.setTextViewText(R.id.city, mCity);

        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, new Intent(context,
                WidgetDialog.class), PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.city, configPendingIntent);

        return views;
    }

    private RemoteViews buildForecastUpdate(Context context) {
        String degree = context.getResources().getString(R.string.degree);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setImageViewResource(R.id.icon_d1, IconMatcher.getSmallDrawableId(mDays3.getDay1().getIconCode()));
        views.setTextViewText(R.id.day_name1, mDays3.getDay1().getDayName());
        views.setTextViewText(R.id.temp_max1, mDays3.getDay1().getTempMax() + degree);
        views.setTextViewText(R.id.temp_min1, mDays3.getDay1().getTempMin() + degree);
        views.setImageViewResource(R.id.icon_d2, IconMatcher.getSmallDrawableId(mDays3.getDay2().getIconCode()));
        views.setTextViewText(R.id.day_name2, mDays3.getDay2().getDayName());
        views.setTextViewText(R.id.temp_max2, mDays3.getDay2().getTempMax() + degree);
        views.setTextViewText(R.id.temp_min2, mDays3.getDay2().getTempMin() + degree);
        views.setImageViewResource(R.id.icon_d3, IconMatcher.getSmallDrawableId(mDays3.getDay3().getIconCode()));
        views.setTextViewText(R.id.day_name3, mDays3.getDay3().getDayName());
        views.setTextViewText(R.id.temp_max3, mDays3.getDay3().getTempMax() + degree);
        views.setTextViewText(R.id.temp_min3, mDays3.getDay3().getTempMin() + degree);
        views.setViewVisibility(R.id.content, View.VISIBLE);
        views.setViewVisibility(R.id.loading, View.GONE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_frame, configPendingIntent);
        return views;
    }
}