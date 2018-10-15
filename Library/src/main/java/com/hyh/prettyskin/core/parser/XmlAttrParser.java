package com.hyh.prettyskin.core.parser;

import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Eric_He on 2018/10/15.
 */

public interface XmlAttrParser {

    boolean isSupportAttrName(View view, String attrName);

    Object parse(View view, AttributeSet attrs, String attrName);

}
