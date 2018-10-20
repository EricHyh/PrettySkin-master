package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.AttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */

@SuppressWarnings("all")
public class ViewSkinHandler implements ISkinHandler {

    private int defStyleAttr;

    private int defStyleRes;

    public ViewSkinHandler() {
    }

    public ViewSkinHandler(int defStyleAttr) {
        this.defStyleAttr = defStyleAttr;
    }

    public ViewSkinHandler(int defStyleAttr, int defStyleRes) {
        this.defStyleAttr = defStyleAttr;
        this.defStyleRes = defStyleRes;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return true;
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        Class styleableClass = getStyleableClass(view);
        String styleableName = getStyleableName(view);
        attrValue = parseAttrValue(view, set, attrName, styleableClass, styleableName);
        return attrValue;
    }

    protected AttrValue parseAttrValue(View view, AttributeSet set, String attrName, Class styleableClass, String styleableName) {
        AttrValue attrValue = null;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        Context context = view.getContext();
        int[] attrs = AttrUtil.getAttrs(styleableClass, styleableName);
        if (attrs != null) {
            int styleableIndex = AttrUtil.getStyleableIndex(styleableClass, styleableName, attrName);
            final int defStyleAttr = this.defStyleAttr;
            final int defStyleRes = this.defStyleRes;
            TypedArray typedArray = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (styleableIndex == index) {
                    int indexType = typedArray.getType(index);
                    switch (indexType) {
                        case TypedValue.TYPE_INT_COLOR_ARGB8:
                        case TypedValue.TYPE_INT_COLOR_RGB8:
                        case TypedValue.TYPE_INT_COLOR_ARGB4:
                        case TypedValue.TYPE_INT_COLOR_RGB4: {
                            int color = typedArray.getColor(index, 0);
                            type = ValueType.TYPE_COLOR_INT;
                            value = color;
                            break;
                        }
                        case TypedValue.TYPE_INT_DEC:
                        case TypedValue.TYPE_INT_HEX: {
                            type = ValueType.TYPE_INT;
                            value = typedArray.getInt(index, 0);
                            break;
                        }
                        case TypedValue.TYPE_FLOAT: {
                            type = ValueType.TYPE_FLOAT;
                            value = typedArray.getFloat(index, 0.0f);
                            break;
                        }
                        case TypedValue.TYPE_FRACTION: {
                            type = ValueType.TYPE_FLOAT;
                            value = typedArray.getFraction(index, 1, 1, 0.0f);
                            break;
                        }
                        case TypedValue.TYPE_INT_BOOLEAN: {
                            type = ValueType.TYPE_BOOLEAN;
                            value = typedArray.getBoolean(index, false);
                            break;
                        }
                        case TypedValue.TYPE_DIMENSION: {
                            type = ValueType.TYPE_FLOAT;
                            value = typedArray.getDimension(index, 0.0f);
                            break;
                        }
                        case TypedValue.TYPE_STRING: {
                            String string = typedArray.getString(index);//res/drawable-anydpi-v21/ic_launcher_background.xml
                            if (!TextUtils.isEmpty(string)) {
                                if (string.matches("^res/color.*/.+\\.xml$")) {
                                    try {
                                        value = typedArray.getResourceId(index, 0);
                                        type = ValueType.TYPE_COLOR_ID;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (string.matches("^res/[(drawable)|(mipmap)].*/.+$")) {
                                    try {
                                        type = ValueType.TYPE_DRAWABLE_ID;
                                        value = typedArray.getResourceId(index, 0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (string.matches("^res/anim.*/.+\\.xml$")) {
                                    try {
                                        type = ValueType.TYPE_ANIM_ID;
                                        value = typedArray.getResourceId(index, 0);
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
                            value = typedArray.getResourceId(index, 0);
                            break;
                        }
                        case TypedValue.TYPE_ATTRIBUTE: {
                            break;
                        }
                    }
                    break;
                }
            }
            typedArray.recycle();
        }
        if (value != null) {
            attrValue = new AttrValue(context.getResources(), type, value);
        }
        return attrValue;
    }


    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {

    }

    private Class getStyleableClass(View view) {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private String getStyleableName(View view) {
        return "View";
    }

}
