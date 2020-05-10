package com.hyh.prettyskin.utils.reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class RefExecutable<E, T extends RefExecutable<E, T>> extends RefAccessible<E, T> {

    private List<Class> parameterTypeList = new ArrayList<>();

    private List<Object> parameterList = new ArrayList<>();

    RefExecutable(Throwable throwable) {
        super(throwable);
    }

    public T params(Class[] types, Object[] params) {
        if (types != null) {
            parameterTypeList.addAll(Arrays.asList(types));
        }
        if (params != null) {
            parameterList.addAll(Arrays.asList(params));
        }
        return (T) this;
    }

    public T params(String[] typePaths, Object[] params) {
        return params(RefMethod.class.getClassLoader(), typePaths, params);
    }

    public T params(ClassLoader typeClassLoader, String[] typePaths, Object[] params) {
        if (typePaths != null) {
            int length = typePaths.length;
            for (int index = 0; index < length; index++) {
                String typePath = typePaths[index];
                try {
                    parameterList.add(Reflect.classForNameWithException(typeClassLoader, typePath));
                } catch (Throwable e) {
                    e = Reflect.getRealThrowable(e);
                    this.throwable = new RefException("Param Class[" + typePath + "] not found in ClassLoader[" + typeClassLoader + "]", e);
                    break;
                }
            }
        }
        if (params != null) {
            parameterList.addAll(Arrays.asList(params));
        }
        return (T) this;
    }

    public T param(Class type, Object param) {
        parameterTypeList.add(type);
        parameterList.add(param);
        return (T) this;
    }

    public T param(String typePath, Object param) {
        return param(RefMethod.class.getClassLoader(), typePath, param);
    }

    public T param(ClassLoader typeClassLoader, String typePath, Object param) {
        Class type = null;
        try {
            type = Reflect.classForNameWithException(typeClassLoader, typePath);
        } catch (Throwable e) {
            e = Reflect.getRealThrowable(e);
            this.throwable = new RefException("Class[" + typePath + "] not found in ClassLoader[" + typeClassLoader + "]", e);
        }
        if (type != null) {
            parameterTypeList.add(type);
            parameterList.add(param);
        }
        return (T) this;
    }

    Class[] getParameterTypes() {
        return parameterTypeList.toArray(new Class[parameterTypeList.size()]);
    }

    Object[] getParameters() {
        return parameterList.toArray();
    }
}
