package com.hyh.prettyskin.demo.net;

import android.content.Context;

import java.lang.reflect.Method;

import okhttp3.Interceptor;

public class StethoHelper {

    static Interceptor createStethoInterceptor() {
        try {
            Class<?> aClass = Class.forName("com.facebook.stetho.okhttp3.StethoInterceptor");
            Object instance = aClass.newInstance();
            return (Interceptor) instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init(Context context) {
        try {
            Class<?> loadClass = Class.forName("com.facebook.stetho.Stetho");
            Method method = loadClass.getDeclaredMethod("initializeWithDefaults", Context.class);
            method.invoke(null, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}