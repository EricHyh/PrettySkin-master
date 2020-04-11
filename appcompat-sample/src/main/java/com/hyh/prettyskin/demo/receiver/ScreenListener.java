package com.hyh.prettyskin.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Administrator
 * @description
 * @data 2018/7/23
 */

public interface ScreenListener {

    void onScreenOn(Context context, BroadcastReceiver receiver, Intent intent);

    void onScreenOff(Context context, BroadcastReceiver receiver, Intent intent);

}
