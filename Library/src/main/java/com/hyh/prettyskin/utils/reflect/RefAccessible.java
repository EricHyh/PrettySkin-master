package com.hyh.prettyskin.utils.reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/21
 */

public abstract class RefAccessible<E, T extends RefAccessible<E, T>> {

    protected Throwable throwable;

    private E defaultValue;

    private ReflectResult<E> reflectResult;

    private boolean printException;

    RefAccessible(Throwable throwable) {
        this.throwable = throwable;
    }

    public T defaultValue(E defaultValue) {
        this.defaultValue = defaultValue;
        return (T) this;
    }

    public T printException() {
        this.printException = true;
        return (T) this;
    }

    public T saveResult(ReflectResult<E> result) {
        this.reflectResult = result;
        return (T) this;
    }


    void saveFailure(E result) {
        if (reflectResult != null) {
            reflectResult.setResult(result);
            reflectResult.setThrowable(this.throwable);
        }
    }

    void saveSuccess(E result) {
        if (reflectResult != null) {
            reflectResult.setResult(result);
            reflectResult.setSuccess(true);
        }
    }

    void tryToPrintException() {
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
                this.throwable = new ReflectException(getResultTypeErrorMessage(resultType));
                return getDefaultValue(null);
            }
        } else {
            return (E) result;
        }
    }

    @SuppressWarnings("unchecked")
    E getDefaultValue(Class realType) {
        Class<E> ensureType = getEnsureType();
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
