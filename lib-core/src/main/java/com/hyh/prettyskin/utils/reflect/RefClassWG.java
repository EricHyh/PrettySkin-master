package com.hyh.prettyskin.utils.reflect;


public class RefClassWG<T> extends RefClass {

    private Class<T> cls;

    RefClassWG(Class<T> cls) {
        super(cls);
        this.cls = cls;
    }

    @Override
    public RefConstructor<T> constructor() {
        return new RefConstructor<>(cls, null);
    }
}