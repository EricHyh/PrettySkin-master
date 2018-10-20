package com.hyh.prettyskin.core;

import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.core.handler.ISkinHandler;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public class SkinView {

    private WeakReference<View> viewReference;

    private Map<String, ViewAttr> viewAttrMap;

    public SkinView(View view, Map<String, ViewAttr> viewAttrMap) {
        this.viewReference = new WeakReference<>(view);
        this.viewAttrMap = viewAttrMap;
    }

    public boolean isRecycled() {
        return viewReference.get() == null;
    }

    public boolean hasAttrValueKey(String attrValueKey) {
        if (viewAttrMap != null && !viewAttrMap.isEmpty()) {
            Set<String> keySet = viewAttrMap.keySet();
            return keySet.contains(attrValueKey);
        }
        return false;
    }


    public void notifySkinChanged(SkinAttr skinAttr) {
        if (viewAttrMap == null || viewAttrMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        String attrValueKey = skinAttr.getAttrValueKey();
        ViewAttr viewAttr = viewAttrMap.get(attrValueKey);
        if (viewAttr == null) {
            return;
        }
        String attrName = viewAttr.getAttrName();
        AttrValue attrValue = skinAttr.getAttrValue();
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler != null && skinHandler.isSupportAttrName(view, attrName)) {
            skinHandler.replace(view, attrName, skinAttr.getAttrValue());
            viewAttr.setCurrentAttrValue(attrValue);
        }
    }

    public void notifySkinRecovered() {
        if (viewAttrMap == null || viewAttrMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler != null) {
            Collection<ViewAttr> viewAttrs = viewAttrMap.values();
            for (ViewAttr viewAttr : viewAttrs) {
                String attrName = viewAttr.getAttrName();
                AttrValue defaultAttrValue = viewAttr.getDefaultAttrValue();
                if (skinHandler.isSupportAttrName(view, attrName)) {
                    skinHandler.replace(view, attrName, defaultAttrValue);
                    viewAttr.setCurrentAttrValue(defaultAttrValue);
                }
            }
        }
    }
}
