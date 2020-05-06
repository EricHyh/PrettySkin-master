package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/12/4.
 */

public class ChronometerSH extends TextViewSH {

    private Class mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private TypedArray mTypedArray;

    private final List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("format");
        mSupportAttrNames.add("countDown");
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
        return view instanceof Chronometer && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "Chronometer";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }

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
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof Chronometer) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Chronometer chronometer = (Chronometer) view;
            switch (attrName) {
                case "format": {
                    String format = attrValue.getTypedValue(String.class, null);
                    chronometer.setFormat(format);
                    break;
                }
                case "countDown": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        boolean countDown = attrValue.getTypedValue(boolean.class, false);
                        chronometer.setCountDown(countDown);
                    }
                    break;
                }
            }
        }
    }
}
