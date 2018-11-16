package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/11/15
 */

public class Reflect {

    private static final Map<String, Class> CLASS_MAP = new HashMap<>();

    private static final Map<String, Field> FIELD_MAP = new HashMap<>();

    private static final Map<String, Method> METHOD_MAP = new HashMap<>();

    private static final Map<String, Constructor> CONSTRUCTOR_MAP = new HashMap<>();

    //用于保证返回值为基础数据类型时，不返回null
    private static final Map<Class, Object> ELEMENTARY_DEFAULT_VALUE = new HashMap<>(8);

    static {
        ELEMENTARY_DEFAULT_VALUE.put(byte.class, 0);
        ELEMENTARY_DEFAULT_VALUE.put(short.class, 0);
        ELEMENTARY_DEFAULT_VALUE.put(int.class, 0);
        ELEMENTARY_DEFAULT_VALUE.put(long.class, 0L);
        ELEMENTARY_DEFAULT_VALUE.put(float.class, 0.0f);
        ELEMENTARY_DEFAULT_VALUE.put(double.class, 0.0d);
        ELEMENTARY_DEFAULT_VALUE.put(boolean.class, false);
        ELEMENTARY_DEFAULT_VALUE.put(char.class, 0);
    }


    public static Throwable getRealThrowable(Throwable throwable) {
        return getRealThrowable(throwable, 0);
    }

    private static Throwable getRealThrowable(Throwable throwable, int num) {
        if (throwable == null || num >= 10) {
            return throwable;
        }
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return throwable;
        } else {
            return getRealThrowable(throwable, ++num);
        }
    }


    public static <T> T getDefaultValue(Class<T> valueType) {
        return (T) ELEMENTARY_DEFAULT_VALUE.get(valueType);
    }


    public static Class classForName(String className) {
        String key = generateClassMapKey(Reflect.class.getClassLoader(), className);
        Class result = CLASS_MAP.get(key);
        if (result == null) {
            try {
                result = Class.forName(className);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (result != null) {
                CLASS_MAP.put(key, result);
            }
        }
        return result;
    }


    public static Class classForName(ClassLoader classLoader, String className) {
        String key = generateClassMapKey(classLoader, className);
        Class result = CLASS_MAP.get(key);
        if (result == null) {
            try {
                result = classLoader.loadClass(className);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (result != null) {
                CLASS_MAP.put(key, result);
            }
        }
        return result;
    }


    public static Class classForNameWithException(String className) throws ClassNotFoundException {
        String key = generateClassMapKey(Reflect.class.getClassLoader(), className);
        Class result = CLASS_MAP.get(key);
        if (result == null) {
            result = Class.forName(className);
            if (result != null) {
                CLASS_MAP.put(key, result);
            }
        }
        return result;
    }

    public static Class classForNameWithException(ClassLoader classLoader, String className) throws ClassNotFoundException {
        if (classLoader == null) {
            return classForNameWithException(className);
        }
        String key = generateClassMapKey(classLoader, className);
        Class result = CLASS_MAP.get(key);
        if (result == null) {
            result = classLoader.loadClass(className);
            if (result != null) {
                CLASS_MAP.put(key, result);
            }
        }
        return result;
    }


    public static Field getDeclaredField(Class cls, String fieldName) {
        String key = generateFieldMapKey(cls);
        Field field = FIELD_MAP.get(key);
        if (field != null) {
            return field;
        }
        try {
            field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (Exception e) {
            //IGNORE
        }
        if (field == null) {
            Class<?> superclass = cls.getSuperclass();
            if (superclass != null) {
                return getDeclaredField(superclass, fieldName);
            } else {
                return null;
            }
        } else {
            FIELD_MAP.put(key, field);
            return field;
        }
    }


    public static Method getDeclaredMethod(Class cls, String methodName, Class... parameterTypes) {
        String key = generateMethodMapKey(cls, methodName, parameterTypes);
        Method method = METHOD_MAP.get(key);
        if (method != null) {
            return method;
        }
        try {
            method = cls.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
        } catch (Exception e) {
            //IGNORE
        }
        if (method == null) {
            Class<?> superclass = cls.getSuperclass();
            if (superclass != null) {
                return getDeclaredMethod(superclass, methodName, parameterTypes);
            } else {
                return null;
            }
        } else {
            METHOD_MAP.put(key, method);
            return method;
        }
    }

    public static Constructor getDeclaredConstructor(Class cls, Class[] parameterTypes) {
        String key = generateConstructorMapKey(cls, parameterTypes);
        Constructor constructor = CONSTRUCTOR_MAP.get(key);
        if (constructor != null) {
            return constructor;
        }
        try {
            constructor = cls.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
        } catch (Exception e) {
            //IGNORE
        }
        if (constructor != null) {
            CONSTRUCTOR_MAP.put(key, constructor);
        }
        return constructor;
    }

    private static String generateClassMapKey(ClassLoader classLoader, String className) {
        String key = "";
        int hashCode = System.identityHashCode(classLoader);
        key += hashCode;
        key += "-";
        key += className;
        return key;
    }

    private static String generateFieldMapKey(Class cls) {
        return String.valueOf(System.identityHashCode(cls));
    }

    private static String generateMethodMapKey(Class cls, String methodName, Class[] parameterTypes) {
        StringBuilder key = new StringBuilder();
        key.append(System.identityHashCode(cls))
                .append("-")
                .append(methodName)
                .append("-");
        if (parameterTypes == null || parameterTypes.length <= 0) {
            key.append("void");
        } else {
            int length = parameterTypes.length;
            for (int index = 0; index < length; index++) {
                if (index < length - 1) {
                    key.append(System.identityHashCode(parameterTypes[index])).append("-");
                } else {
                    key.append(System.identityHashCode(parameterTypes[index]));
                }
            }
        }
        return key.toString();
    }

    private static String generateConstructorMapKey(Class cls, Class[] parameterTypes) {
        StringBuilder key = new StringBuilder();
        key.append(System.identityHashCode(cls))
                .append("-");
        if (parameterTypes == null || parameterTypes.length <= 0) {
            key.append("void");
        } else {
            int length = parameterTypes.length;
            for (int index = 0; index < length; index++) {
                if (index < length - 1) {
                    key.append(System.identityHashCode(parameterTypes[index])).append("-");
                } else {
                    key.append(System.identityHashCode(parameterTypes[index]));
                }
            }
        }
        return key.toString();
    }


    public static RefClass from(Class<?> cls) {
        return new RefClass(cls);
    }

    public static RefClass from(String className) {
        return new RefClass(className);
    }

    public static RefClass from(ClassLoader classLoader, String className) {
        return new RefClass(classLoader, className);
    }

    public static <T extends TargetApi> T orm(Object target, Class<T> targetApi) {
        return null;
    }
}
