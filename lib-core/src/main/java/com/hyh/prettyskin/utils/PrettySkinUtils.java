package com.hyh.prettyskin.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class PrettySkinUtils {

    public static ApplicationInfo getApplicationInfo(Context context, String apkPath) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA);
            packageInfo.applicationInfo.sourceDir = apkPath;
            packageInfo.applicationInfo.publicSourceDir = apkPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return null;
        }
        return packageInfo.applicationInfo;
    }
}