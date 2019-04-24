package com.hyh.prettyskin;

import android.view.View;

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

    //attrName --> attrValueKey
    private Map<String, String> attrNameMap;

    //attrName --> AttrValue
    private Map<String, AttrValue> defaultAttrValueMap;

    public SkinView(View view, Map<String, String> attrNameMap, Map<String, AttrValue> defaultAttrValueMap) {
        this.viewReference = new WeakReference<>(view);
        this.attrNameMap = attrNameMap;
        this.defaultAttrValueMap = defaultAttrValueMap;
    }

    public View getView() {
        return viewReference == null ? null : viewReference.get();
    }

    public boolean isRecycled() {
        return viewReference.get() == null;
    }

    public void changeSkin(ISkin skin) {
        if (skin == null || attrNameMap == null || attrNameMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler == null) {
            return;
        }
        Set<Map.Entry<String, String>> entrySet = attrNameMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String attrName = entry.getKey();
            String attrValueKey = entry.getValue();
            if (skinHandler.isSupportAttrName(view, attrName)) {
                AttrValue attrValue = skin.getAttrValue(attrValueKey);
                if (attrValue != null) {
                    skinHandler.replace(view, attrName, attrValue);
                }
            }
        }
    }

    public void recoverSkin() {
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