package com.hyh.prettyskin.sh.support;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatBackgroundSH;
import android.support.v7.widget.AppCompatImageSH;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ImageButtonSH;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */
@SuppressLint("RestrictedApi")
public class AppCompatImageButtonSH extends ImageButtonSH {

    private final AppCompatBackgroundSH mBackgroundSH;

    private final AppCompatImageSH mImageSH;

    public AppCompatImageButtonSH() {
        this(android.support.v7.appcompat.R.attr.imageButtonStyle);
    }

    public AppCompatImageButtonSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatImageButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
        mBackgroundSH = new AppCompatBackgroundSH(defStyleAttr);
        mImageSH = new AppCompatImageSH(defStyleAttr);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mBackgroundSH.isSupportAttrName(view, attrName)
                || mImageSH.isSupportAttrName(view, attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = super.parse(view, set, attrName);
        if (mBackgroundSH.isSupportAttrName(view, attrName)) {
            attrValue = mBackgroundSH.parse(view, set, attrName);
        }
        if (mImageSH.isSupportAttrName(view, attrName)) {
            attrValue = mImageSH.parse(view, set, attrName);
        }
        return attrValue;
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
        if (mBackgroundSH.isSupportAttrName(view, attrName)) {
            mBackgroundSH.replace(view, attrName, attrValue);
        }
        if (mImageSH.isSupportAttrName(view, attrName)) {
            mImageSH.replace(view, attrName, attrValue);
        }
    }
}