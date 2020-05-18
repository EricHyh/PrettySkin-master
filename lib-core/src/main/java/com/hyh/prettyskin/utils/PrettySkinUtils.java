package com.hyh.prettyskin.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


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


    public static long getVersionCode(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return packageInfo.getLongVersionCode();
        } else {
            return packageInfo.versionCode;
        }
    }

    public static void saveLastAppVersionCode(Context context, String assetsPath) {
        context.getSharedPreferences("pretty_skin_config", Context.MODE_PRIVATE)
                .edit()
                .putLong("last_app_version_code_" + System.identityHashCode(assetsPath), getVersionCode(context))
                .apply();
    }

    public static long getLastAppVersionCode(Context context, String assetsPath) {
        return context.getSharedPreferences("pretty_skin_config", Context.MODE_PRIVATE)
                .getLong("last_app_version_code_" + System.identityHashCode(assetsPath), 0L);
    }
}