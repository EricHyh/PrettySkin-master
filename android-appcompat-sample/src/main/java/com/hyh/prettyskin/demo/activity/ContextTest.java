package com.hyh.prettyskin.demo.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

/**
 * @author Administrator
 * @description
 * @data 2019/5/18
 */

public class ContextTest extends ContextWrapper {

    private LayoutInflater mInflater;

    public ContextTest(Context base) {
        super(base);
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return mInflater;
        }
        return getBaseContext().getSystemService(name);
    }
}
