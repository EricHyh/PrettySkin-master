package com.hyh.prettyskin.sh.androidx;

import android.annotation.SuppressLint;

import com.hyh.prettyskin.sh.SeekBarSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */
@SuppressLint("RestrictedApi")
public class AppCompatSeekBarSH extends SeekBarSH {

    public AppCompatSeekBarSH() {
        super(ViewAttrUtil.getStyleAttr("androidx.appcompat.R", "seekBarStyle"));
    }

    public AppCompatSeekBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatSeekBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
