package com.example.arkadiuszkarbowy.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arkadiuszkarbowy.weatherapp.rest.model.Weather;
import com.example.arkadiuszkarbowy.weatherapp.rest.service.RestClient;

import java.io.IOException;
import java.net.URL;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestClient restClient = new RestClient();
        Call<Weather> call = restClient.getApiService().weather("3081368");
        call.enqueue(mCb);
    }

    Callback<Weather> mCb = new Callback<Weather>() {
        @Override
        public void onResponse(Response<Weather> response) {
            Weather w = response.body();
            Log.d("City", "id: " + w.id + " name: " + w.name + " temp: " + w.main.temp);
            Log.d("City", response.raw().toString());
        }

        @Override
        public void onFailure(Throwable t) {

            Log.d("City", "failure");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
