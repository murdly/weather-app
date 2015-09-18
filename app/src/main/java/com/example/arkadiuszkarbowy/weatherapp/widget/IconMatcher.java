package com.example.arkadiuszkarbowy.weatherapp.widget;

import android.content.Context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.example.arkadiuszkarbowy.weatherapp.R;
/**
 * Created by arkadiuszkarbowy on 16/09/15.
 */
public class IconMatcher {
    private static final Map<String, Integer> DRAWABLE_IDS= createMap();
    private static final Map<String, Integer> SMALL_DRAWABLE_IDS= createMap2();

    private static Map<String, Integer> createMap() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("01", R.drawable.ic_weather_sunny);
        result.put("02", R.drawable.ic_weather_partlycloudy);
        result.put("03", R.drawable.ic_weather_cloudy);
        result.put("04", R.drawable.ic_weather_cloudy);
        result.put("09", R.drawable.ic_weather_pouring);
        result.put("10", R.drawable.ic_weather_rainy);
        result.put("11", R.drawable.ic_weather_lightning);
        result.put("13", R.drawable.ic_weather_snowy);
        result.put("50", R.drawable.ic_weather_fog);

        return Collections.unmodifiableMap(result);
    }

    private static Map<String, Integer> createMap2() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("01", R.drawable.ic_weather_sunny_small);
        result.put("02", R.drawable.ic_weather_partlycloudy_small);
        result.put("03", R.drawable.ic_weather_cloudy_small);
        result.put("04", R.drawable.ic_weather_cloudy_small);
        result.put("09", R.drawable.ic_weather_pouring_small);
        result.put("10", R.drawable.ic_weather_rainy_small);
        result.put("11", R.drawable.ic_weather_lightning_small);
        result.put("13", R.drawable.ic_weather_snowy_small);
        result.put("50", R.drawable.ic_weather_fog_small);

        return Collections.unmodifiableMap(result);
    }

    public static int getDrawableId(String code){
        return DRAWABLE_IDS.get(code.substring(0, code.length() - 1));
    }

    public static int getSmallDrawableId(String code){
        return SMALL_DRAWABLE_IDS.get(code.substring(0, code.length()-1));
    }
}
