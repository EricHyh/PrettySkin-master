package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * Created by Eric_He on 2018/12/4.
 */

public class ChronometerSH extends TextViewSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    private TypedArray mTypedArray;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "Chronometer";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }


    public ChronometerSH() {
    }

    public ChronometerSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ChronometerSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parse(view, set, attrName);
        } else {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
        }
    }

    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
