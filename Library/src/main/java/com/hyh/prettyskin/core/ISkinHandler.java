package com.hyh.prettyskin.core;

import android.view.View;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public interface ISkinHandler {

    boolean isSupportAttrName(String attrName);

    void replace(View view, String attrName, Object attrValue);

}
