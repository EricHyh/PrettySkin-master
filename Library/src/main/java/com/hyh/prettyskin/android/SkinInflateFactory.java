package com.hyh.prettyskin.android;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class SkinInflateFactory implements LayoutInflater.Factory2 {


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Log.d("SkinInflateFactory", "onCreateView: ");
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }
}
