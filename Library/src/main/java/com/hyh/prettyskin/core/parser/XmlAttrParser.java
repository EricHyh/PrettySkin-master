package com.hyh.prettyskin.core.parser;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;

/**
 * Created by Eric_He on 2018/10/15.
 */

public interface XmlAttrParser {

    boolean isSupportAttrName(View view, String attrName);

    AttrValue parse(View view, AttributeSet set, String attrName);

}
