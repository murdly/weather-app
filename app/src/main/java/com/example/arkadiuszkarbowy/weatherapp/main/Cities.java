package com.example.arkadiuszkarbowy.weatherapp.main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arkadiuszkarbowy on 17/09/15.
 */
public class Cities {
    public static final String DEFAULT_CITY = "Wrocław";
    private static final Map<String, String> CITIES_IDS = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<>();
        result.put("Wrocław", "3081368");
        result.put("Konin", "3095321");
        result.put("Poznań", "3088171");
        result.put("Warszawa", "756135");
        result.put("Kraków", "3085041");

        return Collections.unmodifiableMap(result);
    }


    public static String getId(String name) {
        return CITIES_IDS.get(name);
    }

    public static String[] getNames() {
        return CITIES_IDS.keySet().toArray(new String[CITIES_IDS.size()]);
    }
}
