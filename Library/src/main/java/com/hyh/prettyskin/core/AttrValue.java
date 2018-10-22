package com.hyh.prettyskin.core;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @author Administrator
 * @description
 * @data 2018/10/18
 */

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
        return themeContextRef != null ? themeContextRef.get() : null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
