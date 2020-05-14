package com.hyh.prettyskin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;

import com.hyh.prettyskin.android.SkinContext;
import com.hyh.prettyskin.android.SkinResources;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.PrettySkinUtils;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import dalvik.system.DexClassLoader;


public class ApkThemeSkin extends BaseSkin {

    private static final String SKIN_R_CLASS_PATH = "skin_r_class_path";
    private static final String SKIN_DECLARE_STYLEABLE = "skin_declare_styleable";
    private static final String SKIN_THEME_LIST = "skin_theme_list";

    private final Context mApplicationContext;
    private final String mApkPath;
    private int mSkinIndex;

    private SkinContext mSkinContext;
    private Map<String, AttrValue> mInnerSkinAttrMap;

    public ApkThemeSkin(Context context, String apkPath) {
        this(context, apkPath, 0);
    }

    public ApkThemeSkin(Context context, String apkPath, int skinIndex) {
        this.mApplicationContext = context.getApplicationContext();
        this.mApkPath = apkPath;
        this.mSkinIndex = skinIndex;
    }

    @Override
    public boolean loadSkinAttrs() {
        if (mInnerSkinAttrMap != null) return true;
        ApplicationInfo applicationInfo = PrettySkinUtils.getApplicationInfo(mApplicationContext, mApkPath);
        if (applicationInfo == null) return false;
        Bundle metaData = applicationInfo.metaData;
        if (metaData == null) return false;
        String skinRClassPath = metaData.getString(SKIN_R_CLASS_PATH);
        if (TextUtils.isEmpty(skinRClassPath)) return false;
        String skinDeclareStyleable = metaData.getString(SKIN_DECLARE_STYLEABLE);
        if (TextUtils.isEmpty(skinDeclareStyleable)) return false;

        Resources resources = SkinResources.createSkinResources(mApplicationContext, applicationInfo);
        if (resources == null) return false;

        int skinThemeArrayId = metaData.getInt(SKIN_THEME_LIST);
        String[] skinThemeArray = resources.getStringArray(skinThemeArrayId);

        if (skinThemeArray.length < mSkinIndex) return false;
        String skinTheme = skinThemeArray[mSkinIndex];
        int skinThemeId = metaData.getInt(skinTheme);
        if (skinThemeId == 0) return false;

        File filesDir = mApplicationContext.getFilesDir();
        File skinDexDir = new File(filesDir, "skin".concat(File.separator).concat("dex"));
        skinDexDir.mkdirs();
        DexClassLoader classLoader = new DexClassLoader(mApkPath,
                skinDexDir.getAbsolutePath(),
                null,
                Context.class.getClassLoader());

        final Class<?> styleableClass = getStyleableClass(classLoader, skinRClassPath);
        final String styleableName = skinDeclareStyleable;

        if (styleableClass == null || TextUtils.isEmpty(styleableName)) {
            return false;
        }
        final int[] attrs = Reflect.from(styleableClass).filed(styleableName, int[].class).get(null);
        if (attrs == null) {
            return false;
        }
        Map<String, Integer> filedNameMap = AttrValueHelper.getStyleableFieldMap(styleableClass, styleableName);
        if (filedNameMap == null || filedNameMap.isEmpty()) {
            return false;
        }

        mSkinContext = new SkinContext(mApplicationContext, resources, skinThemeId);
        TypedArray typedArray = mSkinContext.obtainStyledAttributes(attrs);

        @SuppressLint("Recycle") TypedArrayFactory typedArrayFactory = () -> mSkinContext.obtainStyledAttributes(attrs);

        mInnerSkinAttrMap = new HashMap<>(filedNameMap.size());
        Set<Map.Entry<String, Integer>> entrySet = filedNameMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            String attrKey = entry.getKey().substring(styleableName.length() + 1);
            Integer attrIndex = entry.getValue();
            AttrValue attrValue = AttrValueHelper.getAttrValue(mSkinContext, typedArray, typedArrayFactory, attrIndex);
            if (attrValue != null) {
                mInnerSkinAttrMap.put(attrKey, attrValue);
            }
        }
        typedArray.recycle();
        return true;
    }

    private Class getStyleableClass(DexClassLoader classLoader, String skinRClassPath) {
        try {
            return classLoader.loadClass(skinRClassPath + "$styleable");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected AttrValue getInnerAttrValue(String attrKey) {
        return mInnerSkinAttrMap == null ? null : mInnerSkinAttrMap.get(attrKey);
    }
}