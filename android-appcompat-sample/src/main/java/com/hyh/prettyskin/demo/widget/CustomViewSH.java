package com.hyh.prettyskin.demo.widget;

import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ViewSH;

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
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
