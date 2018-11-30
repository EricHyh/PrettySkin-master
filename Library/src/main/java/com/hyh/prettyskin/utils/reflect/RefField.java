package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Field;

/**
 * @author Administrator
 * @description
 * @data 2018/11/15
 */

public class RefField<E> extends RefAccessible<E, RefField<E>> {

    private Class cls;

    private String fieldName;

    private Class<E> fieldType;

    RefField(Class cls, String fieldName, Class<E> fieldType, Throwable throwable) {
        super(throwable);
        this.cls = cls;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.throwable = throwable;
    }

    public E get(Object receiver) {
        if (this.cls == null) {
            this.throwable = new ReflectException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return defaultValue;
        }

        Field field = null;
        try {
            field = Reflect.getDeclaredFieldWithException(cls, fieldName);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }

        if (field == null) {
            this.throwable = new ReflectException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return defaultValue;
        }

        Object result = null;
        boolean getSuccess = false;

        try {
            result = field.get(receiver);
            getSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            this.throwable = new ReflectException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
        }
        if (getSuccess) {
            E e = ensureResult(result, field.getType());
            if (e == result) {
                saveSuccess(e);
            } else {
                saveFailure(e);
                tryToPrintException();
            }
            return e;
        } else {
            E e = getDefaultValue(field.getType());
            saveFailure(e);
            tryToPrintException();
            return e;
        }
    }


    public Object getWithException(Object receiver) throws ReflectException {
        ReflectException exception = null;
        if (this.cls == null) {
            exception = new ReflectException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            throw exception;
        }

        Field field = null;
        try {
            field = Reflect.getDeclaredFieldWithException(cls, fieldName);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }

        if (field == null) {
            exception = new ReflectException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            throw exception;
        }

        Object result = null;
        boolean getSuccess = false;

        try {
            result = field.get(receiver);
            getSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            exception = new ReflectException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
            this.throwable = exception;
        }
        if (getSuccess) {
            E e = ensureResult(result, field.getType());
            if (e == result) {
                saveSuccess(e);
                return e;
            } else {
                saveFailure(e);
                tryToPrintException();
                throw (ReflectException) this.throwable;
            }
        } else {
            E e = getDefaultValue(field.getType());
            saveFailure(e);
            tryToPrintException();
            throw exception;
        }
    }


    public void set(Object receiver, E value) {
        if (this.cls == null) {
            this.throwable = new ReflectException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return;
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            this.throwable = new ReflectException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return;
        }
        boolean setSuccess = false;
        try {
            field.set(receiver, value);
            setSuccess = true;
            saveSuccess(null);
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            this.throwable = new ReflectException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
        }

        if (!setSuccess) {
            saveFailure(null);
            tryToPrintException();
        }
    }


    public void setWithException(Object receiver, E value) throws ReflectException {
        ReflectException exception = null;
        if (this.cls == null) {
            exception = new ReflectException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return;
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            exception = new ReflectException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return;
        }
        boolean setSuccess = false;
        try {
            field.set(receiver, value);
            setSuccess = true;
            saveSuccess(null);
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            exception = new ReflectException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
            this.throwable = exception;
        }

        if (!setSuccess) {
            saveFailure(null);
            tryToPrintException();
            throw exception;
        }
    }

    @Override
    Class<E> getEnsureType() {
        return fieldType;
    }

    @Override
    String getResultTypeErrorMessage(Class resultType) {
        return "Filed[" + fieldName + "] found in Class[" + cls + "]" +
                ", and get value success" +
                ", but value type[" + resultType + "] is not extends from specified fieldType[" + fieldType + "]" +
                ", please specify a right fieldType";
    }
}
