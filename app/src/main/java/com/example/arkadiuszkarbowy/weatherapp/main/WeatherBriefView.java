package com.example.arkadiuszkarbowy.weatherapp.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arkadiuszkarbowy.weatherapp.R;

import java.util.ArrayList;

/**
 * Created by arkadiuszkarbowy on 22/09/15.
 */
public class WeatherBriefView extends LinearLayout {
    private Context mContext;
    private LinearLayout mIconTempContainer;
    private ImageView mCrrIcon;
    private TextView mCrrTemp;
    private FrameLayout mSeparationLine;
    private LinearLayout mDailyItemsContainer;
    private ArrayList<DayItemView> mDailyItems;

    public WeatherBriefView(Context context) {
        super(context);
        mContext = context;
        init();
        buildView();
    }

    public WeatherBriefView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        buildView();
    }

    public WeatherBriefView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        buildView();
    }

    private void init() {
        mCrrIcon = new ImageView(mContext);
        mCrrTemp = new TextView(mContext);
        mIconTempContainer = new LinearLayout(mContext);
        mSeparationLine = new FrameLayout(mContext);
        mDailyItemsContainer = new LinearLayout(mContext);
        mDailyItems = new ArrayList<>();
        mDailyItems.add(new DayItemView(mContext));
        mDailyItems.add(new DayItemView(mContext));
        mDailyItems.add(new DayItemView(mContext));
        customizeIconTempLayout();
        customizeDailyItemsLayout();
    }

    private void customizeIconTempLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        int color = getResources().getColor(R.color.red);
        mCrrIcon.setColorFilter(color);
        mCrrIcon.setLayoutParams(params);
        int p8 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.pad_8), getResources()
                .getDisplayMetrics());
        mCrrIcon.setPadding(p8, 0, p8, 0);

        mCrrTemp.setLayoutParams(params);
        mCrrTemp.setPadding(p8, 0, p8, 0);
        mCrrTemp.setTextColor(color);
        mCrrTemp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        mIconTempContainer.setLayoutParams(params);
        mIconTempContainer.setOrientation(VERTICAL);
    }

    private void customizeDailyItemsLayout() {
        LinearLayout.LayoutParams line = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources()
                .getDisplayMetrics()), LayoutParams.MATCH_PARENT);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                .getDisplayMetrics());
        line.setMargins(0, margin, margin, margin);
        mSeparationLine.setLayoutParams(line);
        mSeparationLine.setBackgroundColor(getResources().getColor(R.color.red));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mDailyItemsContainer.setLayoutParams(params);
    }

    private void buildView() {
        mIconTempContainer.addView(mCrrIcon);
        mIconTempContainer.addView(mCrrTemp);
        addView(mIconTempContainer);

        addView(mSeparationLine);

        for (DayItemView item : mDailyItems)
            mDailyItemsContainer.addView(item);

        addView(mDailyItemsContainer);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(HORIZONTAL);
    }

    public void setCurrentTemp(String temp) {
        mCrrTemp.setText(temp);
    }

    public void setCurrentIcon(int drawableId) {
        mCrrIcon.setImageResource(drawableId);
    }

    public DayItemView getDailyItem(int i) {
        return mDailyItems.get(i);
    }

    public void setDailyItemSelectedListener(OnClickListener mOnDaySelectedListener) {
        for (DayItemView item : mDailyItems)
            item.setOnClickListener(mOnDaySelectedListener);
    }

    public int indexOf(DayItemView mSelected) {
        return mDailyItems.indexOf(mSelected);
    }

    public void markSelections(DayItemView old, DayItemView fresh) {
        if (old != null)
            old.setBackgroundColor(0);
        fresh.setBackgroundColor(getResources().getColor(R.color.selection));
    }
}
