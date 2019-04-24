package com.hyh.prettyskin;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hyh.prettyskin.sh.AppCompatButtonSH;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class AppCompatSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.put(AppCompatButton.class, new AppCompatButtonSH());
    }

    @Override
    public Map<Class<? extends View>, ISkinHandler> get() {
        return mSkinHandlerMap;
    }
}