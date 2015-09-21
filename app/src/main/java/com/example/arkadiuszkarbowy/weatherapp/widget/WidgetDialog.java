package com.example.arkadiuszkarbowy.weatherapp.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arkadiuszkarbowy.weatherapp.main.Cities;
import com.example.arkadiuszkarbowy.weatherapp.R;

public class WidgetDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_dialog);

        ListView cities = (ListView) findViewById(R.id.cities);
        final String[] items = Cities.getNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout
                .support_simple_spinner_dropdown_item,
                items);

        cities.setAdapter(adapter);
        cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent updateLocation = new Intent(getApplicationContext(), Widget.class);
                updateLocation.setAction(Widget.ACTION_UPDATE_LOCATION);
                updateLocation.putExtra(Widget.DATA_CITY_NAME, items[position]);
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, updateLocation, PendingIntent
                        .FLAG_CANCEL_CURRENT);
                try {
                    pending.send();
                    setResult(RESULT_OK);
                    finish();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
