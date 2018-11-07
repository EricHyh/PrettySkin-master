package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
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
                    //switchView.setShowText(showText);
                    break;
                }
                case "thumbTextPadding": {
                    break;
                }
                case "switchMinWidth": {
                    break;
                }
                case "switchPadding": {
                    break;
                }
                case "splitTrack": {
                    break;
                }
                case "thumbTint": {
                    break;
                }
                case "thumbTintMode": {
                    break;
                }
                case "trackTint": {
                    break;
                }
                case "trackTintMode": {
                    break;
                }
                case "switchTextAppearance": {
                    break;
                }
            }
        }
    }
}
