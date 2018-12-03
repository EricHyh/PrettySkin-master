package com.hyh.prettyskin.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * Created by Eric_He on 2018/11/4.
 */

public class ViewAttrUtil {

    private static final ImageView.ScaleType[] SCALE_TYPE_ARRAY = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    public static ImageView.ScaleType getImageScaleType(int index) {
        if (index < 0 || index >= SCALE_TYPE_ARRAY.length) {
            return null;
        }
        return SCALE_TYPE_ARRAY[index];
    }

    public static TextUtils.TruncateAt getTextEllipsize(int index) {
        TextUtils.TruncateAt where = null;
        switch (index) {
            case 1:
                where = TextUtils.TruncateAt.START;
                break;
            case 2:
                where = TextUtils.TruncateAt.MIDDLE;
                break;
            case 3:
                where = TextUtils.TruncateAt.END;
                break;
            case 4:
                where = TextUtils.TruncateAt.MARQUEE;
                break;
        }
        return where;
    }

    public static Typeface getTextTypeface(int index) {
        Typeface tf = null;
        switch (index) {
            case 1:
                tf = Typeface.SANS_SERIF;
                break;
            case 2:
                tf = Typeface.SERIF;
                break;
            case 3:
                tf = Typeface.MONOSPACE;
                break;
        }
        return tf;
    }

    public static int getDefStyleAttr(String styleName) {
        return Reflect.from("android.R$attr").filed(styleName, int.class).get(null);
    }


    public static int getDefStyleAttr_internal(String styleName) {
        return Reflect.from("com.android.internal.R$attr").filed(styleName, int.class).get(null);
    }

    public static int getDefStyleAttr_V7(String styleName) {
        return Reflect.from("android.support.v7.appcompat.R$attr").filed(styleName, int.class).get(null);
    }

    public static int getDefStyleAttr_X(String styleName) {
        return Reflect.from("androidx.appcompat.R$attr").filed(styleName, int.class).get(null);
    }

    public static boolean needsTileify(Drawable progressDrawable) {
        if (progressDrawable != null) {
            return Reflect.from(ProgressBar.class)
                    .method("needsTileify", boolean.class)
                    .param(Drawable.class, progressDrawable)
                    .invoke(null);
        }
        return false;
    }


    public static PorterDuff.Mode getTintMode(int type, Object value) {
        PorterDuff.Mode tintMode = null;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_INT: {
                    int index = (int) value;
                    tintMode = ViewAttrUtil.getTintMode(index);
                    break;
                }
                case ValueType.TYPE_OBJECT: {
                    if (value instanceof PorterDuff.Mode) {
                        tintMode = (PorterDuff.Mode) value;
                    }
                    break;
                }
            }
        }
        return tintMode;
    }


    public static PorterDuff.Mode getTintMode(int index) {
        return Reflect.from(Drawable.class)
                .method("parseTintMode", PorterDuff.Mode.class)
                .param(int.class, index)
                .param(PorterDuff.Mode.class, null)
                .invoke(null);
    }

    public static Drawable getDrawable(Resources resources, int type, Object value) {
        Drawable drawable = null;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_REFERENCE: {
                    drawable = resources.getDrawable((Integer) value);
                    break;
                }
                case ValueType.TYPE_DRAWABLE: {
                    drawable = (Drawable) value;
                    break;
                }
            }
        }
        return drawable;
    }


    public static int getColor(Resources resources, int type, Object value) {
        return getColor(resources, type, value, 0);
    }


    public static int getColor(Resources resources, int type, Object value, int defaultValue) {
        int color = defaultValue;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_COLOR_INT: {
                    color = (int) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    color = resources.getColor((Integer) value);
                    break;
                }
            }
        }
        return color;
    }

    public static ColorStateList getColorStateList(Resources resources, int type, Object value) {
        return getColorStateList(resources, type, value, null);
    }

    public static ColorStateList getColorStateList(Resources resources, int type, Object value, ColorStateList defaultValue) {
        ColorStateList colorStateList = defaultValue;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_COLOR_INT: {
                    colorStateList = ColorStateList.valueOf((Integer) value);
                    break;
                }
                case ValueType.TYPE_COLOR_STATE_LIST: {
                    colorStateList = (ColorStateList) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    colorStateList = resources.getColorStateList((Integer) value);
                    break;
                }
            }
        }
        return colorStateList;
    }

    public static ColorStateList getColorStateList(Resources resources, int type, Object value, int defaultValue) {
        ColorStateList colorStateList = ColorStateList.valueOf(defaultValue);
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_COLOR_INT: {
                    colorStateList = ColorStateList.valueOf((Integer) value);
                    break;
                }
                case ValueType.TYPE_COLOR_STATE_LIST: {
                    colorStateList = (ColorStateList) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    colorStateList = resources.getColorStateList((Integer) value);
                    break;
                }
            }
        }
        return colorStateList;
    }

    public static boolean getBoolean(Resources resources, int type, Object value) {
        return getBoolean(resources, type, value, false);
    }

    public static boolean getBoolean(Resources resources, int type, Object value, boolean defaultValue) {
        boolean result = defaultValue;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_BOOLEAN: {
                    result = (boolean) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    result = resources.getBoolean((Integer) value);
                    break;
                }
            }
        }
        return result;
    }


    public static int getInt(Resources resources, int type, Object value) {
        return getInt(resources, type, value, 0);
    }

    public static int getInt(Resources resources, int type, Object value, int defaultValue) {
        int result = defaultValue;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_INT: {
                    result = (int) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    result = resources.getInteger((Integer) value);
                    break;
                }
            }
        }
        return result;
    }

    public static float getFloat(Resources resources, int type, Object value) {
        return getFloat(resources, type, value, 0.0f);
    }

    public static float getFloat(Resources resources, int type, Object value, float defaultValue) {
        float result = defaultValue;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_FLOAT: {
                    result = (float) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    result = resources.getFraction((int) value, 1, 1);
                    break;
                }
            }
        }
        return result;
    }

    public static CharSequence getCharSequence(Resources resources, int type, Object value) {
        return getCharSequence(resources, type, value, null);
    }

    public static CharSequence getCharSequence(Resources resources, int type, Object value, CharSequence defaultValue) {
        CharSequence result = defaultValue;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_STRING: {
                    result = (CharSequence) value;
                    break;
                }
                case ValueType.TYPE_REFERENCE: {
                    result = resources.getString((int) value);
                    break;
                }
            }
        }
        return result;
    }

    public static String getString(Resources resources, int type, Object value) {
        return getString(resources, type, value, null);
    }

    public static String getString(Resources resources, int type, Object value, CharSequence defaultValue) {
        CharSequence result = getCharSequence(resources, type, value, defaultValue);
        if (result == null) {
            return null;
        }
        return result.toString();
    }

    public static LayoutAnimationController getLayoutAnimation(Context context, int type, Object value) {
        LayoutAnimationController layoutAnimationController = null;
        if (value != null) {
            switch (type) {
                case ValueType.TYPE_REFERENCE: {
                    layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, (int) value);
                    break;
                }
                case ValueType.TYPE_OBJECT: {
                    if (value instanceof LayoutAnimationController) {
                        layoutAnimationController = (LayoutAnimationController) value;
                    }
                    break;
                }
            }
        }
        return layoutAnimationController;
    }
}
