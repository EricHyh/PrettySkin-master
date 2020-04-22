package com.hyh.prettyskin.sh;

import android.support.v7.widget.AppCompatCompoundButtonSH;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatCheckBoxSH extends CheckBoxSH {

    private AppCompatCompoundButtonSH mAppCompatCompoundButtonSH;

    public AppCompatCheckBoxSH() {
        this(android.support.v7.appcompat.R.attr.checkboxStyle);
    }

    public AppCompatCheckBoxSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatCheckBoxSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
        mAppCompatCompoundButtonSH = new AppCompatCompoundButtonSH(defStyleAttr);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mAppCompatCompoundButtonSH.isSupportAttrName(view, attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mAppCompatCompoundButtonSH.isSupportAttrName(view, attrName)) {
            return mAppCompatCompoundButtonSH.parse(view, set, attrName);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (mAppCompatCompoundButtonSH.isSupportAttrName(view, attrName)) {
            mAppCompatCompoundButtonSH.replace(view, attrName, attrValue);
        } else {
            super.replace(view, attrName, attrValue);
        }
    }

}
