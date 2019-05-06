package com.hyh.prettyskin;

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
public class ThemeSkin implements ISkin {

    private Context mContext;

    private int themeResId;

    private Class mStyleableClass;

    private String mStyleableName;

    private Map<String, AttrValue> mSkinAttrMap;

    public ThemeSkin(Context context, int themeResId, Class styleableClass, String styleableName) {
        this.mContext = new ContextThemeWrapper(context.getApplicationContext(), themeResId);
        this.themeResId = themeResId;
        this.mStyleableClass = styleableClass;
        this.mStyleableName = styleableName;
    }

    @Override
    public boolean loadSkinAttrs() {
        if (mSkinAttrMap != null) return true;
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
            String attrKey = entry.getKey().substring(styleableName.length() + 1);
            Integer attrIndex = entry.getValue();
            AttrValue attrValue = AttrValueHelper.getAttrValue(mContext, typedArray, attrIndex);
            if (attrValue != null) {
                mSkinAttrMap.put(attrKey, attrValue);
            }
        }
        typedArray.recycle();
        return true;
    }

    @Override
    public AttrValue getAttrValue(String attrKey) {
        return mSkinAttrMap == null ? null : mSkinAttrMap.get(attrKey);
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
