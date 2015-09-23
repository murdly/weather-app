package com.example.arkadiuszkarbowy.weatherapp.main;

import com.example.arkadiuszkarbowy.weatherapp.rest.model.City;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Detail;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arkadiuszkarbowy on 17/09/15.
 */
public class Forecast3 {
    private City city;
    private Day day1, day2, day3;


    public Forecast3(Forecast combined) {
        city = combined.getCity();
        split(combined);
    }

    public void split(Forecast f) {
        int endDay1 = range(0, f);
        int endDay2 = range(endDay1 + 1, f);
        int endDay3 = range(endDay2 + 1, f);
        day1 = new Day(f.getList().subList(0, endDay1));
        day2 = new Day(f.getList().subList(endDay1, endDay2));
        day3 = new Day(f.getList().subList(endDay2, endDay3));
    }

    private static int range(int pos, Forecast f) {
        int dayOfMonth = f.getList().get(pos).getNumericDay();
        int end = -1;
        while (pos < f.getList().size() && end == -1) {
            if (f.getList().get(pos).getNumericDay() != dayOfMonth)
                end = pos;
            pos++;
        }

        return end;
    }

    public Day getDay1() {
        return day1;
    }

    public Day getDay2() {
        return day2;
    }

    public Day getDay3() {
        return day3;
    }

    public ArrayList<Day> get3Days() {
        ArrayList<Day> days = new ArrayList<>();
        days.add(day1);
        days.add(day2);
        days.add(day3);
        return days;
    }

    public Day getDay(int i) {
        return get3Days().get(i);
    }

    public class Day {
        private List<Detail> details;
        private Map<String, Float> temps;
        private int tempMax;
        private int tempMin;

        public Day(List<Detail> details) {
            this.details = details;
            temps = new LinkedHashMap<>();
            tempMax = countMax();
            tempMin = countMin();
        }

        public Map<String, Float> collectDailyTemps() {
            for (Detail d : details)
                temps.put(d.getTimeHm(), (float) d.getTemp());
            return temps;
        }

        private int countMax() {
            return Collections.max(details, mTempComparator).getTemp();
        }

        private int countMin() {
            return Collections.min(details, mTempComparator).getTemp();
        }

        Comparator<Detail> mTempComparator = new Comparator<Detail>() {
            @Override
            public int compare(Detail lhs, Detail rhs) {
                return lhs.getTemp() - rhs.getTemp();
            }
        };

        public String getIconCode() {
            return details.get(0).getIconCode();
        }

        public String getDayName() {
            return details.get(0).getDayName();
        }

        public int getTempMax() {
            return tempMax;
        }

        public int getTempMin() {
            return tempMin;
        }

        public double getAvgWindSpeed() {
            double total = 0;
            for (Detail detail : details)
                total += detail.getWind().getSpeed();

            return round(total / details.size());
        }

        public double getAvgPressure() {
            double total = 0;
            for (Detail detail : details)
                total += detail.getMain().getPressure();

            return round(total / details.size());
        }

        public int getAvgHumidity() {
            int total = 0;
            for (Detail detail : details)
                total += detail.getMain().getHumidity();

            return total / details.size();
        }

        private double round(double val) {
            return Math.round(val * 100) / 100;
        }
    }
}