package com.hyh.prettyskin.core.handler;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public interface ISkinHandler {

    boolean isSupportAttrName(View view, String attrName);

    AttrValue parseAttrValue(View view, AttributeSet set, String attrName);

    void replace(View view, String attrName, AttrValue attrValue);

}
