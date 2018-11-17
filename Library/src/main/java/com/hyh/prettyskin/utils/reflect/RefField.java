package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Field;

/**
 * @author Administrator
 * @description
 * @data 2018/11/15
 */

public class RefField {

    private Class cls;

    private String fieldName;

    private Throwable throwable;

    private Object defaultValue;

    private ReflectResult reflectResult;

    private boolean printException;

    public RefField(Class cls, String fieldName, Throwable throwable) {
        this.cls = cls;
        this.fieldName = fieldName;
        this.throwable = throwable;
    }

    public RefField defaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }


    public RefField printException() {
        this.printException = true;
        return this;
    }

    public RefField saveResult(ReflectResult result) {
        this.reflectResult = result;
        return this;
    }


    public Object get(Object receiver) {
        if (this.cls == null) {
            return filedNotFoundOnGetField();
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            return filedNotFoundOnGetField();
        }

        Object result = null;
        Throwable throwable = null;
        boolean getSuccess = false;

        try {
            result = field.get(receiver);
            getSuccess = true;
        } catch (Throwable e) {
            throwable = Reflect.getRealThrowable(e);
        }
        if (getSuccess) {
            return getSuccess(result);
        } else {
            return getFailure(field, throwable);
        }
    }


    public Object getWithException(Object receiver) throws Throwable {
        if (this.cls == null) {
            throw filedNotFoundException();
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            throw filedNotFoundException();
        }

        Object result = null;
        Throwable throwable = null;
        boolean getSuccess = false;

        try {
            result = field.get(receiver);
            getSuccess = true;
        } catch (Throwable e) {
            throwable = Reflect.getRealThrowable(e);
        }
        if (getSuccess) {
            return getSuccess(result);
        } else {
            throw getFailureException(field, throwable);
        }
    }


    public void set(Object receiver, Object value) {
        if (this.cls == null) {
            filedNotFoundOnSetField();
            return;
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            filedNotFoundOnSetField();
            return;
        }

        Throwable throwable = null;

        try {
            field.set(receiver, value);
        } catch (Throwable e) {
            throwable = Reflect.getRealThrowable(e);
        }

        if (throwable != null && printException) {
            throwable.printStackTrace();
        }
    }


    private Object filedNotFoundOnGetField() {
        ReflectException reflectException = new ReflectException("filed[" + fieldName + "] not found in Class[" + cls + "]");
        if (throwable != null) {
            reflectException.setStackTrace(throwable.getStackTrace());
        }
        if (printException) {
            reflectException.printStackTrace();
        }
        Object defaultValue = getDefaultValue(null);
        if (reflectResult != null) {
            reflectResult.setResult(defaultValue);
            reflectResult.setThrowable(reflectException);
        }
        return defaultValue;
    }


    private void filedNotFoundOnSetField() {
        ReflectException reflectException = new ReflectException("filed[" + fieldName + "] not found in Class[" + cls + "]");
        if (throwable != null) {
            reflectException.setStackTrace(throwable.getStackTrace());
        }
        if (printException) {
            reflectException.printStackTrace();
        }
        if (reflectResult != null) {
            reflectResult.setThrowable(reflectException);
        }
    }


    private Throwable filedNotFoundException() {
        ReflectException reflectException = new ReflectException("filed[" + fieldName + "] not found in Class[" + cls + "]");
        if (throwable != null) {
            reflectException.setStackTrace(throwable.getStackTrace());
        }
        if (printException) {
            reflectException.printStackTrace();
        }
        Object defaultValue = getDefaultValue(null);
        if (reflectResult != null) {
            reflectResult.setResult(defaultValue);
            reflectResult.setThrowable(reflectException);
        }
        return reflectException;
    }

    private Object getSuccess(Object result) {
        if (reflectResult != null) {
            reflectResult.setResult(result);
            reflectResult.setSuccess(true);
        }
        return result;
    }

    private Object getFailure(Field field, Throwable throwable) {
        if (printException) {
            throwable.printStackTrace();
        }
        Object result = getDefaultValue(field);
        if (reflectResult != null) {
            reflectResult.setThrowable(throwable);
            reflectResult.setResult(result);
        }
        return result;
    }

    private Throwable getFailureException(Field field, Throwable throwable) {
        if (printException) {
            throwable.printStackTrace();
        }
        Object result = getDefaultValue(field);
        if (reflectResult != null) {
            reflectResult.setThrowable(throwable);
            reflectResult.setResult(result);
        }
        return throwable;
    }


    private Object getDefaultValue(Field field) {
        if (defaultValue == null && field != null) {
            defaultValue = Reflect.getDefaultValue(field.getType());
        }
        return defaultValue;
    }
}
