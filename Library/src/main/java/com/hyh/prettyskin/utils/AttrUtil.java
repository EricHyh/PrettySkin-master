package com.hyh.prettyskin.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/10/17
 */

@SuppressWarnings("all")
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

    public static int[] getNativeAttrs(String viewName) {
        //com.android.internal.R.styleable.
        int[] attrs = null;
        try {
            Class<?> aClass = Class.forName("com.android.internal.R$styleable");
            Field field = aClass.getDeclaredField(viewName);
            attrs = (int[]) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attrs;
    }

    public static Map<Integer, String> getStyleableFieldMap(String styleableClassPath, String styleableName) {
        try {
            return getStyleableFieldMap(Class.forName(styleableClassPath), styleableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }


    public static int[] getAttrs(Class styleableClass, String styleableName) {
        if (styleableClass == null || TextUtils.isEmpty(styleableName)) {
            return null;
        }
        try {
            Field field = styleableClass.getDeclaredField(styleableName);
            field.setAccessible(true);
            return (int[]) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<Integer, String> getStyleableFieldMap(Class styleableClass, String styleableName) {
        Map<Integer, String> fieldNameMap = new HashMap<>();
        try {
            Field[] fields = styleableClass.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) && field.getName().startsWith(styleableName + "_")) {
                    Object o = field.get(null);
                    fieldNameMap.put((Integer) o, field.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldNameMap;
    }

    public static int getStyleableIndex(Class styleableClass, String styleableName, String attrName) {
        try {
            String filedName = styleableName + "_" + attrName;
            Field field = styleableClass.getDeclaredField(filedName);
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
                return (int) field.get(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
