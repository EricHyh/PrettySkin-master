package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Constructor;
import java.util.Locale;



public class RefConstructor<E> extends RefExecutable<E, RefConstructor<E>> {

    private Class<E> cls;

    RefConstructor(Class cls, Throwable throwable) {
        super(throwable);
        this.cls = cls;
    }

    @Override
    public RefConstructor<E> defaultValue(E defaultValue) {
        return super.defaultValue(defaultValue);
    }

    @Override
    public RefConstructor<E> defaultValue(Lazy<E> defaultValue) {
        return super.defaultValue(defaultValue);
    }

    @Override
    public RefConstructor<E> saveResult(RefResult<E> result) {
        return super.saveResult(result);
    }

    @Override
    public RefConstructor<E> printException() {
        return super.printException();
    }

    @Override
    public RefConstructor<E> params(Class[] types, Object[] params) {
        return super.params(types, params);
    }

    @Override
    public RefConstructor<E> params(String[] typePaths, Object[] params) {
        return super.params(typePaths, params);
    }

    @Override
    public RefConstructor<E> params(ClassLoader typeClassLoader, String[] typePaths, Object[] params) {
        return super.params(typeClassLoader, typePaths, params);
    }

    @Override
    public  RefConstructor<E> param(Class type, Object param) {
        return super.param(type, param);
    }

    @Override
    public RefConstructor<E> param(String typePath, Object param) {
        return super.param(typePath, param);
    }

    @Override
    public RefConstructor<E> param(ClassLoader typeClassLoader, String typePath, Object param) {
        return super.param(typeClassLoader, typePath, param);
    }

    public E newInstance() {
        if (cls == null) {
            this.throwable = new RefException("Constructor: " + getConstructorSignature() + " not found, because Class is null", this.throwable);
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }
        Constructor<E> constructor = null;
        try {
            constructor = Reflect.getDeclaredConstructor(cls, getParameterTypes());
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
        if (constructor == null) {
            this.throwable = new RefException("Constructor: " + getConstructorSignature() + " not found in Class[" + cls + "]", this.throwable);
            E defaultValue = getDefaultValue(cls);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }

        E result = null;
        boolean newInstanceSuccess = false;

        try {
            result = constructor.newInstance(getParameters());
            newInstanceSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            /*this.throwable = new RefException("Constructor: " + getConstructorSignature() + " found in Class[" + cls + "], but newInstance " +
                    "failed", e);*/
            this.throwable = e;
        }
        if (newInstanceSuccess) {
            onSuccess(result);
            return result;
        } else {
            E defaultValue = getDefaultValue(cls);
            onFailure(defaultValue, this.throwable);
            return defaultValue;
        }
    }


    public E newInstanceWithException() throws Throwable {
        Throwable exception = null;
        if (cls == null) {
            exception = new RefException("Constructor: " + getConstructorSignature() + " not found, because Class is null", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(null);
            onFailure(defaultValue, this.throwable);
            throw exception;
        }
        Constructor<E> constructor = null;
        try {
            constructor = Reflect.getDeclaredConstructorWithException(cls, getParameterTypes());
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
        if (constructor == null) {
            exception = new RefException("Constructor: " + getConstructorSignature() + " not found in Class[" + cls + "]", this.throwable);
            this.throwable = exception;
            E defaultValue = getDefaultValue(cls);
            onFailure(defaultValue, this.throwable);
            throw exception;
        }

        E result = null;
        boolean newInstanceSuccess = false;

        try {
            result = constructor.newInstance(getParameters());
            newInstanceSuccess = true;
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            /*exception = new RefException("Constructor: " + getConstructorSignature() + " found in Class[" + cls + "], but newInstance failed", e);*/
            exception = e;
            this.throwable = exception;
        }
        if (newInstanceSuccess) {
            onSuccess(result);
            return result;
        } else {
            E defaultValue = getDefaultValue(cls);
            onFailure(defaultValue, this.throwable);
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
