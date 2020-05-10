package com.hyh.prettyskin;

import android.content.Context;

import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.lang.ref.WeakReference;



public class AttrValue {

    private WeakReference<Context> themeContextRef;

    private int type;

    private Object value;

    public AttrValue(Context themeContext, int type, Object value) {
        this.themeContextRef = new WeakReference<>(themeContext);
        this.type = type;
        this.value = value;
    }

    public Context getThemeContext() {
        return themeContextRef.get();
    }

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public <T> T getTypedValue(Class<T> valueClass, T defaultValue) {
        return ViewAttrUtil.getTypedValue(this, valueClass, defaultValue);
    }
}