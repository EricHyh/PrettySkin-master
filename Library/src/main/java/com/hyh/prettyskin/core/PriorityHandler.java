package com.hyh.prettyskin.core;


import android.os.Handler;
import android.os.Looper;

/**
 * @author Administrator
 * @description
 * @data 2019/4/4
 */

public class PriorityHandler {

    private static PriorityHandler sInstance = new PriorityHandler();

    private Handler handler = new Handler(Looper.getMainLooper());

    private Integer mTopActivityHashCode;

    public static PriorityHandler getInstance() {
        return sInstance;
    }

    public void postSkinChanged(SkinView skinView, Runnable runnable) {

    }

    public void setTopActivityHashCode(int hashCode) {
        mTopActivityHashCode = hashCode;
    }

    public void removeTopActivityHashCode(int hashCode) {
        if (mTopActivityHashCode != null && mTopActivityHashCode == hashCode) {
            mTopActivityHashCode = null;
        }
    }
}