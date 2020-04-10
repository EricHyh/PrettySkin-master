package com.hyh.prettyskin.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Administrator
 * @description
 * @data 2018/10/17
 */

public class NumberUtil {

    public static boolean isBinNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String binRegex = "^[-\\+]?0[b|B][0-1]+$";
        return str.matches(binRegex);
    }

    public static boolean isOctNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String octRegex = "^[-\\+]?0[0-7]+$";
        return str.matches(octRegex);
    }

    public static boolean isDecNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if ("0".equals(str) || "-0".equals(str)) {
            return true;
        }
        String binRegex = "^[-\\+]?[1-9]\\d*$";
        return str.matches(binRegex);
    }

    public static boolean isHexNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String hexRegex = "^[-\\+]?0[x|X][\\da-fA-F]+$";
        return str.matches(hexRegex);
    }

    public static boolean isIntegerNumber(String str) {
        return isBinNumber(str) || isOctNumber(str) || isDecNumber(str) || isHexNumber(str);
    }

    public static boolean isFloatNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String floatRegex1 = "^[-\\+]?\\d+\\.\\d+[f|F]?$";
        String floatRegex2 = "^[-\\+]?\\d+e\\d+[f|F]?$";
        return str.matches(floatRegex1) || str.matches(floatRegex2);
    }

    public static boolean isNumber(String str) {
        return isBinNumber(str) || isOctNumber(str) || isDecNumber(str) || isHexNumber(str) || isFloatNumber(str);
    }


    public static BigInteger parseInteger(String str) {
        if (isBinNumber(str)) {
            return new BigInteger(str.replaceAll("0[b|B]", ""), 2);
        } else if (isOctNumber(str)) {
            return new BigInteger(str, 8);
        } else if (isDecNumber(str)) {
            return new BigInteger(str, 10);
        } else if (isHexNumber(str)) {
            return new BigInteger(str.replaceAll("0[x|X]", ""), 16);
        }
        return null;
    }

    public static BigDecimal parseFloat(String str) {
        if (isFloatNumber(str)) {
            return new BigDecimal(str);
        }
        return null;
    }

    public static Number parseNumber(String str) {
        if (isBinNumber(str)) {
            return new BigInteger(str.replaceAll("0[b|B]", ""), 2);
        } else if (isOctNumber(str)) {
            return new BigInteger(str, 8);
        } else if (isDecNumber(str)) {
            return new BigInteger(str, 10);
        } else if (isHexNumber(str)) {
            return new BigInteger(str.replaceAll("0[x|X]", ""), 16);
        } else if (isFloatNumber(str)) {
            return new BigDecimal(str);
        }
        return null;
    }
}
