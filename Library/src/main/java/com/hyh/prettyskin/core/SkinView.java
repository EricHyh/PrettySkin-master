package com.hyh.prettyskin.core;

import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.core.handler.ISkinHandler;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public class SkinView {

    private WeakReference<View> viewReference;

    private String attrName;

    private String attrValueKey;

    private Object defaultAttrValue;

    private Object currentAttrValue;

    public SkinView(View view, String attrName, String attrValueKey) {
        this.viewReference = new WeakReference<>(view);
        this.attrName = attrName;
        this.attrValueKey = attrValueKey;
    }

    public SkinView(View view, String attrName, String attrValueKey, Object defaultAttrValue) {
        this.viewReference = new WeakReference<>(view);
        this.attrName = attrName;
        this.attrValueKey = attrValueKey;
        this.defaultAttrValue = defaultAttrValue;
        this.currentAttrValue = defaultAttrValue;
    }

    public String getAttrValueKey() {
        return attrValueKey;
    }

    public void notifySkinChanged(int valueType, Object attrValue) {
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        List<ISkinHandler> skinHandlers = PrettySkin.getInstance().getSkinHandlers();
        for (ISkinHandler skinHandler : skinHandlers) {
            if (skinHandler.isSupportAttrName(view, attrName)) {
                skinHandler.replace(view, attrName, attrValue);
                currentAttrValue = attrValue;
            }
        }
    }

    public void notifySkinRecovered() {
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        List<ISkinHandler> skinHandlers = PrettySkin.getInstance().getSkinHandlers();
        for (ISkinHandler skinHandler : skinHandlers) {
            if (skinHandler.isSupportAttrName(view, attrName)) {
                skinHandler.replace(view, attrName, defaultAttrValue);
                currentAttrValue = defaultAttrValue;
            }
        }
    }

    public boolean isRecycled() {
        return viewReference.get() == null;
    }
}
