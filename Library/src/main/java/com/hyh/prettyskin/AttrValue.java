package com.hyh.prettyskin;

import android.content.Context;

/**
 * @author Administrator
 * @description
 * @data 2018/10/18
 */

public class AttrValue {

    private Context themeContext;

    private int type;

    private Object value;

    public AttrValue(Context themeContext, int type, Object value) {
        this.themeContext = themeContext;
        this.type = type;
        this.value = value;
    }

    public Context getThemeContext() {
        return themeContext;
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
