package com.hyh.prettyskin.core.handler.ntv;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;

/**
 * @author Administrator
 * @description
 * @data 2018/10/20
 */

public class TextViewSkinHandler extends ViewSkinHandler {

    public TextViewSkinHandler() {
        super();
    }

    public TextViewSkinHandler(int defStyleAttr) {
        super(defStyleAttr);
    }

    public TextViewSkinHandler(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        Class styleableClass = getStyleableClass();
        String styleableName = getStyleableName();
        AttrValue attrValue = parseAttrValue(view, set, attrName, styleableClass, styleableName);
        if (attrValue != null) {
            return attrValue;
        }
        return super.parseAttrValue(view, set, attrName);
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }

    private Class getStyleableClass() {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private String getStyleableName() {
        return "TextView";
    }
}
