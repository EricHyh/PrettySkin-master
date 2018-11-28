package com.hyh.prettyskin.demo.widget;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.handler.ntv.ViewSH;

import java.util.Collection;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/10/20
 */

public class CustomViewSH extends ViewSH {

    public CustomViewSH() {
        super();
    }

    public CustomViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CustomViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }

    @Override
    public Map<String, AttrValue> parseAttrValue(View view, AttributeSet set, Collection<String> attrNames) {

        Class styleableClass = R.styleable.class;
        String styleableName = "CustomView";
        AttrValue attrValue = parseAttrValue(view, set, attrName, styleableClass, styleableName);


        return super.parse(view, set, attrNames);
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
