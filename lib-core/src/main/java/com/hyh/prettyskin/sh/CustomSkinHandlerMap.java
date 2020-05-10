package com.hyh.prettyskin.sh;

import android.view.View;

import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ISkinHandlerMap;

import java.util.HashMap;
import java.util.Map;


public class CustomSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class, ISkinHandler> mMap = new HashMap<>();

    @Override
    public ISkinHandler get(Class viewClass) {
        return mMap.get(viewClass);
    }

    void addSkinHandler(Class<? extends View> viewClass, ISkinHandler skinHandler) {
        mMap.put(viewClass, skinHandler);
    }
}