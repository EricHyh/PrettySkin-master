package com.hyh.prettyskin.utils;

import android.text.TextUtils;

/**
 * @author Administrator
 * @description
 * @data 2018/10/17
 */

public class NumberUtil {

    public static boolean isHexNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String hexRegex = "^0[x|X][\\da-fA-F]+$";
        return false;
    }

    public static boolean isOctNumber(String str) {
        return false;
    }

    public static boolean isBinNumber(String str) {
        return false;
    }
}
