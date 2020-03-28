package com.hyh.prettyskin.utils.reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/21
 */

public abstract class RefAccessible<E, T extends RefAccessible<E, T>> {

    protected Throwable throwable;

    private E defaultValue;

    private Lazy<E> lazyDefaultValue;

    private RefResult<E> refResult;

    private RefAction<E> refAction;

    private boolean printException;

    RefAccessible(Throwable throwable) {
        this.throwable = throwable;
    }

    public T defaultValue(E defaultValue) {
        this.defaultValue = defaultValue;
        return (T) this;
    }

    public T defaultValue(Lazy<E> defaultValue) {
        this.lazyDefaultValue = defaultValue;
        return (T) this;
    }

    public T printException() {
        this.printException = true;
        return (T) this;
    }

    public T saveResult(RefResult<E> result) {
        this.refResult = result;
        return (T) this;
    }

    public T resultAction(RefAction<E> action) {
        this.refAction = action;
        return (T) this;
    }

    void onSuccess(E result) {
        if (refResult != null) {
            refResult.setResult(result);
            refResult.setSuccess(true);
        }
        if (refAction != null) {
            refAction.onSuccess(result);
        }
    }

    void onFailure(E result, Throwable throwable) {
        if (refResult != null) {
            refResult.setResult(result);
            refResult.setThrowable(throwable);
        }
        if (refAction != null) {
            refAction.onFailure(result, throwable);
        }
        if (printException && throwable != null) {
            throwable.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    E ensureResult(Object result, Class resultType) {
        Class<E> ensureType = getEnsureType();
        if (ensureType != null) {
            if (Reflect.isAssignableFrom(resultType, ensureType)) {
                return (E) result;
            } else {
                //Create Exception
                this.throwable = new RefException(getResultTypeErrorMessage(resultType));
                return getDefaultValue(null);
            }
        } else {
            return (E) result;
        }
    }

    @SuppressWarnings("unchecked")
    E getDefaultValue(Class realType) {
        Class<E> ensureType = getEnsureType();
        E defaultValue = this.defaultValue;
        if (defaultValue == null && lazyDefaultValue != null) {
            defaultValue = lazyDefaultValue.get();
        }
        if (ensureType != null) {
            if (defaultValue != null && Reflect.isAssignableFrom(defaultValue.getClass(), ensureType)) {
                return defaultValue;
            } else {
                return Reflect.getDefaultValue(ensureType);
            }
        } else {
            if (defaultValue != null) {
                return defaultValue;
            }
            if (realType != null) {
                return (E) Reflect.getDefaultValue(realType);
            }
        }
        return null;
    }

    abstract Class<E> getEnsureType();

    abstract String getResultTypeErrorMessage(Class resultType);

}