package com.hyh.prettyskin.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;

/**
 * @author Administrator
 * @description
 * @data 2018/10/17
 */

public class AttrUtil {

    public static AttrValue getAttrValueFromAttributeSet(Context context, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        int attributeCount = set.getAttributeCount();
        if (attributeCount > 0) {
            for (int index = 0; index < attributeCount; index++) {
                String attributeName = set.getAttributeName(index);
                if (TextUtils.equals(attributeName, attrName)) {
                    String attributeValue = set.getAttributeValue(index);
                    if (!TextUtils.isEmpty(attributeName)) {
                        if (attributeValue.startsWith("#")) {
                            type = ValueType.TYPE_COLOR_INT;
                            value = Color.parseColor(attributeValue);
                        } else if (attributeValue.startsWith("@")) {
                            int attributeResourceValue = set.getAttributeResourceValue(index, 0);
                            if (attributeResourceValue != 0) {
                                String resourceTypeName = context.getResources().getResourceTypeName(attributeResourceValue);
                                if ("color".equalsIgnoreCase(resourceTypeName)) {
                                    type = ValueType.TYPE_COLOR_ID;
                                    value = attributeResourceValue;
                                } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                    type = ValueType.TYPE_DRAWABLE_ID;
                                    value = attributeResourceValue;
                                }
                            }
                        } else if (attributeValue.endsWith("dip")) {

                        } else if (attributeValue.endsWith("sp")) {

                        } else if (attributeValue.endsWith("px")) {

                        } else if (attributeValue.endsWith("in")) {

                        } else if (attributeValue.endsWith("pt")) {

                        } else if (attributeValue.endsWith("mm")) {

                        }

                        if (value == null) {
                            if (NumberUtil.isIntegerNumber(attributeValue)) {
                                try {
                                    int attributeIntValue = set.getAttributeIntValue(index, Integer.MIN_VALUE);
                                    if (attributeIntValue == Integer.MIN_VALUE) {
                                        attributeIntValue = set.getAttributeIntValue(index, Integer.MAX_VALUE);
                                        if (attributeIntValue != Integer.MAX_VALUE) {
                                            type = ValueType.TYPE_INT;
                                            value = attributeIntValue;
                                        }
                                    } else {
                                        type = ValueType.TYPE_INT;
                                        value = attributeIntValue;
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
                                            type = ValueType.TYPE_FLOAT;
                                            value = attributeFloatValue;
                                        }
                                    } else {
                                        type = ValueType.TYPE_FLOAT;
                                        value = attributeFloatValue;
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
