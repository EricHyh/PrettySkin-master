package com.hyh.prettyskin.sh;

import android.view.View;

import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ISkinHandlerMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/15
 */
public class SkinHandlerMaps implements ISkinHandlerMap {

    private final List<ISkinHandlerMap> mSkinHandlerMaps = new ArrayList<>();
    private final CustomSkinHandlerMap mCustomSkinHandlerMap = new CustomSkinHandlerMap();

    public SkinHandlerMaps() {
        mSkinHandlerMaps.add(mCustomSkinHandlerMap);
        mSkinHandlerMaps.add(new NativeSkinHandlerMap());
    }

    public void addSkinHandlerMap(ISkinHandlerMap skinHandlerMap) {
        if (skinHandlerMap == null || mSkinHandlerMaps.contains(skinHandlerMap)) return;
        mSkinHandlerMaps.add(skinHandlerMap);
    }

    public void addCustomSkinHandler(Class<? extends View> viewClass, ISkinHandler skinHandler) {
        mCustomSkinHandlerMap.addSkinHandler(viewClass, skinHandler);
    }

    @Override
    public ISkinHandler get(Class viewClass) {
        if (viewClass == null || !View.class.isAssignableFrom(viewClass)) {
            return null;
        }
        for (ISkinHandlerMap skinHandlerMap : mSkinHandlerMaps) {
            ISkinHandler skinHandler = skinHandlerMap.get(viewClass);
            if (skinHandler != null) {
                return skinHandler;
            }
        }
        return get(viewClass.getSuperclass());
    }
}
