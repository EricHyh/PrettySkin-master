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
                                    attrValue = attributeResourceValue;
                                } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                    attrValue = attributeResourceValue;
                                }
                            }
                        } else {
                            if (NumberUtil.isIntegerNumber(attributeValue)) {
                                try {
                                    int attributeIntValue = set.getAttributeIntValue(index, Integer.MIN_VALUE);
                                    if (attributeIntValue == Integer.MIN_VALUE) {
                                        attributeIntValue = set.getAttributeIntValue(index, Integer.MAX_VALUE);
                                        if (attributeIntValue != Integer.MAX_VALUE) {
                                            attrValue = attributeIntValue;
                                        }
                                    } else {
                                        attrValue = attributeIntValue;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (NumberUtil.isFloatNumber(attributeValue)) {
                                try {
                                    float attributeFloatValue = set.getAttributeFloatValue(index, Float.MIN_VALUE);
                                    if (attributeFloatValue == Float.MIN_VALUE) {
                                        attributeFloatValue = set.getAttributeFloatValue(index, Float.MAX_VALUE);
                                        if (attributeFloatValue != Float.MAX_VALUE) {
                                            attrValue = attributeFloatValue;
                                        }
                                    } else {
                                        attrValue = attributeFloatValue;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
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
