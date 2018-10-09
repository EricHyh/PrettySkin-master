package com.hyh.prettyskin.demo.base;

import android.app.Application;

import com.hyh.prettyskin.PrettySkin;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrettySkin.getInstance().init(this);
    }
}
