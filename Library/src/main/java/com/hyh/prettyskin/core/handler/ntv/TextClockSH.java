package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextClock;

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

public class TextClockSH extends TextViewSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("format12Hour");
        mSupportAttrNames.add("format24Hour");
        mSupportAttrNames.add("timeZone");
    }

    public TextClockSH() {
    }

    public TextClockSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public TextClockSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof TextClock && mSupportAttrNames.contains(attrName)
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
        return "TextClock";
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof TextClock) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            TextClock textClock = (TextClock) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "format12Hour": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        CharSequence format12Hour = ViewAttrUtil.getCharSequence(resources, type, value);
                        if (!TextUtils.isEmpty(format12Hour)) {
                            textClock.setFormat12Hour(format12Hour);
                        }
                    }
                    break;
                }
                case "format24Hour": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        CharSequence format24Hour = ViewAttrUtil.getCharSequence(resources, type, value);
                        if (!TextUtils.isEmpty(format24Hour)) {
                            textClock.setFormat24Hour(format24Hour);
                        }
                    }
                    break;
                }
                case "timeZone": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        String timeZone = ViewAttrUtil.getString(resources, type, value);
                        if (!TextUtils.isEmpty(timeZone)) {
                            textClock.setTimeZone(timeZone);
                        }
                    }
                    break;
                }
            }
        }
    }
}
