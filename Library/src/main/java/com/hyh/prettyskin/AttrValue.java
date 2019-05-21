package com.hyh.prettyskin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hyh.prettyskin.utils.Logger;

/**
 * @author Administrator
 * @description
 * @data 2018/10/18
 */

public class AttrValue {

    private Context themeContext;

    private int type;

    private Object value;

    public AttrValue(Context themeContext, int type, Object value) {
        this.themeContext = themeContext;
        this.type = type;
        this.value = value;
    }

    public Context getThemeContext() {
        return themeContext;
    }

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public <T> T getTypedValue(Class<T> valueClass, T defaultValue) {
        if (valueClass == null) return null;
        switch (type) {
            case ValueType.TYPE_NULL: {
                return defaultValue;
            }
            case ValueType.TYPE_REFERENCE: {
                return getTypedReferenceValue(valueClass, defaultValue);
            }
            case ValueType.TYPE_INT: {
                if (valueClass == int.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_FLOAT: {
                if (valueClass == float.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_BOOLEAN: {
                if (valueClass == boolean.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_STRING: {
                if (valueClass == String.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_COLOR_INT: {
                if (valueClass == int.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_COLOR_STATE_LIST: {
                if (valueClass == ColorStateList.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_DRAWABLE: {
                if (valueClass == Drawable.class) {
                    return (T) value;
                } else {
                    return defaultValue;
                }
            }
            case ValueType.TYPE_OBJECT: {

                break;
            }
        }
        return null;
    }

    private <T> T getTypedReferenceValue(Class<T> valueClass, T defaultValue) {
        if (themeContext == null) return defaultValue;
        Resources resources = themeContext.getResources();
        int resourceId = (int) value;
        String resourceTypeName = resources.getResourceTypeName(resourceId);
        if (TextUtils.isEmpty(resourceTypeName)) return defaultValue;
        switch (resourceTypeName) {
            case "integer": {
                if (valueClass == int.class) {
                    Integer integer = resources.getInteger(resourceId);
                    return (T) integer;
                }
                return defaultValue;
            }
            case "fraction": {
                if (valueClass == float.class) {
                    try {
                        TypedValue typedValue = new TypedValue();
                        resources.getValue(resourceId, typedValue, true);
                        switch (typedValue.type) {
                            case TypedValue.TYPE_FLOAT: {
                                Float f = typedValue.getFloat();
                                return (T) f;
                            }
                            case TypedValue.TYPE_FRACTION: {
                                Float fraction = typedValue.getFraction(1, 1);
                                return (T) fraction;
                            }
                        }
                    } catch (Exception e) {
                        Logger.w("parse fraction failed, resourceId = " + resourceId, e);
                    }
                }
                return defaultValue;
            }
            case "dimen": {
                if (valueClass == float.class) {
                    Float dimen = resources.getDimension(resourceId);
                    return (T) dimen;
                }
                return defaultValue;
            }
            case "bool": {
                if (valueClass == boolean.class) {
                    Boolean bool = resources.getBoolean(resourceId);
                    return (T) bool;
                }
                return defaultValue;
            }
            case "string": {
                if (valueClass == String.class) {
                    String string = resources.getString(resourceId);
                    return (T) string;
                }
                return defaultValue;
            }
            case "color": {
                if (valueClass == int.class) {
                    Integer color = resources.getColor(resourceId);
                    return (T) color;
                } else if (valueClass == ColorStateList.class) {
                    return (T) resources.getColorStateList(resourceId);
                }
                return defaultValue;
            }
            case "drawable":
            case "mipmap": {
                if (valueClass == int.class) {
                    Drawable drawable = resources.getDrawable(resourceId);
                    return (T) drawable;
                }
                return defaultValue;
            }
            case "anim": {
                if (valueClass == Animation.class) {
                    Animation animation = AnimationUtils.loadAnimation(themeContext, resourceId);
                    return (T) animation;
                }
                return defaultValue;
            }
            default: {
                return defaultValue;
            }
        }
    }
}