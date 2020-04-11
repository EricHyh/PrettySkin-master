package com.hyh.prettyskin.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yly.basetool.utils.PhoneStateUtil;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Administrator
 * @description
 * @data 2017/11/6
 */

public class ScreenReceiver extends BroadcastReceiver {

    private static ScreenReceiver sScreenReceiver;

    public static ScreenReceiver getInstance(Context context) {
        if (sScreenReceiver != null) {
            sScreenReceiver.tryToRegister(context.getApplicationContext());
            return sScreenReceiver;
        }
        synchronized (ScreenReceiver.class) {
            if (sScreenReceiver == null) {
                sScreenReceiver = new ScreenReceiver(context.getApplicationContext());
            }
        }
        return sScreenReceiver;
    }

    private CopyOnWriteArrayList<ScreenListener> mScreenListeners = new CopyOnWriteArrayList<>();

    private volatile boolean mIsRegister;

    private volatile boolean mIsScreenOn;

    private ScreenReceiver(Context context) {
        tryToRegister(context);
        mIsScreenOn = PhoneStateUtil.isScreenOn(context);
    }

    private void tryToRegister(Context context) {
        if (mIsRegister) {
            return;
        }
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
            mIsRegister = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isScreenOn() {
        return mIsScreenOn;
    }

    public void addListener(ScreenListener listener) {
        if (listener == null || mScreenListeners.contains(listener)) {
            return;
        }
        mScreenListeners.add(listener);
    }

    public void removeListener(ScreenListener listener) {
        mScreenListeners.remove(listener);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            mIsScreenOn = false;
            if (mScreenListeners.isEmpty()) {
                return;
            }
            for (ScreenListener screenListener : mScreenListeners) {
                if (screenListener != null) {
                    screenListener.onScreenOff(context, this, intent);
                }
            }
        } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            mIsScreenOn = true;
            if (mScreenListeners.isEmpty()) {
                return;
            }
            for (ScreenListener screenListener : mScreenListeners) {
                if (screenListener != null) {
                    screenListener.onScreenOn(context, this, intent);
                }
            }
        }
    }
}