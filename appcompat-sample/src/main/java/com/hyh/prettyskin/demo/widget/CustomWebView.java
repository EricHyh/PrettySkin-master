package com.hyh.prettyskin.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * @author Administrator
 * @description
 * @data 2019/1/4
 */

public class CustomWebView extends WebView {

    private OnTouchListener mWebTouchListener;

    public CustomWebView(Context context) {
        super(context.getApplicationContext());
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context.getApplicationContext(), attrs, defStyleAttr);
    }

    public void setWebTouchListener(OnTouchListener webTouchListener) {
        mWebTouchListener = webTouchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mWebTouchListener != null) mWebTouchListener.onTouch(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}