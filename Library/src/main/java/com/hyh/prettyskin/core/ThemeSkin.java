package com.hyh.prettyskin.core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.hyh.prettyskin.utils.AttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eric_He on 2018/10/14.
 */
public class ThemeSkin implements ISkin {

    private Context mContext;

    private int themeResId;

    private Class mStyleableClass;

    private String mStyleableName;

    private Map<String, SkinAttr> mSkinAttrMap;

    public ThemeSkin(Context context, int themeResId, Class styleableClass, String styleableName) {
        this.mContext = new ContextThemeWrapper(context.getApplicationContext(), themeResId);
        this.themeResId = themeResId;
        this.mStyleableClass = styleableClass;
        this.mStyleableName = styleableName;
    }

    @Override
    public boolean loadSkinAttrs() {
        final Class styleableClass = mStyleableClass;
        final String styleableName = mStyleableName;
        if (styleableClass == null || TextUtils.isEmpty(styleableName)) {
            return false;
        }
        int[] attrs = Reflect.from(styleableClass).filed(styleableName, int[].class).get(null);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
        Map<Integer, String> filedNameMap = AttrUtil.getStyleableFieldMap(styleableClass, styleableName);
        if (filedNameMap != null && !filedNameMap.isEmpty()) {
            mSkinAttrMap = new HashMap<>(filedNameMap.size());
            Set<Map.Entry<Integer, String>> entrySet = filedNameMap.entrySet();
            for (Map.Entry<Integer, String> entry : entrySet) {
                Integer attrIndex = entry.getKey();
                String attrValueKey = entry.getValue().substring(styleableName.length() + 1);
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
                    SkinAttr skinAttr = new SkinAttr(attrValueKey, new AttrValue(mContext, valueType, attrValue));
                    mSkinAttrMap.put(attrValueKey, skinAttr);
                }
            }
        }
        typedArray.recycle();
        return mSkinAttrMap != null && !mSkinAttrMap.isEmpty();
    }

    @Override
    public List<SkinAttr> getSkinAttrs() {
        if (mSkinAttrMap != null) {
            return new ArrayList<>(mSkinAttrMap.values());
        } else {
            return null;
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThemeSkin themeSkin = (ThemeSkin) o;

        if (themeResId != themeSkin.themeResId) return false;
        if (mStyleableClass != null ? !mStyleableClass.equals(themeSkin.mStyleableClass) : themeSkin.mStyleableClass != null) return false;
        return mStyleableName != null ? mStyleableName.equals(themeSkin.mStyleableName) : themeSkin.mStyleableName == null;
    }
}