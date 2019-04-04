package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/7
 */

public class CompoundButtonSH extends ButtonSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "CompoundButton";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("buttonTintMode");
        mSupportAttrNames.add("buttonTint");
        mSupportAttrNames.add("checked");
    }

    public CompoundButtonSH() {
    }

    public CompoundButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CompoundButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof CompoundButton && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
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
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            CompoundButton compoundButton = (CompoundButton) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "buttonTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        compoundButton.setButtonTintMode(tintMode);
                    }
                    break;
                }
                case "buttonTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ColorStateList buttonTint = ViewAttrUtil.getColorStateList(resources, type, value);
                        compoundButton.setButtonTintList(buttonTint);
                    }
                    break;
                }
                case "checked": {
                    boolean checked = ViewAttrUtil.getBoolean(resources, type, value);
                    compoundButton.setChecked(checked);
                    break;
                }
            }
        }
    }
}
