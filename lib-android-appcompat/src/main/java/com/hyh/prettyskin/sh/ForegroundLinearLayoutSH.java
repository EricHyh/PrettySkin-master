package com.hyh.prettyskin.sh;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.design.internal.ForegroundLinearLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;

/**
 * @author Administrator
 * @description
 * @data 2020/4/23
 */
@SuppressLint("RestrictedApi")
public class ForegroundLinearLayoutSH extends ViewGroupSH {

    public ForegroundLinearLayoutSH() {
    }

    public ForegroundLinearLayoutSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ForegroundLinearLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof ForegroundLinearLayout) &&
                (
                        TextUtils.equals(attrName, "foregroundGravity")
                                || TextUtils.equals(attrName, "foreground")
                )) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        ForegroundLinearLayout layout = (ForegroundLinearLayout) view;
        switch (attrName) {
            case "foregroundGravity": {
                int foregroundGravity = layout.getForegroundGravity();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, foregroundGravity);
            }
            case "foreground": {
                Drawable foreground = layout.getForeground();
                return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, foreground);
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        ForegroundLinearLayout layout = (ForegroundLinearLayout) view;
        switch (attrName) {
            case "foregroundGravity": {
                layout.setForegroundGravity(attrValue.getTypedValue(int.class, Gravity.FILL));
                break;
            }
            case "foreground": {
                layout.setForeground(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }

    }
}