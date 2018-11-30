package com.hyh.prettyskin.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eric_He on 2018/10/14.
 */
@SuppressLint("UseSparseArrays")
public class ThemeSkin implements ISkin {

    private Context mContext;

    private int mThemeResId;

    private Class mStyleableClass;

    private String mStyleableName;

    private Map<String, SkinAttr> mSkinAttrMap;

    public ThemeSkin(Context context, int themeResId, Class styleableClass, String styleableName) {
        mContext = new ContextThemeWrapper(context.getApplicationContext(), themeResId);
        mThemeResId = themeResId;
        mStyleableClass = styleableClass;
        mStyleableName = styleableName;
    }

    @Override
    public boolean loadSkinAttrs() {
        if (mSkinAttrMap != null) {
            return true;
        }
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
    public boolean equals(ISkin skin) {
        if (this == skin) {
            return true;
        }
        if (skin != null && skin instanceof ThemeSkin) {
            ThemeSkin other = (ThemeSkin) skin;
            if (other.mThemeResId == this.mThemeResId
                    && other.mStyleableClass == this.mStyleableClass
                    && TextUtils.equals(other.mStyleableName, this.mStyleableName)) {
                return true;
            }
        }
        return false;
    }
}
