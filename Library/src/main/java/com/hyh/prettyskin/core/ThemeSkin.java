package com.hyh.prettyskin.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

import com.hyh.prettyskin.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eric_He on 2018/10/14.
 */
@SuppressLint("UseSparseArrays")
public class ThemeSkin implements ISkin {

    private Context mContext;

    private String mStyleableClassPath;

    private String mStyleableName;

    private List<SkinAttr> mSkinAttrList;

    public ThemeSkin(Context context, int themeResId, String styleableClassPath, String styleableName) {
        mContext = new ContextThemeWrapper(context.getApplicationContext(), themeResId);
        mStyleableClassPath = styleableClassPath;
        mStyleableName = styleableName;
    }

    @Override
    public List<SkinAttr> getSkinAttrs() {
        if (mSkinAttrList != null) {
            return mSkinAttrList;
        }
        Class styleableClass = ReflectUtil.getClassByPath(mStyleableClassPath);
        if (styleableClass == null) {
            return mSkinAttrList;
        }
        int[] attrs = (int[]) ReflectUtil.getStaticFieldValue(styleableClass, mStyleableName);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
        Map<Integer, String> filedNameMap = getStyleableAttrMap(styleableClass, mStyleableName);
        if (filedNameMap != null && !filedNameMap.isEmpty()) {
            mSkinAttrList = new ArrayList<>(filedNameMap.size());
            Set<Map.Entry<Integer, String>> entrySet = filedNameMap.entrySet();
            for (Map.Entry<Integer, String> entry : entrySet) {
                Integer key = entry.getKey();
                String value = entry.getValue().substring(mStyleableName.length() + 1);
                int type = typedArray.getType(key);
                switch (type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB4: {
                        int color = typedArray.getColor(key, 0);
                        mSkinAttrList.add(new SkinAttr(value, SkinAttr.TYPE_COLOR, color));
                        break;
                    }
                    case TypedValue.TYPE_STRING: {
                        String string = typedArray.getString(key);
                        if (!TextUtils.isEmpty(string)) {
                            if (string.startsWith("res/mipmap") || string.startsWith("res/drawable")) {
                                Drawable drawable = typedArray.getDrawable(key);
                                mSkinAttrList.add(new SkinAttr(value, SkinAttr.TYPE_DRAWABLE, drawable));
                            } else if (string.startsWith("res/color")) {
                                ColorStateList colorStateList = typedArray.getColorStateList(key);
                                mSkinAttrList.add(new SkinAttr(value, SkinAttr.TYPE_COLOR_STATE_LIST, colorStateList));
                            }
                        }
                        break;
                    }
                }
            }
        }
        typedArray.recycle();
        return mSkinAttrList;
    }

    private Map<Integer, String> getStyleableAttrMap(Class styleableClass, String styleableName) {
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
}
