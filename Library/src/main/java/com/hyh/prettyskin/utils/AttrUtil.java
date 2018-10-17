package com.hyh.prettyskin.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * @author Administrator
 * @description
 * @data 2018/10/17
 */

public class AttrUtil {

    public static Object getAttrValueFromAttributeSet(Context context, AttributeSet set, String attrName) {
        Object attrValue = null;
        int attributeCount = set.getAttributeCount();
        if (attributeCount > 0) {
            for (int index = 0; index < attributeCount; index++) {
                String attributeName = set.getAttributeName(index);
                if (TextUtils.equals(attributeName, attrName)) {
                    String attributeValue = set.getAttributeValue(index);
                    if (!TextUtils.isEmpty(attributeName)) {
                        if (attributeValue.startsWith("#")) {
                            attrValue = Color.parseColor(attributeValue);
                        } else if (attributeValue.startsWith("@")) {
                            int attributeResourceValue = set.getAttributeResourceValue(index, 0);
                            if (attributeResourceValue != 0) {
                                String resourceTypeName = context.getResources().getResourceTypeName(attributeResourceValue);
                                if ("color".equalsIgnoreCase(resourceTypeName)) {
                                    attrValue = context.getResources().getDrawable(attributeResourceValue);
                                } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                    attrValue = context.getResources().getDrawable(attributeResourceValue);
                                }
                            }
                        } else {
                            boolean isParseToNumber = true;
                            if (attributeValue.startsWith("0b") || attributeValue.startsWith("0B")) {
                                try {
                                    String numberStr = attributeValue.substring(2);

                                } catch (Exception e) {
                                    isParseToNumber = false;
                                }
                            } else if (attributeValue.startsWith("0x") || attributeValue.startsWith("0X")) {
                                try {
                                    String numberStr = attributeValue.substring(2);
                                    int number = Integer.parseInt(numberStr, 16);
                                } catch (NumberFormatException e) {
                                    isParseToNumber = false;
                                }
                            } else if (attributeValue.startsWith("0")) {
                                try {
                                    String numberStr = attributeValue.substring(1);
                                    int number = Integer.parseInt(numberStr, 8);
                                } catch (NumberFormatException e) {
                                    isParseToNumber = false;
                                }
                            } else {
                                isParseToNumber = false;
                            }
                        }
                    }
                }
            }
        }
        return attrValue;
    }
}
