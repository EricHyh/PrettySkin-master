package com.hyh.prettyskin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;

import com.hyh.prettyskin.android.SkinContext;
import com.hyh.prettyskin.android.SkinResources;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.PackageUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import dalvik.system.DexClassLoader;

/**
 * @author Administrator
 * @description
 * @data 2019/4/30
 */

public class ApkThemeSkin implements ISkin {

    private static final String SKIN_R_CLASS_PATH = "skin_r_class_path";
    private static final String SKIN_DECLARE_STYLEABLE = "skin_declare_styleable";
    private static final String SKIN_THEME_LIST = "skin_theme_list";

    private final Context mApplicationContext;
    private final String mApkPath;
    private final int mIndex;

    private HashMap<String, AttrValue> mSkinAttrMap;

    public ApkThemeSkin(Context context, String apkPath) {
        this(context, apkPath, 0);
    }

    public ApkThemeSkin(Context context, String apkPath, int index) {
        this.mApplicationContext = context.getApplicationContext();
        this.mApkPath = apkPath;
        this.mIndex = index;
    }

    @Override
    public boolean loadSkinAttrs() {
        if (mSkinAttrMap != null) return true;
        ApplicationInfo applicationInfo = PackageUtil.getApplicationInfo(mApplicationContext, mApkPath);
        if (applicationInfo == null) return false;
        Bundle metaData = applicationInfo.metaData;
        if (metaData == null) return false;
        String skinRClassPath = metaData.getString(SKIN_R_CLASS_PATH);
        if (TextUtils.isEmpty(skinRClassPath)) return false;
        String skinDeclareStyleable = metaData.getString(SKIN_DECLARE_STYLEABLE);
        if (TextUtils.isEmpty(skinDeclareStyleable)) return false;
        Resources resources = SkinResources.createSkinResources(mApplicationContext, mApkPath);
        if (resources == null) return false;

        int skinThemeArrayId = metaData.getInt(SKIN_THEME_LIST);
        String[] skinThemeArray = resources.getStringArray(skinThemeArrayId);

        if (skinThemeArray.length < mIndex) return false;
        String skinTheme = skinThemeArray[mIndex];
        int skinThemeId = metaData.getInt(skinTheme);
        if (skinThemeId == 0) return false;


        File dataDir = mApplicationContext.getFilesDir();
        File skinDexDir = new File(dataDir, "skin".concat(File.separator).concat("dex"));
        DexClassLoader classLoader = new DexClassLoader(mApkPath,
                skinDexDir.getAbsolutePath(),
                null,
                mApplicationContext.getClassLoader().getParent());


        final Class styleableClass = getStyleableClass(classLoader, skinRClassPath);
        final String styleableName = skinDeclareStyleable;

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

        SkinContext skinContext = new SkinContext(mApplicationContext, resources, skinThemeId);
        TypedArray typedArray = skinContext.obtainStyledAttributes(attrs);

        mSkinAttrMap = new HashMap<>(filedNameMap.size());
        Set<Map.Entry<String, Integer>> entrySet = filedNameMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            String attrKey = entry.getKey().substring(styleableName.length() + 1);
            Integer attrIndex = entry.getValue();
            AttrValue attrValue = AttrValueHelper.getAttrValue(skinContext, typedArray, attrIndex);
            if (attrValue != null) {
                mSkinAttrMap.put(attrKey, attrValue);
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
    public AttrValue getAttrValue(String attrKey) {
        return mSkinAttrMap == null ? null : mSkinAttrMap.get(attrKey);
    }
}