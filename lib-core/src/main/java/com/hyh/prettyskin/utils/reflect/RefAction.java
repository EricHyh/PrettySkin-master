package com.hyh.prettyskin.utils.reflect;

/**
 * @author Administrator
 * @description
 * @data 2019/7/1
 */

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