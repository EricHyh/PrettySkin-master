package com.hyh.prettyskin.utils.reflect;

public class RefClass {

    private Class cls;

    private Throwable throwable;

    RefClass(Class cls) {
        this.cls = cls;
        if (this.cls == null) {
            this.throwable = new NullPointerException("Class not be null");
        }
    }

    RefClass(String className) {
        try {
            this.cls = Reflect.classForNameWithException(className);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
    }

    RefClass(ClassLoader classLoader, String className) {
        try {
            this.cls = Reflect.classForNameWithException(classLoader, className);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
    }

    public <T> RefField<T> filed(String filedName) {
        return new RefField<>(cls, filedName, null, throwable);
    }

    public <T> RefField<T> filed(String filedName, Class<T> fieldType) {
        return new RefField<>(cls, filedName, fieldType, throwable);
    }

    public <T> RefMethod<T> method(String methodName) {
        return new RefMethod<>(cls, methodName, null, throwable);
    }

    public <T> RefMethod<T> method(String methodName, Class<T> returnType) {
        return new RefMethod<>(cls, methodName, returnType, throwable);
    }

    public <T> RefConstructor<T> constructor() {
        return new RefConstructor<>(cls, throwable);
    }
}