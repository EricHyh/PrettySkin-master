package com.hyh.prettyskin.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.hyh.prettyskin.R;

/**
 * @author Administrator
 * @description
 * @data 2020/5/6
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    public CustomSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSwipeRefreshLayout);
        if (typedArray.hasValue(R.styleable.CustomSwipeRefreshLayout_progressBackground)) {
            int color = typedArray.getColor(R.styleable.CustomSwipeRefreshLayout_progressBackground, 0xFFFAFAFA);
            setProgressBackgroundColorSchemeColor(color);
        }
        if (typedArray.hasValue(R.styleable.CustomSwipeRefreshLayout_progressColorScheme)) {
            int color = typedArray.getColor(R.styleable.CustomSwipeRefreshLayout_progressColorScheme, Color.BLACK);
            setColorSchemeColors(color);
        }
        if (typedArray.hasValue(R.styleable.CustomSwipeRefreshLayout_progressColorSchemes)) {
            int resourceId = typedArray.getResourceId(R.styleable.CustomSwipeRefreshLayout_progressColorSchemes, 0);
            int[] colors = context.getResources().getIntArray(resourceId);
            setColorSchemeColors(colors);
        }
        typedArray.recycle();
    }
}