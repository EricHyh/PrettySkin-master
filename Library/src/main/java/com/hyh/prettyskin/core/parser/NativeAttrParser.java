package com.hyh.prettyskin.core.parser;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.parser.ntv.TextViewAttrParser;
import com.hyh.prettyskin.core.parser.ntv.ViewAttrParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/15.
 */

public class NativeAttrParser implements XmlAttrParser {


    private List<XmlAttrParser> mXmlAttrParsers = new ArrayList<>();

    {
        mXmlAttrParsers.add(new ViewAttrParser());
        mXmlAttrParsers.add(new TextViewAttrParser());
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        for (XmlAttrParser xmlAttrParser : mXmlAttrParsers) {
            if (xmlAttrParser.isSupportAttrName(view, attrName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (set == null || TextUtils.isEmpty(attrName)) {
            return null;
        }
        AttrValue attrValue = null;
        for (XmlAttrParser xmlAttrParser : mXmlAttrParsers) {
            if (xmlAttrParser.isSupportAttrName(view, attrName)) {
                attrValue = xmlAttrParser.parse(view, set, attrName);
            }
        }
        if (attrValue != null) {
            return attrValue;
        }
        int type = ValueType.TYPE_NULL;
        Object value = null;
        Context context = view.getContext();
        int attributeCount = set.getAttributeCount();
        if (attributeCount > 0) {
            for (int index = 0; index < attributeCount; index++) {
                String attributeName = set.getAttributeName(index);
                if (TextUtils.equals(attributeName, attrName)) {
                    String attributeValue = set.getAttributeValue(index);
                    if (!TextUtils.isEmpty(attributeName)) {
                        if (attributeValue.startsWith("#")) {
                            value = Color.parseColor(attributeValue);
                        } else if (attributeValue.startsWith("@")) {
                            int attributeResourceValue = set.getAttributeResourceValue(index, 0);
                            if (attributeResourceValue != 0) {
                                String resourceTypeName = context.getResources().getResourceTypeName(attributeResourceValue);
                                if ("color".equalsIgnoreCase(resourceTypeName)) {
                                    value = context.getResources().getDrawable(attributeResourceValue);
                                } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                    value = context.getResources().getDrawable(attributeResourceValue);
                                }
                            }
                        }
                    }
                }
            }
        }
        return attrValue;
    }

}
