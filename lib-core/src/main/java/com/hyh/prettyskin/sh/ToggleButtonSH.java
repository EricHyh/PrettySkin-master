package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ToggleButton;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;



public class ToggleButtonSH extends CompoundButtonSH {

    private Class<?> mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;


    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("textOn");
        mSupportAttrNames.add("textOff");
        mSupportAttrNames.add("disabledAlpha");
    }

    public ToggleButtonSH() {
        //com.android.internal.R.attr.buttonStyleToggle
        this(ViewAttrUtil.getInternalStyleAttr("buttonStyleToggle"));
    }

    public ToggleButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ToggleButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof ToggleButton && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "ToggleButton";
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
        } else {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            ToggleButton toggleButton = (ToggleButton) view;
            switch (attrName) {
                case "textOn": {
                    CharSequence textOn = attrValue.getTypedValue(CharSequence.class, null);
                    toggleButton.setTextOn(textOn);
                    break;
                }
                case "textOff": {
                    CharSequence textOf = attrValue.getTypedValue(CharSequence.class, null);
                    toggleButton.setTextOff(textOf);
                    break;
                }
                case "disabledAlpha": {
                    float disabledAlpha = attrValue.getTypedValue(float.class, 0.5f);
                    Reflect.from(ToggleButton.class)
                            .filed("mDisabledAlpha", float.class)
                            .set(toggleButton, disabledAlpha);
                    break;
                }
            }
        }
    }
}
