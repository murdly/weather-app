package com.example.arkadiuszkarbowy.weatherapp.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkadiuszkarbowy.weatherapp.R;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Forecast;
import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.ApiService;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class HistoryFragment extends WeatherFragment {
    private TextView mDate;
    private String mCity;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Button mSearch = (Button) view.findViewById(R.id.show);
        mSearch.setOnClickListener(onHistoryDataListener);
        mDate = (TextView) view.findViewById(R.id.date);
        mDate.setOnClickListener(new DateListener());
        mListener.hideViews();
        return view;
    }

    private Button.OnClickListener onHistoryDataListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dateSet()) {
                Call<Forecast> call = new RestClient().getApiService().forecast(Cities.getId(mCity), ApiService
                        .UNITS);
                call.enqueue(mHistoryDumb);
            } else {
                Toast.makeText(getActivity(), getString(R.string.choose_date), Toast.LENGTH_SHORT).show();
            }
        }

        private boolean dateSet() {
            return !mDate.getText().toString().isEmpty();
        }
    };

    private Callback<Forecast> mHistoryDumb = new Callback<Forecast>() {
        @Override
        public void onResponse(Response<Forecast> response) {
            Log.d("HistoryFragment", response.raw().toString());
            Forecast3 dailyForecast = new Forecast3(response.body());
            mListener.onChartUpdate(dailyForecast.getDay3().collectDailyTemps());
            mListener.onWeatherInfoUpdate(new Weather(23d, 13d, 5d, 999d, 55));
            mListener.showViews();
        }

        @Override
        public void onFailure(Throwable t) {
            mListener.hideViews();
            Toast.makeText(getActivity(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void updateDataFor(String city) {
        mCity = city;
    }

    private class DateListener implements View.OnClickListener {
        DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                String date = new SimpleDateFormat("EEE, MMM d, ''yy").format(c.getTime());
                mDate.setText(date);
            }
        };

        @Override
        public void onClick(View v) {
            DatePickerFragment.newInstance(mDateListener).show(getFragmentManager(), "date");
        }
    }

    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener onDateSetListener;

        static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener onDateSetListener) {
            DatePickerFragment pickerFragment = new DatePickerFragment();
            pickerFragment.setOnDateSetListener(onDateSetListener);
            return pickerFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        }

        private void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
            this.onDateSetListener = listener;
        }
    }
}