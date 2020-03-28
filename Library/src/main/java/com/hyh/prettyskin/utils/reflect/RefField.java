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
            this.throwable = new RefException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }

        Field field = null;
        try {
            field = Reflect.getDeclaredFieldWithException(cls, fieldName);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }

        if (field == null) {
            this.throwable = new RefException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }

        Object result = null;
        boolean getSuccess = false;

        try {
            result = field.get(receiver);
            getSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            this.throwable = new RefException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
        }
        if (getSuccess) {
            E e = ensureResult(result, field.getType());
            if (e == result) {
                onSuccess(e);
            } else {
                onFailure(e, this.throwable);
            }
            return e;
        } else {
            E defaultValue = getDefaultValue(field.getType());
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }
    }


    public E getWithException(Object receiver) throws RefException {
        RefException exception = null;
        if (this.cls == null) {
            exception = new RefException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            throw exception;
        }

        Field field = null;
        try {
            field = Reflect.getDeclaredFieldWithException(cls, fieldName);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }

        if (field == null) {
            exception = new RefException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            throw exception;
        }

        Object result = null;
        boolean getSuccess = false;

        try {
            result = field.get(receiver);
            getSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            exception = new RefException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
            this.throwable = exception;
        }
        if (getSuccess) {
            E e = ensureResult(result, field.getType());
            if (e == result) {
                onSuccess(e);
                return e;
            } else {
                onFailure(e, this.throwable);
                throw (RefException) this.throwable;
            }
        } else {
            E defaultValue = getDefaultValue(field.getType());
            onFailure(defaultValue, this.throwable);
            throw exception;
        }
    }


    public void set(Object receiver, E value) {
        if (this.cls == null) {
            this.throwable = new RefException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            onFailure(null, this.throwable);
            return;
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            this.throwable = new RefException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            onFailure(null, this.throwable);
            return;
        }
        boolean setSuccess = false;
        try {
            field.set(receiver, value);
            setSuccess = true;
            onSuccess(null);
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            this.throwable = new RefException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
        }

        if (!setSuccess) {
            onFailure(null, this.throwable);
        }
    }


    public void setWithException(Object receiver, E value) throws RefException {
        RefException exception = null;
        if (this.cls == null) {
            exception = new RefException("Field[" + fieldName + "] not found, because Class is null", this.throwable);
            this.throwable = exception;
            onFailure(null, this.throwable);
            return;
        }
        Field field = Reflect.getDeclaredField(cls, fieldName);
        if (field == null) {
            exception = new RefException("Field[" + fieldName + "] not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            onFailure(null, this.throwable);
            return;
        }
        boolean setSuccess = false;
        try {
            field.set(receiver, value);
            setSuccess = true;
            onSuccess(null);
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            exception = new RefException("Field[" + fieldName + "] found in Class[" + cls + "], but get value failed", e);
            this.throwable = exception;
        }

        if (!setSuccess) {
            onFailure(null, this.throwable);
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
