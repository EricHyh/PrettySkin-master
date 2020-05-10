package com.hyh.prettyskin.utils.reflect;



public class RefResult<E> {

    private E result;

    private boolean isSuccess;

    private Throwable throwable;

    void setResult(E result) {
        this.result = result;
    }

    void setSuccess(boolean success) {
        isSuccess = success;
    }

    void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public E getResult() {
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
