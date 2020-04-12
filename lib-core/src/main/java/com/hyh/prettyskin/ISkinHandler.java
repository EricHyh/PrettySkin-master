package com.hyh.prettyskin;

import android.util.AttributeSet;
import android.view.View;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public interface ISkinHandler {

    boolean isSupportAttrName(View view, String attrName);

    void prepareParse(View view, AttributeSet set);

    AttrValue parse(View view, AttributeSet set, String attrName);

    void finishParse();

    void replace(View view, String attrName, AttrValue attrValue);

}