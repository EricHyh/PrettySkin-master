package com.hyh.prettyskin.sh;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;

/**
 * @author Administrator
 * @description
 * @data 2018/11/13
 */

public class AppCompatTextViewSH extends TextViewSH {

    public AppCompatTextViewSH() {
        this(android.R.attr.textViewStyle);
    }

    public AppCompatTextViewSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatTextViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        return super.parse(view, set, attrName);
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}