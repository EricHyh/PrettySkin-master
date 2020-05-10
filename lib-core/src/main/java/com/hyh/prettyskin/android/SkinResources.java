package com.hyh.prettyskin.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;


public class SkinResources extends Resources {

    private String mSkinPackageName;

    private SkinResources(AssetManager assets, DisplayMetrics metrics, Configuration config, String skinPackageName) {
        super(assets, metrics, config);
        this.mSkinPackageName = skinPackageName;
    }

    @Override
    public int getIdentifier(String name, String defType, String defPackage) {
        return super.getIdentifier(name, defType, mSkinPackageName);
    }

    public static Resources createSkinResources(Context context, ApplicationInfo applicationInfo) {
        try {
            return context.getPackageManager().getResourcesForApplication(applicationInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Resources createSkinResources(Context context, String skinPath) {
        AssetManager assetManager = createAssetManager(skinPath);
        if (assetManager == null) {
            return null;
        }
        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_META_DATA);
        if (packageInfo == null) {
            return null;
        }
        String packageName = packageInfo.packageName;
        return new SkinResources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration(), packageName);
    }

    @SuppressLint("PrivateApi")
    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            try {
                AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(assetManager, apkPath);
            } catch (Throwable th) {
                Log.e("SkinResources", "createAssetManager failed", th);
            }
            return assetManager;
        } catch (Throwable th) {
            Log.e("SkinResources", "createAssetManager failed", th);
        }
        return null;
    }
}