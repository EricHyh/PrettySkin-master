package com.hyh.prettyskin.utils.reflect;


public abstract class Lazy<T> {

    private T t;

    public T get() {
        if (t != null) return t;
        synchronized (this) {
            if (t == null) {
                t = create();
            }
        }
        return t;
    }

    protected abstract T create();

}