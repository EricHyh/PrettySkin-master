package com.hyh.prettyskin.core;

import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.ViewReferenceUtil;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public class SkinView {

    private final Reference<View> viewReference;

    //attrValueKey --> attrName
    private final Map<String, String> attrNameMap;

    //attrName --> AttrValue
    private final Map<String, AttrValue> defaultAttrValueMap;

    public SkinView(View view, Map<String, String> attrNameMap, Map<String, AttrValue> defaultAttrValueMap) {
        this.viewReference = ViewReferenceUtil.createViewReference(this, view);
        this.attrNameMap = attrNameMap;
        this.defaultAttrValueMap = defaultAttrValueMap;
    }

    public View getView() {
        return viewReference == null ? null : viewReference.get();
    }

    public boolean isRecycled() {
        return viewReference == null || viewReference.get() == null;
    }

    public boolean hasAttrValueKey(String attrValueKey) {
        return attrNameMap != null && !attrNameMap.isEmpty() && attrNameMap.containsKey(attrValueKey);
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
        Set<Map.Entry<String, String>> entrySet = attrNameMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String attrValueKey = entry.getKey();
            String attrName = entry.getValue();
            AttrValue attrValue = skin.getAttrValue(attrValueKey);
            skinHandler.replace(view, attrName, attrValue);
        }
    }

    /*public void changeSkin(SkinAttr skinAttr) {
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
    }*/

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