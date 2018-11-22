package com.hyh.prettyskin.utils.reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/20
 */

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
