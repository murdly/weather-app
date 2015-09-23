package com.example.arkadiuszkarbowy.weatherapp.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arkadiuszkarbowy.weatherapp.R;

/**
 * Created by arkadiuszkarbowy on 22/09/15.
 */
public class DayItemView extends LinearLayout {
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageView mIcon;
    private TextView mName;
    private TextView mTempMax;
    private TextView mTempMin;
//        private FrameLayout mUnderline;

    public DayItemView(Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initDaily();

    }

    public DayItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initDaily();
    }

    public DayItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initDaily();
    }

    private void initDaily() {
        View v = mInflater.inflate(R.layout.daily, this);
        mIcon = (ImageView) v.findViewById(R.id.icon);
        mName = (TextView) v.findViewById(R.id.day_name);
        mTempMax = (TextView) v.findViewById(R.id.temp_max);
        mTempMin = (TextView) v.findViewById(R.id.temp_min);
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        super.setLayoutParams(p);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }

//    @Override
//    public void setGravity(int gravity) {
//        super.setGravity(Gravity.CENTER_HORIZONTAL);
//    }

    public void setIcon(int smallDrawableId) {
        mIcon.setImageResource(smallDrawableId);
    }

    public void setName(String name) {
        mName.setText(name);
    }

    public void setTempMax(String temp) {
        mTempMax.setText(temp);
    }

    public void setTempMin(String temp) {
        mTempMin.setText(temp);
    }

    public String getName() {
        return "jups";
    }
}
