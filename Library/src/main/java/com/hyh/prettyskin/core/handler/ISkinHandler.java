package com.hyh.prettyskin.core.handler;

import android.view.View;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public interface ISkinHandler {

    boolean isSupportAttrName(View view, String attrName);

    void replace(View view, String attrName, Object attrValue);

}
