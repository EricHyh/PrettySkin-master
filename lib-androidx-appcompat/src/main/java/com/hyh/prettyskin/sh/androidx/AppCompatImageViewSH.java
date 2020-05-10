package com.hyh.prettyskin.sh.androidx;

import android.annotation.SuppressLint;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ImageViewSH;

import androidx.appcompat.widget.AppCompatBackgroundSH;
import androidx.appcompat.widget.AppCompatImageSH;

@SuppressLint("RestrictedApi")
public class AppCompatImageViewSH extends ImageViewSH {

    private final AppCompatBackgroundSH mBackgroundSH;

    private final AppCompatImageSH mImageSH;

    public AppCompatImageViewSH() {
        this(0);
    }

    public AppCompatImageViewSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatImageViewSH(int defStyleAttr, int defStyleRes) {
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
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mBackgroundSH.prepareParse(view, set);
        mImageSH.prepareParse(view, set);
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
    public void finishParse() {
        super.finishParse();
        mBackgroundSH.finishParse();
        mImageSH.finishParse();
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
