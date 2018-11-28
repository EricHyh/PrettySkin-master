package com.hyh.prettyskin.core.handler;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ReflectUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/26
 */

public class AttrValueHelper {


    public static AttrValue getAttrValue(View view, TypedArray typedArray, int styleableIndex) {
        return getAttrValue(view.getContext(), typedArray, styleableIndex);
    }

    public static AttrValue getAttrValue(Context context, TypedArray typedArray, int styleableIndex) {
        AttrValue attrValue;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        int indexType = getTypedValue(typedArray, styleableIndex);
        if (indexType == TypedValue.TYPE_NULL) {
            return null;
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
                            value = typedArray.getResourceId(styleableIndex, 0);
                            type = ValueType.TYPE_REFERENCE;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (string.matches("^res/[(drawable)|(mipmap)].*/.+$")) {
                        try {
                            value = typedArray.getResourceId(styleableIndex, 0);
                            type = ValueType.TYPE_REFERENCE;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (string.matches("^res/anim.*/.+\\.xml$")) {
                        try {
                            value = typedArray.getResourceId(styleableIndex, 0);
                            type = ValueType.TYPE_REFERENCE;
                        } catch (Exception e) {
                            e.printStackTrace();
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
        int type = TypedValue.TYPE_NULL;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                type = typedArray.getType(index);
            } else {
                /**
                 *
                 * index *= AssetManager.STYLE_NUM_ENTRIES;
                 * return mData[index + AssetManager.STYLE_TYPE];
                 */
                int STYLE_NUM_ENTRIES = 6;
                Object STYLE_NUM_ENTRIES_OBJ = ReflectUtil.getStaticFieldValue(AssetManager.class, "STYLE_NUM_ENTRIES");
                if (STYLE_NUM_ENTRIES_OBJ != null && STYLE_NUM_ENTRIES_OBJ instanceof Integer) {
                    STYLE_NUM_ENTRIES = (int) STYLE_NUM_ENTRIES_OBJ;
                }
                int STYLE_TYPE = 0;
                Object STYLE_TYPE_OBJ = ReflectUtil.getStaticFieldValue(AssetManager.class, "STYLE_NUM_ENTRIES");
                if (STYLE_TYPE_OBJ != null && STYLE_TYPE_OBJ instanceof Integer) {
                    STYLE_TYPE = (int) STYLE_TYPE_OBJ;
                }
                int[] mData = (int[]) ReflectUtil.getFieldValue(typedArray, "mData");
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
}
