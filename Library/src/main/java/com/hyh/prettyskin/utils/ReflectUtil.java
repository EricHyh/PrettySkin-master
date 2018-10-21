package com.hyh.prettyskin.utils;

import java.lang.reflect.Field;

/**
 * @author Administrator
 * @description
 * @data 2018/7/23
 */

public class ReflectUtil {

    public static Field getField(Class clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            Logger.d("getField success : clazz is " + clazz + ", fieldName is " + fieldName);
        } catch (Exception e) {
            Logger.d("getField failed : clazz is " + clazz + ", fieldName is " + fieldName);
        }
        if (field == null) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                return getField(superclass, fieldName);
            } else {
                return null;
            }
        } else {
            return field;
        }
    }


    public static Object getFieldValue(Object obj, String fieldName){
        Field field = getField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean setFieldValue(Object obj, String fieldName, Object fieldValue) {
        Field field = getField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(obj, fieldValue);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static <T> T getStaticFieldValue(String classPath, String fieldName) {
        try {
            Class<?> aClass = Class.forName(classPath);
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getStaticFieldValue(Class clz, String fieldName) {
        Field field = getField(clz, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Class getClassByPath(String styleableClassPath) {
        try {
            return Class.forName(styleableClassPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
