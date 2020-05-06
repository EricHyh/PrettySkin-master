/*
 * 文件名: Share.java
 * 描述: SharePreferences工具类
 * 修改人: [ulegendtimes][tangdongwei]
 * 修改日期: 2015-6-9 下午6:30:27
 * 修改内容: 新增
 */
package com.hyh.prettyskin.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceUtil {

    public static final String SHARE_NAME = "pretty_skin_config";

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences mPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return mPreferences.getInt(key, defaultValue);
    }

    public static int getInt(Context context, String shareFileName, String key) {
        if (TextUtils.isEmpty(shareFileName) || TextUtils.isEmpty(key)) {
            return 0;
        }
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static int getInt(Context context, String shareFileName, String key, int defaultValue) {
        if (TextUtils.isEmpty(shareFileName) || TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        SharedPreferences mPreferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return mPreferences.getInt(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences mPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putInt(Context context, String shareFileName, String key, int value) {
        if (TextUtils.isEmpty(shareFileName) || TextUtils.isEmpty(key)) {
            return;
        }
        SharedPreferences mPreferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences mPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return mPreferences.getFloat(key, defaultValue);
    }

    public static float getFloat(Context context, String shareFileName, String key) {
        if (TextUtils.isEmpty(shareFileName) || TextUtils.isEmpty(key)) {
            return 0.0f;
        }
        SharedPreferences mPreferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return mPreferences.getFloat(key, 0.0f);
    }

    public static float getFloat(Context context, String shareFileName, String key, float defaultValue) {
        if (TextUtils.isEmpty(shareFileName) || TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        SharedPreferences mPreferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return mPreferences.getFloat(key, defaultValue);
    }

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences mPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putFloat(Context context, String shareFileName, String key, float value) {
        if (TextUtils.isEmpty(shareFileName) || TextUtils.isEmpty(key)) {
            return;
        }
        SharedPreferences mPreferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }


    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static String getString(Context context, String shareFileName, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (context == null)
            return false;

        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String shareFileName, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, 0L);
    }

    public static long getLong(Context context, String key, long defaultData) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultData);
    }

    public static long getLong(Context context, String shareFileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static long getLong(Context context, String shareFileName, String key, long defaultData) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultData);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putString(Context context, String shareFileName, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (context == null)
            return;

        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putBoolean(Context context, String shareFileName, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void putLong(Context context, String shareFileName, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void clear(Context context, String shareFileName) {
        SharedPreferences preferences = context.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void registerOnSharedPreferenceChangeListener(Context context,
                                                                SharedPreferences.OnSharedPreferenceChangeListener listener) {
        context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(listener);
    }

    public static void registerOnSharedPreferenceChangeListener(Context context,
                                                                String shareName,
                                                                SharedPreferences.OnSharedPreferenceChangeListener listener) {
        context.getSharedPreferences(shareName, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnSharedPreferenceChangeListener(Context context,
                                                                  SharedPreferences.OnSharedPreferenceChangeListener listener) {
        context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnSharedPreferenceChangeListener(Context context,
                                                                  String shareName,
                                                                  SharedPreferences.OnSharedPreferenceChangeListener listener) {
        context.getSharedPreferences(shareName, Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static boolean contains(Context context, String key) {
        return context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE).contains(key);
    }

    public static boolean contains(Context context, String shareName, String key) {
        return context.getSharedPreferences(shareName, Context.MODE_PRIVATE).contains(key);
    }
}