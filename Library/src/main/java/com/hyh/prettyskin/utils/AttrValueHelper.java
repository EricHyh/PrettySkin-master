package com.hyh.prettyskin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ColorStateListFactory;
import com.hyh.prettyskin.DrawableFactory;
import com.hyh.prettyskin.TypedArrayFactory;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/11/26
 */

public class AttrValueHelper {


    public static AttrValue getAttrValue(View view, TypedArray typedArray, int styleableIndex) {
        return getAttrValue(view.getContext(), typedArray, null, styleableIndex);
    }

    public static AttrValue getAttrValue(Context context, final TypedArray typedArray, final TypedArrayFactory typedArrayFactory, final int styleableIndex) {
        AttrValue attrValue;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        int indexType = getTypedValue(typedArray, styleableIndex);
        if (indexType == TypedValue.TYPE_NULL) {
            return new AttrValue(context, type, null);
        }
        switch (indexType) {
            case TypedValue.TYPE_INT_COLOR_ARGB8:
            case TypedValue.TYPE_INT_COLOR_RGB8:
            case TypedValue.TYPE_INT_COLOR_ARGB4:
            case TypedValue.TYPE_INT_COLOR_RGB4: {
                int color = typedArray.getColor(styleableIndex, 0);
                type = ValueType.TYPE_COLOR_INT;
                value = color;
                break;
            }
            case TypedValue.TYPE_INT_DEC:
            case TypedValue.TYPE_INT_HEX: {
                type = ValueType.TYPE_INT;
                value = typedArray.getInt(styleableIndex, 0);
                break;
            }
            case TypedValue.TYPE_FLOAT: {
                type = ValueType.TYPE_FLOAT;
                value = typedArray.getFloat(styleableIndex, 0.0f);
                break;
            }
            case TypedValue.TYPE_FRACTION: {
                type = ValueType.TYPE_FLOAT;
                value = typedArray.getFraction(styleableIndex, 1, 1, 0.0f);
                break;
            }
            case TypedValue.TYPE_INT_BOOLEAN: {
                type = ValueType.TYPE_BOOLEAN;
                value = typedArray.getBoolean(styleableIndex, false);
                break;
            }
            case TypedValue.TYPE_DIMENSION: {
                type = ValueType.TYPE_FLOAT;
                value = typedArray.getDimension(styleableIndex, 0.0f);
                break;
            }
            case TypedValue.TYPE_STRING: {
                String string = typedArray.getString(styleableIndex);//res/drawable-anydpi-v21/ic_launcher_background.xml
                if (!TextUtils.isEmpty(string)) {
                    if (string.matches("^res/color.*/.+\\.xml$")) {
                        try {
                            ColorStateList colorStateList = typedArray.getColorStateList(styleableIndex);
                            if (typedArrayFactory != null && colorStateList != null) {
                                value = new ColorStateListFactory() {
                                    @Override
                                    public ColorStateList create() {
                                        TypedArray newTypedArray = typedArrayFactory.create();
                                        ColorStateList newColorStateList = newTypedArray.getColorStateList(styleableIndex);
                                        newTypedArray.recycle();
                                        return newColorStateList;
                                    }
                                };
                                type = ValueType.TYPE_LAZY_COLOR_STATE_LIST;
                            } else {
                                value = colorStateList;
                                type = ValueType.TYPE_COLOR_STATE_LIST;
                            }
                        } catch (Exception e) {
                            Logger.d("AttrValueHelper getAttrValue [" + string + "] getColorStateList failed ", e);
                        }
                        if (value == null) {
                            try {
                                value = typedArray.getResourceId(styleableIndex, 0);
                                type = ValueType.TYPE_REFERENCE;
                            } catch (Exception e) {
                                Logger.d("AttrValueHelper getAttrValue [" + string + "] getResourceId failed ", e);
                            }
                        }
                    } else if (string.matches("^res/[(drawable)|(mipmap)].*/.+$")) {
                        try {
                            Drawable drawable = typedArray.getDrawable(styleableIndex);
                            if (drawable != null && typedArrayFactory != null) {
                                value = new DrawableFactory() {
                                    @Override
                                    public Drawable create() {
                                        TypedArray newTypedArray = typedArrayFactory.create();
                                        Drawable newDrawable = newTypedArray.getDrawable(styleableIndex);
                                        newTypedArray.recycle();
                                        return newDrawable;
                                    }
                                };
                                type = ValueType.TYPE_LAZY_DRAWABLE;
                            } else {
                                value = drawable;
                                type = ValueType.TYPE_DRAWABLE;
                            }
                        } catch (Exception e) {
                            Logger.d("AttrValueHelper getAttrValue [" + string + "] getDrawable failed ", e);
                        }
                        if (value == null) {
                            try {
                                value = typedArray.getResourceId(styleableIndex, 0);
                                type = ValueType.TYPE_REFERENCE;
                            } catch (Exception e) {
                                Logger.d("AttrValueHelper getAttrValue [" + string + "] getResourceId failed ", e);
                            }
                        }
                    } else if (string.matches("^res/anim.*/.+\\.xml$")) {
                        try {
                            value = typedArray.getResourceId(styleableIndex, 0);
                            type = ValueType.TYPE_REFERENCE;
                        } catch (Exception e) {
                            Logger.d("AttrValueHelper getAttrValue [" + string + "] getResourceId failed ", e);
                        }
                    }
                    if (value == null) {
                        type = ValueType.TYPE_STRING;
                        value = string;
                    }
                }
                break;
            }
            case TypedValue.TYPE_REFERENCE: {
                type = ValueType.TYPE_REFERENCE;
                value = typedArray.getResourceId(styleableIndex, 0);
                break;
            }
            case TypedValue.TYPE_ATTRIBUTE: {
                break;
            }
        }
        attrValue = new AttrValue(context, type, value);
        return attrValue;
    }

