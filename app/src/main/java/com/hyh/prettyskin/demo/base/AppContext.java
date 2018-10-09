package com.hyh.prettyskin.demo.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.hyh.prettyskin.android.SkinInflateFactory;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater.from(this).setFactory2(new SkinInflateFactory());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
