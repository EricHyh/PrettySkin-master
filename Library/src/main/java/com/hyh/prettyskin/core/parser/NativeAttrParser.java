package com.hyh.prettyskin.core.parser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/15.
 */

public class NativeAttrParser implements XmlAttrParser {


    private List<XmlAttrParser> mXmlAttrParsers = new ArrayList<>();

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return false;
    }

    @Override
    public Object parse(View view, AttributeSet attrs, String attrName) {
        if (attrs == null || TextUtils.isEmpty(attrName)) {
            return null;
        }
        Context context = view.getContext();
        Object attrValue = null;
        boolean isAttrParsed = false;
        if (TextUtils.equals(attrName, "background")) {
            Drawable drawable = view.getBackground();
            if (drawable != null && drawable instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) drawable;
                attrValue = colorDrawable.getColor();
            } else {
                attrValue = drawable;
            }
            isAttrParsed = true;
        } else if (TextUtils.equals(attrName, "textColor") && view instanceof TextView) {
            TextView textView = (TextView) view;
            attrValue = textView.getTextColors();
            isAttrParsed = true;
        }
        if (!isAttrParsed) {
            int attributeCount = attrs.getAttributeCount();
            if (attributeCount > 0) {
                for (int index = 0; index < attributeCount; index++) {
                    String attributeName = attrs.getAttributeName(index);
                    if (TextUtils.equals(attributeName, attrName)) {
                        String attributeValue = attrs.getAttributeValue(index);
                        if (!TextUtils.isEmpty(attributeName)) {
                            if (attributeValue.startsWith("#")) {
                                attrValue = Color.parseColor(attributeValue);
                            } else if (attributeValue.startsWith("@")) {
                                int attributeResourceValue = attrs.getAttributeResourceValue(index, 0);
                                if (attributeResourceValue != 0) {
                                    String resourceTypeName = context.getResources().getResourceTypeName(attributeResourceValue);
                                    if ("color".equalsIgnoreCase(resourceTypeName)) {
                                        attrValue = context.getResources().getDrawable(attributeResourceValue);
                                    } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                        attrValue = context.getResources().getDrawable(attributeResourceValue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return attrValue;
    }

    private static class ViewAttrParser implements XmlAttrParser {

        @Override
        public boolean isSupportAttrName(View view, String attrName) {
            return false;
        }

        @Override
        public Object parse(View view, AttributeSet attrs, String attrName) {
            switch (attrName) {
                case "background": {
                    break;
                }
                case "backgroundTint": {
                    break;
                }
                case "backgroundTintMode": {
                    break;
                }
                case "padding": {
                    break;
                }
                case "paddingLeft": {
                    break;
                }
                case "paddingTop": {
                    break;
                }
                case "paddingRight": {
                    break;
                }
                case "paddingBottom": {
                    break;
                }
                case "paddingStart": {
                    break;
                }
                case "paddingEnd": {
                    break;
                }
                case "paddingHorizontal": {
                    //TODO 暂不实现
                    break;
                }
                case "paddingVertical": {
                    //TODO 暂不实现
                    break;
                }
                case "scrollX": {
                    break;
                }
                case "scrollY": {
                    break;
                }
                default: {
                    break;
                }
            }
            return null;
        }
    }
}
