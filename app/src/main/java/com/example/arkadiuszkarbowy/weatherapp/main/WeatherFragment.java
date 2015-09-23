package com.example.arkadiuszkarbowy.weatherapp.main;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by arkadiuszkarbowy on 18/09/15.
 */
public abstract class WeatherFragment extends Fragment{
    public static final int ACTION_CURRENT_DATA = 1;
    public static final int ACTION_HISTORY_DATA = 2;


    protected OnFragmentDataListener mListener;

    abstract void updateDataFor(String city);

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
}
