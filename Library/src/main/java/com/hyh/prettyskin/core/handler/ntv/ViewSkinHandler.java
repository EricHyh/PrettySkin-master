package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.AttrUtil;

import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */

@SuppressWarnings("all")
public class ViewSkinHandler implements ISkinHandler {

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return false;
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        Context context = view.getContext();
        Class styleableClass = getStyleableClass(view);
        String styleableName = getStyleableName(view);
        int[] attrs = AttrUtil.getAttrs(styleableClass, styleableName);
        if (attrs != null) {
            Map<Integer, String> styleableFieldMap = AttrUtil.getStyleableFieldMap(styleableClass, styleableName);
            int defStyleAttr = getDefStyleAttr(view);
            int defStyleRes = getDefStyleRes(view);
            TypedArray typedArray = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
            int indexCount = typedArray.getIndexCount();
            for (int index = 0; index < indexCount; index++) {
                String filedName = styleableFieldMap.get(index);
                if (TextUtils.equals(filedName, styleableName + "_" + attrName)) {
                    int indexType = typedArray.getType(index);
                    switch (indexType) {
                        case TypedValue.TYPE_INT_COLOR_ARGB8:
                        case TypedValue.TYPE_INT_COLOR_RGB8:
                        case TypedValue.TYPE_INT_COLOR_ARGB4:
                        case TypedValue.TYPE_INT_COLOR_RGB4: {
                            break;
                        }
                        case TypedValue.TYPE_INT_DEC:
                        case TypedValue.TYPE_INT_HEX: {
                            break;
                        }
                        case TypedValue.TYPE_FLOAT: {
                            break;
                        }
                        case TypedValue.TYPE_FRACTION: {
                            break;
                        }
                        case TypedValue.TYPE_INT_BOOLEAN: {
                            break;
                        }
                        case TypedValue.TYPE_DIMENSION: {
                            break;
                        }
                        case TypedValue.TYPE_STRING: {
                            break;
                        }
                        case TypedValue.TYPE_REFERENCE: {
                            break;
                        }
                        case TypedValue.TYPE_ATTRIBUTE: {
                            break;
                        }
                    }
                    String string = typedArray.getString(index);
                    if (!TextUtils.isEmpty(string)) {
                        int type = TypedValue.TYPE_NULL;
                        Object value = null;
                        if (string.startsWith("#")) {
                            int color = typedArray.getColor(index, 0);
                            type = ValueType.TYPE_COLOR_INT;
                            value = color;
                        } else if (string.startsWith("res/color")) {
                            ColorStateList colorStateList = typedArray.getColorStateList(index);
                            type = ValueType.TYPE_COLOR_ID;
                            value = typedArray.getResourceId(index, 0);
                        } else if (string.startsWith("res/mipmap") || string.startsWith("res/drawable")) {
                            type = ValueType.TYPE_DRAWABLE_ID;
                            value = typedArray.getResourceId(index, 0);
                        }
                    }
                    break;
                }
            }
            typedArray.recycle();
        }
        return attrValue;
    }


    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {

    }

    protected Class getStyleableClass(View view) {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    protected String getStyleableName(View view) {
        return "View";
    }

    protected int getDefStyleAttr(View view) {
        return 0;
    }

    protected int getDefStyleRes(View view) {
        return 0;
    }
}
