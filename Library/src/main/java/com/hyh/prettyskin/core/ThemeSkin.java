package com.hyh.prettyskin.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.hyh.prettyskin.core.handler.AttrValueHelper;
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
@SuppressLint("UseSparseArrays")
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
        if (attrs == null) {
            return false;
        }
        Map<String, Integer> filedNameMap = AttrValueHelper.getStyleableFieldMap(styleableClass, styleableName);
        if (filedNameMap == null || filedNameMap.isEmpty()) {
            return false;
        }
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
        mSkinAttrMap = new HashMap<>(filedNameMap.size());
        Set<Map.Entry<String, Integer>> entrySet = filedNameMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            String attrValueKey = entry.getKey().substring(styleableName.length() + 1);
            Integer attrIndex = entry.getValue();
            AttrValue attrValue = AttrValueHelper.getAttrValue(mContext, typedArray, attrIndex);
            if (attrValue != null) {
                SkinAttr skinAttr = new SkinAttr(attrValueKey, attrValue);
                mSkinAttrMap.put(attrValueKey, skinAttr);
            }
        }
        typedArray.recycle();
        return true;
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
