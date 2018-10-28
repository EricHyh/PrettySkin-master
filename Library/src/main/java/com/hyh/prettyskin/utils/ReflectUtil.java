package com.hyh.prettyskin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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


    public static Object getFieldValue(Object obj, String fieldName) {
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

    public static Method getMethod(Class clz, String methodName, Class<?>[] parameterTypes) {
        if (clz == null) {
            return null;
        }
        try {
            Method method = clz.getDeclaredMethod(methodName, parameterTypes);
            if (method != null) {
                return method;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getMethod(clz.getSuperclass(), methodName, parameterTypes);
    }


    public static Object invokeStaticMethod(Class clz, String methodName, Class<?>[] parameterTypes, Object... params) {
        try {
            Method method = clz.getDeclaredMethod(methodName, parameterTypes);
            return method.invoke(null, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object... params) {
        Object result = null;
        try {
            Method method = getMethod(object.getClass(), methodName, parameterTypes);
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(object, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
