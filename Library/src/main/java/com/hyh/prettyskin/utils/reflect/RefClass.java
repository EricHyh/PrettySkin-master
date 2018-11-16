package com.hyh.prettyskin.utils.reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/15
 */

public class RefClass {

    private Class cls;

    private Throwable throwable;

    public RefClass(Class cls) {
        this.cls = cls;
    }

    public RefClass(String className) {
        try {
            this.cls = Reflect.classForNameWithException(className);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
    }

    public RefClass(ClassLoader classLoader, String className) {
        try {
            this.cls = Reflect.classForNameWithException(classLoader, className);
        } catch (Throwable e) {
            this.throwable = Reflect.getRealThrowable(e);
        }
    }

    public RefField filed(String filedName) {
        return new RefField(cls, filedName, throwable);
    }

    public RefMethod method(String methodName) {
        return new RefMethod(cls, methodName, throwable);
    }

    public RefConstructor constructor() {
        return new RefConstructor(cls, throwable);
    }
}