    private static int getTypedValue(TypedArray typedArray, int index) {
        if (index < 0) {
            return TypedValue.TYPE_NULL;
        }
        int type = TypedValue.TYPE_NULL;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                type = typedArray.getType(index);
            } else {
                /**
                 * index *= AssetManager.STYLE_NUM_ENTRIES;
                 * return mData[index + AssetManager.STYLE_TYPE];
                 */
                int STYLE_NUM_ENTRIES = Reflect.from(AssetManager.class)
                        .filed("STYLE_NUM_ENTRIES", int.class)
                        .defaultValue(6)
                        .get(null);

                int STYLE_TYPE = Reflect.from(AssetManager.class)
                        .filed("STYLE_TYPE", int.class)
                        .get(null);

                int[] mData = Reflect.from(TypedArray.class)
                        .filed("mData", int[].class)
                        .get(typedArray);

                index *= STYLE_NUM_ENTRIES;
                index += STYLE_TYPE;
                if (mData != null && mData.length > index) {
                    type = mData[index];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }


    /**
     * @param styleableClass 样式Class，如R.styleable.class
     * @param styleableName  样式名称
     * @return 返回该样式下所有属性的（属性名称-属性索引）
     */
    public static Map<String, Integer> getStyleableFieldMap(Class styleableClass, String styleableName) {
        Map<String, Integer> fieldNameMap = new HashMap<>();
        try {
            Field[] fields = styleableClass.getFields();
            for (Field field : fields) {
                if (field.getName().startsWith(styleableName + "_")) {
                    Object o = field.get(null);
                    fieldNameMap.put(field.getName(), (Integer) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldNameMap;
    }

    public static int getStyleableIndex(Class styleableClass, String styleableName, String attrName) {
        if (styleableClass == null || TextUtils.isEmpty(styleableName) || TextUtils.isEmpty(attrName)) {
            return -1;
        }
        try {
            String filedName = styleableName + "_" + attrName;
            Field field = styleableClass.getDeclaredField(filedName);
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
                return (int) field.get(null);
            }
        } catch (Exception e) {
            //
        }
        try {
            String filedName = styleableName + "_android_" + attrName;
            Field field = styleableClass.getDeclaredField(filedName);
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
                return (int) field.get(null);
            }
        } catch (Exception e) {
            //
        }
        return -1;
    }

    public static int getTextAppearanceStyleableIndex(Class styleableClass, String attrName) {
        if (styleableClass == null || TextUtils.isEmpty(attrName)) {
            return -1;
        }
        try {
            String filedName = "TextAppearance_" + attrName;
            Field field = styleableClass.getDeclaredField(filedName);
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
                return (int) field.get(null);
            }
        } catch (Exception e) {
            //
        }
        return -1;
    }
}