package com.hyh.prettyskin.sh;

import android.annotation.SuppressLint;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */
@SuppressLint("RestrictedApi")
public class AppCompatSeekBarSH extends SeekBarSH {

    public AppCompatSeekBarSH() {
        super(android.support.v7.appcompat.R.attr.seekBarStyle);
    }

    public AppCompatSeekBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatSeekBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
