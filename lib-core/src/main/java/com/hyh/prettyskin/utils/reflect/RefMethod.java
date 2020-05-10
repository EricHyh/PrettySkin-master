package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Method;
import java.util.Locale;



public class RefMethod<E> extends RefExecutable<E, RefMethod<E>> {

    private Class cls;

    private String methodName;

    private Class<E> returnType;

    RefMethod(Class cls, String methodName, Class<E> returnType, Throwable throwable) {
        super(throwable);
        this.cls = cls;
        this.methodName = methodName;
        this.returnType = returnType;
    }

    public E invoke(Object receiver) {
        if (cls == null) {
            this.throwable = new RefException("Method: " + getMethodSignature() + " not found, because Class is null", this.throwable);
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }
        Method method = null;
        try {
            method = Reflect.getDeclaredMethodWithException(cls, methodName, getParameterTypes());
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
        if (method == null) {
            this.throwable = new RefException("Method: " + getMethodSignature() + " not found in Class[" + cls + "]", this.throwable);
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }

        Object result = null;
        boolean invokeSuccess = false;

        try {
            result = method.invoke(receiver, getParameters());
            invokeSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            //this.throwable = new RefException("Method: " + getMethodSignature() + " found in Class[" + cls + "], but invoke failed", e);
            this.throwable = e;
        }
        if (invokeSuccess) {
            E e = ensureResult(result, method.getReturnType());
            if (e == result) {
                onSuccess(e);
            } else {
                onFailure(e, this.throwable);
            }
            return e;
        } else {
            E defaultValue = getDefaultValue(method.getReturnType());
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }
    }


    public E invokeWithException(Object receiver) throws Throwable {
        Throwable exception = null;
        if (cls == null) {
            exception = new RefException("Method: " + getMethodSignature() + " not found, because Class is null", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            throw exception;
        }
        Method method = null;
        try {
            method = Reflect.getDeclaredMethodWithException(cls, methodName, getParameterTypes());
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
        if (method == null) {
            exception = new RefException("Method: " + getMethodSignature() + " not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            throw exception;
        }

        Object result = null;
        boolean invokeSuccess = false;

        try {
            result = method.invoke(receiver, getParameters());
            invokeSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            //exception = new RefException("Method: " + getMethodSignature() + " found in Class[" + cls + "], but invoke failed", e);
            exception = e;
            this.throwable = exception;
        }
        if (invokeSuccess) {
            E e = ensureResult(result, method.getReturnType());
            if (e == result) {
                onSuccess(e);
                return e;
            } else {
                onFailure(e, this.throwable);
                throw this.throwable;
            }
        } else {
            E defaultValue = getDefaultValue(method.getReturnType());
            onFailure(defaultValue, this.throwable);
            throw exception;
        }
    }


    @Override
    Class<E> getEnsureType() {
        return returnType;
    }

    @Override
    String getResultTypeErrorMessage(Class resultType) {
        return "Method: " + getMethodSignature() + " found in Class[" + cls + "]" +
                ", and get value success" +
                ", but value type[" + resultType + "] is not extends from specified returnType[" + returnType + "]" +
                ", please specify a right returnType";
    }

    private String getMethodSignature() {
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
        return String.format(Locale.getDefault(), "%s(%s)", methodName, parameterStr);
    }
}
