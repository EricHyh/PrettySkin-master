package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class ViewGroupSH extends ViewSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {

    }

    public ViewGroupSH() {
    }

    public ViewGroupSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ViewGroupSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof ViewGroup && mSupportAttrNames.contains(attrName)
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
        return "ViewGroup";
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof TextView) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            ViewGroup viewGroup = (ViewGroup) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "clipChildren": {
                    break;
                }
                case "clipToPadding": {
                    break;
                }
                case "animationCache": {
                    break;
                }
                case "persistentDrawingCache": {
                    break;
                }
                case "addStatesFromChildren": {
                    break;
                }
                case "alwaysDrawnWithCache": {
                    break;
                }
                case "layoutAnimation": {
                    break;
                }
                case "splitMotionEvents": {
                    break;
                }
                case "animateLayoutChanges": {
                    break;
                }
                case "layoutMode": {
                    break;
                }
                case "transitionGroup": {
                    break;
                }
                case "touchscreenBlocksFocus": {
                    break;
                }
            }
        }
    }
}
