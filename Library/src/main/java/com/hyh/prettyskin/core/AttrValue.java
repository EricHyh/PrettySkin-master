package com.hyh.prettyskin.core;

import android.content.res.Resources;

/**
 * @author Administrator
 * @description
 * @data 2018/10/18
 */

public class AttrValue {

    private Resources resources;

    private int type;

    private Object value;

    public AttrValue(Resources resources, int type, Object value) {
        this.resources = resources;
        this.type = type;
        this.value = value;
    }

    public Resources getResources() {
        return resources;
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
