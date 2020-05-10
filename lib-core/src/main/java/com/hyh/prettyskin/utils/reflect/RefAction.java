package com.hyh.prettyskin.utils.reflect;



public abstract class RefAction<T> {

    public void onSuccess(T t) {
        onSuccess();
    }

    public void onSuccess() {
    }

    public void onFailure(T defValue, Throwable throwable) {
        onFailure(throwable);
    }

    public void onFailure(Throwable throwable) {
    }
}