package com.hyh.prettyskin.sh.support;

import android.support.v7.widget.AppCompatBackgroundSH;
import android.support.v7.widget.AppCompatTextAutoSizeSH;
import android.support.v7.widget.AppCompatTextSH;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ButtonSH;



public class AppCompatButtonSH extends ButtonSH {

    private final AppCompatBackgroundSH mBackgroundSH;
    private final AppCompatTextSH mTextSH;
    private final AppCompatTextAutoSizeSH mTextAutoSizeSH;

    public AppCompatButtonSH() {
        this(android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public AppCompatButtonSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
        mBackgroundSH = new AppCompatBackgroundSH(defStyleAttr);
        mTextSH = new AppCompatTextSH(defStyleAttr);
        mTextAutoSizeSH = new AppCompatTextAutoSizeSH(defStyleAttr);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mBackgroundSH.isSupportAttrName(view, attrName)
                || mTextSH.isSupportAttrName(view, attrName)
                || mTextAutoSizeSH.isSupportAttrName(view, attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mBackgroundSH.isSupportAttrName(view, attrName)) {
            return mBackgroundSH.parse(view, set, attrName);
        } else if (mTextSH.isSupportAttrName(view, attrName)) {
            return mTextSH.parse(view, set, attrName);
        } else if (mTextAutoSizeSH.isSupportAttrName(view, attrName)) {
            return mTextAutoSizeSH.parse(view, set, attrName);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (mBackgroundSH.isSupportAttrName(view, attrName)) {
            mBackgroundSH.replace(view, attrName, attrValue);
        } else if (mTextSH.isSupportAttrName(view, attrName)) {
            mTextSH.replace(view, attrName, attrValue);
        } else if (mTextAutoSizeSH.isSupportAttrName(view, attrName)) {
            mTextAutoSizeSH.replace(view, attrName, attrValue);
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}
