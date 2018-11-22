package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Constructor;
import java.util.Locale;

/**
 * @author Administrator
 * @description
 * @data 2018/11/16
 */

public class RefConstructor<E> extends RefExecutable<E, RefConstructor<E>> {

    private Class<E> cls;

    RefConstructor(Class cls, Throwable throwable) {
        super(throwable);
        this.cls = cls;
    }

    public E newInstance() {
        if (cls == null) {
            this.throwable = new ReflectException("Constructor: " + getConstructorSignature() + " not found, because Class is null", this.throwable);
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            return defaultValue;
        }
        Constructor<E> constructor = null;
        try {
            constructor = Reflect.getDeclaredConstructor(cls, getParameterTypes());
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
        if (constructor == null) {
            this.throwable = new ReflectException("Constructor: " + getConstructorSignature() + " not found in Class[" + cls + "]", this.throwable);
            E defaultValue = getDefaultValue(cls);
            saveFailure(defaultValue);
            tryToPrintException();
            return defaultValue;
        }

        E result = null;
        boolean newInstanceSuccess = false;

        try {
            result = constructor.newInstance(getParameters());
            newInstanceSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            this.throwable = new ReflectException("Constructor: " + getConstructorSignature() + " found in Class[" + cls + "], but newInstance failed", e);
        }
        if (newInstanceSuccess) {
            saveSuccess(result);
            return result;
        } else {
            E e = getDefaultValue(cls);
            saveFailure(e);
            tryToPrintException();
            return e;
        }
    }


    public E newInstanceWithException() throws ReflectException {
        ReflectException exception = null;
        if (cls == null) {
            exception = new ReflectException("Constructor: " + getConstructorSignature() + " not found, because Class is null", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            saveFailure(defaultValue);
            tryToPrintException();
            throw exception;
        }
        Constructor<E> constructor = null;
        try {
            constructor = Reflect.getDeclaredConstructorWithException(cls, getParameterTypes());
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
        if (constructor == null) {
            exception = new ReflectException("Constructor: " + getConstructorSignature() + " not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(cls);
            saveFailure(defaultValue);
            tryToPrintException();
            throw exception;
        }

        E result = null;
        boolean newInstanceSuccess = false;

        try {
            result = constructor.newInstance(getParameters());
            newInstanceSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            exception = new ReflectException("Constructor: " + getConstructorSignature() + " found in Class[" + cls + "], but newInstance failed", e);
            this.throwable = exception;
        }
        if (newInstanceSuccess) {
            saveSuccess(result);
            return result;
        } else {
            E e = getDefaultValue(cls);
            saveFailure(e);
            tryToPrintException();
            throw exception;
        }
    }

    @Override
    Class<E> getEnsureType() {
        return cls;
    }

    @Override
    String getResultTypeErrorMessage(Class resultType) {
        return null;
    }

    private String getConstructorSignature() {
        String parameterStr = "";
        Class[] parameterTypes = getParameterTypes();
        int length = parameterTypes.length;
        if (length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < length; index++) {
                if (index < length - 1) {
                    sb.append(parameterTypes[index].getSimpleName()).append(", ");
                } else {
                    sb.append(parameterTypes[index].getSimpleName());
                }
            }
            parameterStr = sb.toString();
        }
        return String.format(Locale.getDefault(), "init(%s)", parameterStr);
    }
}
