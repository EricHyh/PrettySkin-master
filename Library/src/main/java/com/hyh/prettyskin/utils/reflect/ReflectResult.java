package com.hyh.prettyskin.utils.reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/15
 */

public class ReflectResult {

    private Object result;

    private boolean isSuccess;

    private Throwable throwable;

    public void setResult(Object result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object getResult() {
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
