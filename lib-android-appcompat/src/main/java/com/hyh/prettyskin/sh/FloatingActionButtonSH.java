package com.hyh.prettyskin.sh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.design.animation.MotionSpec;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;

/**
 * @author Administrator
 * @description
 * @data 2020/4/20
 */
public class FloatingActionButtonSH extends ImageButtonSH {

    public FloatingActionButtonSH() {
        this(android.support.design.R.attr.floatingActionButtonStyle);
    }

    public FloatingActionButtonSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public FloatingActionButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }


    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof FloatingActionButton) &&
                (
                        TextUtils.equals(attrName, "backgroundTint")
                                || TextUtils.equals(attrName, "backgroundTintMode")
                                || TextUtils.equals(attrName, "rippleColor")
                                || TextUtils.equals(attrName, "fabSize")
                                || TextUtils.equals(attrName, "fabCustomSize")
                                //|| TextUtils.equals(attrName, "borderWidth")
                                || TextUtils.equals(attrName, "elevation")
                                || TextUtils.equals(attrName, "hoveredFocusedTranslationZ")
                                || TextUtils.equals(attrName, "pressedTranslationZ")
                                || TextUtils.equals(attrName, "useCompatPadding")
                                //|| TextUtils.equals(attrName, "maxImageSize")
                                || TextUtils.equals(attrName, "showMotionSpec")
                                || TextUtils.equals(attrName, "hideMotionSpec")
                )) || super.isSupportAttrName(view, attrName);
    }

    @SuppressLint("PrivateResource")
    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        Context context = view.getContext();
        FloatingActionButton button = (FloatingActionButton) view;
        switch (attrName) {
            case "backgroundTint": {
                ColorStateList backgroundTintList = button.getBackgroundTintList();
                return new AttrValue(context, ValueType.TYPE_COLOR_STATE_LIST, backgroundTintList);
            }
            case "backgroundTintMode": {
                PorterDuff.Mode tintMode = button.getBackgroundTintMode();
                return new AttrValue(context, ValueType.TYPE_OBJECT, tintMode);
            }
            case "rippleColor": {
                ColorStateList rippleColor = button.getRippleColorStateList();
                return new AttrValue(context, ValueType.TYPE_COLOR_STATE_LIST, rippleColor);
            }
            case "fabSize": {
                int fabSize = button.getSize();
                return new AttrValue(context, ValueType.TYPE_INT, fabSize);
            }
            case "fabCustomSize": {
                int fabCustomSize = button.getCustomSize();
                return new AttrValue(context, ValueType.TYPE_INT, fabCustomSize);
            }
            case "elevation": {
                float elevation = button.getCompatElevation();
                return new AttrValue(context, ValueType.TYPE_FLOAT, elevation);
            }
            case "hoveredFocusedTranslationZ": {
                float hoveredFocusedTranslationZ = button.getCompatHoveredFocusedTranslationZ();
                return new AttrValue(context, ValueType.TYPE_FLOAT, hoveredFocusedTranslationZ);
            }
            case "pressedTranslationZ": {
                float pressedTranslationZ = button.getCompatPressedTranslationZ();
                return new AttrValue(context, ValueType.TYPE_FLOAT, pressedTranslationZ);
            }
            case "useCompatPadding": {
                boolean useCompatPadding = button.getUseCompatPadding();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, useCompatPadding);
            }
            case "showMotionSpec": {
                MotionSpec showMotionSpec = button.getShowMotionSpec();
                return new AttrValue(context, ValueType.TYPE_OBJECT, showMotionSpec);
            }
            case "hideMotionSpec": {
                MotionSpec hideMotionSpec = button.getHideMotionSpec();
                return new AttrValue(context, ValueType.TYPE_OBJECT, hideMotionSpec);
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        FloatingActionButton button = (FloatingActionButton) view;
        switch (attrName) {
            case "backgroundTint": {
                button.setBackgroundTintList(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "backgroundTintMode": {
                button.setBackgroundTintMode(attrValue.getTypedValue(PorterDuff.Mode.class, null));
                break;
            }
            case "rippleColor": {
                button.setRippleColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "fabSize": {
                button.setSize(attrValue.getTypedValue(int.class, -1));
                break;
            }
            case "fabCustomSize": {
                button.setCustomSize(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "elevation": {
                button.setCompatElevation(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "hoveredFocusedTranslationZ": {
                button.setCompatHoveredFocusedTranslationZ(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "pressedTranslationZ": {
                button.setCompatPressedTranslationZ(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "useCompatPadding": {
                button.setUseCompatPadding(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "showMotionSpec": {
                button.setShowMotionSpec(attrValue.getTypedValue(MotionSpec.class, null));
                break;
            }
            case "hideMotionSpec": {
                button.setHideMotionSpec(attrValue.getTypedValue(MotionSpec.class, null));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}