package com.hyh.prettyskin.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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

    private Map<String, SkinAttr> mSkinAttrMap;

    public ThemeSkin(Context context, int themeResId, String styleableClassPath, String styleableName) {
        mContext = new ContextThemeWrapper(context.getApplicationContext(), themeResId);
        mStyleableClassPath = styleableClassPath;
        mStyleableName = styleableName;
    }

    @Override
    public List<SkinAttr> getSkinAttrs() {
        if (mSkinAttrMap != null) {
            return new ArrayList<>(mSkinAttrMap.values());
        }
        Class styleableClass = ReflectUtil.getClassByPath(mStyleableClassPath);
        if (styleableClass == null) {
            return null;
        }
        int[] attrs = (int[]) ReflectUtil.getStaticFieldValue(styleableClass, mStyleableName);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
        Map<Integer, String> filedNameMap = getStyleableAttrMap(styleableClass, mStyleableName);
        if (filedNameMap != null && !filedNameMap.isEmpty()) {
            mSkinAttrMap = new HashMap<>(filedNameMap.size());
            Set<Map.Entry<Integer, String>> entrySet = filedNameMap.entrySet();
            for (Map.Entry<Integer, String> entry : entrySet) {
                Integer attrIndex = entry.getKey();
                String attrValueKey = entry.getValue().substring(mStyleableName.length() + 1);
                int valueType = ValueType.TYPE_NULL;
                Object attrValue = null;
                String string = typedArray.getString(attrIndex);
                if (!TextUtils.isEmpty(string)) {
                    if (string.startsWith("#")) {
                        int color = typedArray.getColor(attrIndex, 0);
                        valueType = ValueType.TYPE_COLOR_INT;
                        attrValue = color;
                    } else if (string.startsWith("res/color")) {
                        ColorStateList colorStateList = typedArray.getColorStateList(attrIndex);
                        valueType = ValueType.TYPE_COLOR_STATE_LIST;
                        attrValue = colorStateList;
                    } else if (string.startsWith("res/mipmap") || string.startsWith("res/drawable")) {
                        Drawable drawable = typedArray.getDrawable(attrIndex);
                        valueType = ValueType.TYPE_DRAWABLE;
                        attrValue = drawable;
                    }
                }
                if (attrValue != null) {
                    SkinAttr skinAttr = new SkinAttr(attrValueKey, valueType, new AttrValue(valueType, attrValue));
                    mSkinAttrMap.put(attrValueKey, skinAttr);
                }
            }
        }
        typedArray.recycle();
        if (mSkinAttrMap != null) {
            return new ArrayList<>(mSkinAttrMap.values());
        } else {
            return null;
        }
    }

    @Override
    public int getValueType(String attrValueKey) {
        if (mSkinAttrMap != null) {
            SkinAttr skinAttr = mSkinAttrMap.get(attrValueKey);
            if (skinAttr != null) {
                return skinAttr.getValueType();
            }
        }
        return 0;
    }

    @Override
    public AttrValue getAttrValue(String attrValueKey) {
        if (mSkinAttrMap != null) {
            SkinAttr skinAttr = mSkinAttrMap.get(attrValueKey);
            if (skinAttr != null) {
                return skinAttr.getAttrValue();
            }
        }
        return null;
    }

    @Override
    public boolean equals(ISkin skin) {
        return super.equals(skin);
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
