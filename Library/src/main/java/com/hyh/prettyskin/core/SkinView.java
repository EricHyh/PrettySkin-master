package com.hyh.prettyskin.core;

import android.text.TextUtils;
import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.core.handler.ISkinHandler;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public class SkinView {

    private WeakReference<View> viewReference;

    //attrValueKey --> attrName
    private Map<String, String> attrNameMap;

    //attrName --> AttrValue
    private Map<String, AttrValue> defaultAttrValueMap;

    public SkinView(View view, Map<String, String> attrNameMap, Map<String, AttrValue> defaultAttrValueMap) {
        this.viewReference = new WeakReference<>(view);
        this.attrNameMap = attrNameMap;
        this.defaultAttrValueMap = defaultAttrValueMap;
    }

    public boolean isRecycled() {
        return viewReference.get() == null;
    }

    public boolean hasAttrValueKey(String attrValueKey) {
        return attrNameMap != null && !attrNameMap.isEmpty() && attrNameMap.containsKey(attrValueKey);
    }


    public void notifySkinChanged(ISkin skin) {
        if (skin == null || attrNameMap == null || attrNameMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        Set<Map.Entry<String, String>> entrySet = attrNameMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String attrValueKey = entry.getKey();
            String attrName = entry.getValue();
            AttrValue attrValue = skin.getAttrValue(attrValueKey);
            skinHandler.replace(view, attrName, attrValue);
        }
    }

    public void notifySkinChanged(SkinAttr skinAttr) {
        if (attrNameMap == null || attrNameMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        String attrValueKey = skinAttr.getAttrValueKey();
        String attrName = attrNameMap.get(attrValueKey);
        if (!TextUtils.isEmpty(attrName)) {
            AttrValue attrValue = skinAttr.getAttrValue();
            ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
            if (skinHandler != null && skinHandler.isSupportAttrName(view, attrName)) {
                skinHandler.replace(view, attrName, attrValue);
            }
        }
    }

    public void notifySkinRecovered() {
        if (defaultAttrValueMap == null || defaultAttrValueMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler != null) {

            Set<Map.Entry<String, AttrValue>> entrySet = defaultAttrValueMap.entrySet();
            for (Map.Entry<String, AttrValue> entry : entrySet) {
                String attrName = entry.getKey();
                AttrValue defaultAttrValue = entry.getValue();
                if (skinHandler.isSupportAttrName(view, attrName)) {
                    skinHandler.replace(view, attrName, defaultAttrValue);
                }
            }
        }
    }
}
