package com.hyh.prettyskin.utils;

import android.util.Log;

import java.util.Locale;

/**
 * @author Administrator
 * @description
 * @data 2018/3/16
 */

public class Logger {

    private static final String TAG = "PRETTY_SKIN";

    private static String generateContent(StackTraceElement caller, String content) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.getDefault(), tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        content = tag + " : " + content;
        return content;
    }


    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void v(String content) {
        StackTraceElement traceElement = getCallerStackTraceElement();
        content = generateContent(traceElement, content);
        Log.v(TAG, content);
    }

    public static void d(String content) {
        d(content, null);
    }

    public static void d(String content, Throwable th) {
        StackTraceElement traceElement = getCallerStackTraceElement();
        content = generateContent(traceElement, content);
        Log.d(TAG, content, th);
    }


    public static void w(String content) {
        StackTraceElement traceElement = getCallerStackTraceElement();
        content = generateContent(traceElement, content);
        Log.w(TAG, content);
    }


    public static void i(String content) {
        StackTraceElement traceElement = getCallerStackTraceElement();
        content = generateContent(traceElement, content);
        Log.i(TAG, content);
    }

    public static void e(String content) {
        StackTraceElement traceElement = getCallerStackTraceElement();
        content = generateContent(traceElement, content);
        Log.e(TAG, content);
    }

    public static void e(String content, Throwable throwable) {
        StackTraceElement traceElement = getCallerStackTraceElement();
        content = generateContent(traceElement, content);
        Log.e(TAG, content, throwable);
    }
}
