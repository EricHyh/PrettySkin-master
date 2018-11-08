package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ReflectUtil;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/7
 */

public class SwitchSH extends CompoundButtonSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    public SwitchSH() {
        this(ViewAttrUtil.getDefStyleAttr("switchStyle"));//com.android.internal.R.attr.switchStyle
    }

    public SwitchSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public SwitchSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof Switch && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parseAttrValue(view, set, attrName);
        } else {
            Class styleableClass = getStyleableClass();
            String styleableName = getStyleableName();
            return parseAttrValue(view, set, attrName, styleableClass, styleableName);
        }
    }

    private Class getStyleableClass() {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (Exception e) {
            return null;
        }
    }

    private String getStyleableName() {
        return "Switch";
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof CompoundButton) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            Switch switchView = (Switch) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "thumb": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Drawable thumb = ViewAttrUtil.getDrawable(resources, type, value);
                        switchView.setThumbDrawable(thumb);
                    }
                    break;
                }
                case "track": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Drawable track = ViewAttrUtil.getDrawable(resources, type, value);
                        switchView.setTrackDrawable(track);
                    }
                    break;
                }
                case "textOn": {
                    CharSequence textOn = ViewAttrUtil.getCharSequence(resources, type, value);
                    switchView.setTextOn(textOn);
                    break;
                }
                case "textOff": {
                    CharSequence textOff = ViewAttrUtil.getCharSequence(resources, type, value);
                    switchView.setTextOff(textOff);
                    break;
                }
                case "showText": {
                    boolean showText = ViewAttrUtil.getBoolean(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        switchView.setShowText(showText);
                    } else {
                        Object mShowText = ReflectUtil.getFieldValue(switchView, "mShowText");
                        if (mShowText != null && mShowText instanceof Boolean && mShowText != showText) {
                            ReflectUtil.setFieldValue(switchView, "mShowText", showText);
                            switchView.requestLayout();
                        }
                    }
                    break;
                }
                case "thumbTextPadding": {
                    int thumbTextPadding = ViewAttrUtil.getInt(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        switchView.setThumbTextPadding(thumbTextPadding);
                    } else {
                        ReflectUtil.setFieldValue(switchView, "mThumbTextPadding", thumbTextPadding);
                        switchView.requestLayout();
                    }
                    break;
                }
                case "switchMinWidth": {
                    int switchMinWidth = ViewAttrUtil.getInt(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        switchView.setSwitchMinWidth(switchMinWidth);
                    } else {
                        ReflectUtil.setFieldValue(switchView, "mSwitchMinWidth", switchMinWidth);
                        switchView.requestLayout();
                    }
                    break;
                }
                case "switchPadding": {
                    int switchPadding = ViewAttrUtil.getInt(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        switchView.setSwitchPadding(switchPadding);
                    } else {
                        ReflectUtil.setFieldValue(switchView, "mSwitchPadding", switchPadding);
                        switchView.requestLayout();
                    }
                    break;
                }
                case "splitTrack": {
                    boolean splitTrack = ViewAttrUtil.getBoolean(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        switchView.setSplitTrack(splitTrack);
                    } else {
                        ReflectUtil.setFieldValue(switchView, "mSplitTrack", splitTrack);
                        switchView.invalidate();
                    }
                    break;
                }
                case "thumbTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ColorStateList thumbTint = ViewAttrUtil.getColorStateList(resources, type, value);
                        switchView.setThumbTintList(thumbTint);
                    }
                    break;
                }
                case "thumbTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        switchView.setThumbTintMode(tintMode);
                    }
                    break;
                }
                case "trackTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ColorStateList trackTint = ViewAttrUtil.getColorStateList(resources, type, value);
                        switchView.setTrackTintList(trackTint);
                    }
                    break;
                }
                case "trackTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        switchView.setTrackTintMode(tintMode);
                    }
                    break;
                }
                case "switchTextAppearance": {
                    int switchTextAppearance = -1;
                    if (value != null) {
                        switchTextAppearance = (int) value;
                    }
                    if (switchTextAppearance != -1) {
                        switchView.setSwitchTextAppearance(context, switchTextAppearance);
                    }
                    break;
                }
            }
        }
    }
}