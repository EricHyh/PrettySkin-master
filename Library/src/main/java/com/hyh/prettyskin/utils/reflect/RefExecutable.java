package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/16
 */

public abstract class RefExecutable<T extends RefExecutable, E extends AccessibleObject> {

    private Class cls;

    private Object defaultValue;

    private boolean printException;

    private ReflectResult reflectResult;

    private List<Class> parameterTypeList = new ArrayList<>();

    private List<Object> parameterList = new ArrayList<>();

    private Throwable throwable;

    public RefExecutable(Class cls, Throwable throwable) {
        this.cls = cls;
        this.throwable = throwable;
    }

    public T params(Class[] types, Object[] params) {
        if (types != null && params != null && types.length == params.length) {
            for (Class type : types) {
                if (type == null) {
                    this.throwable = new ReflectException("参数类型为空");
                    break;
                }
                parameterTypeList.add(type);
            }
            if (this.throwable == null) {
                parameterList.addAll(Arrays.asList(params));
            }
        } else {
            this.throwable = new ReflectException("参数类型列表与参数列表长度不一致");
        }
        return (T) this;
    }

    public T params(String[] typePaths, Object[] params) {
        return params(RefMethod.class.getClassLoader(), typePaths, params);
    }

    public T params(ClassLoader typeClassLoader, String[] typePaths, Object[] params) {
        if (typePaths != null && params != null && typePaths.length == params.length) {
            for (String typePath : typePaths) {
                try {
                    parameterList.add(Reflect.classForNameWithException(typeClassLoader, typePath));
                } catch (Throwable e) {
                    Throwable cause = e.getCause();
                    if (cause != null) {
                        this.throwable = cause;
                    } else {
                        this.throwable = e;
                    }
                    break;
                }
            }
            if (this.throwable == null) {
                parameterList.addAll(Arrays.asList(params));
            }
        } else {
            this.throwable = new ReflectException("参数类型列表与参数列表长度不一致");
        }
        return (T) this;
    }

    public RefExecutable param(Class type, Object param) {
        if (type != null) {
            parameterTypeList.add(type);
            parameterList.add(param);
        } else {
            this.throwable = new ReflectException("参数类型为空");
        }
        return this;
    }

    public T param(String typePath, Object param) {
        return param(RefMethod.class.getClassLoader(), typePath, param);
    }

    public T param(ClassLoader typeClassLoader, String typePath, Object param) {
        Class type = null;
        Throwable throwable = null;
        try {
            type = Reflect.classForNameWithException(typeClassLoader, typePath);
        } catch (Throwable e) {
            throwable = Reflect.getRealThrowable(e);
        }
        if (type != null) {
            parameterTypeList.add(type);
            parameterList.add(param);
        } else {
            this.throwable = new ReflectException("参数类型为空");
            if (throwable != null) {
                this.throwable.setStackTrace(throwable.getStackTrace());
            }
        }
        return (T) this;
    }

    public Class[] getParameterTypes() {
        return (Class[]) parameterTypeList.toArray();
    }

    public Object[] getParameters() {
        return parameterList.toArray();
    }


    public T defaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return (T) this;
    }

    public T printException() {
        this.printException = true;
        return (T) this;
    }

    public T saveResult(ReflectResult result) {
        this.reflectResult = result;
        return (T) this;
    }


    protected Object execute(Object receiver) {
        if (this.cls == null) {
            return executableNotFound();
        }
        E executable = getExecutable(cls, getParameterTypes());
        if (executable == null) {
            return executableNotFound();
        }

        Object result = null;
        Throwable throwable = null;
        boolean invokeSuccess = false;

        try {
            result = execute(executable, receiver, getParameters());
            invokeSuccess = true;
        } catch (Throwable e) {
            throwable = Reflect.getRealThrowable(e);
        }
        if (invokeSuccess) {
            return invokeSuccess(result);
        } else {
            return invokeFailure(executable, throwable);
        }
    }


    protected Object executeWithException(Object receiver) throws Throwable {
        if (this.cls == null) {
            throw executableNotFoundException();
        }
        E executable = getExecutable(cls, getParameterTypes());
        if (executable == null) {
            throw executableNotFoundException();
        }
        Object result = null;
        Throwable throwable = null;
        boolean invokeSuccess = false;

        try {
            result = execute(executable, receiver, getParameters());
            invokeSuccess = true;
        } catch (Throwable e) {
            throwable = Reflect.getRealThrowable(e);
        }
        if (invokeSuccess) {
            return invokeSuccess(result);
        } else {
            throw invokeFailureException(executable, throwable);
        }
    }

    private Object executableNotFound() {
        ReflectException reflectException = new ReflectException(getExecuteNotFoundMessage(cls));
        if (throwable != null) {
            reflectException.setStackTrace(throwable.getStackTrace());
        }
        if (printException) {
            reflectException.printStackTrace();
        }
        Object defaultValue = getDefaultValue(null);
        if (reflectResult != null) {
            reflectResult.setResult(defaultValue);
            reflectResult.setThrowable(reflectException);
        }
        return defaultValue;
    }


    private Throwable executableNotFoundException() {
        ReflectException reflectException = new ReflectException(getExecuteNotFoundMessage(cls));
        if (throwable != null) {
            reflectException.setStackTrace(throwable.getStackTrace());
        }
        if (printException) {
            reflectException.printStackTrace();
        }
        Object defaultValue = getDefaultValue(null);
        if (reflectResult != null) {
            reflectResult.setResult(defaultValue);
            reflectResult.setThrowable(reflectException);
        }
        return reflectException;
    }

    private Object invokeSuccess(Object result) {
        if (reflectResult != null) {
            reflectResult.setResult(result);
            reflectResult.setSuccess(true);
        }
        return result;
    }

    private Object invokeFailure(E executable, Throwable throwable) {
        if (printException) {
            throwable.printStackTrace();
        }
        Object result = getDefaultValue(executable);
        if (reflectResult != null) {
            reflectResult.setThrowable(throwable);
            reflectResult.setResult(result);
        }
        return result;
    }

    private Throwable invokeFailureException(AccessibleObject executable, Throwable throwable) {
        if (printException) {
            throwable.printStackTrace();
        }
        Object result = getDefaultValue(executable);
        if (reflectResult != null) {
            reflectResult.setThrowable(throwable);
            reflectResult.setResult(result);
        }
        return throwable;
    }


    private Object getDefaultValue(AccessibleObject executable) {
        if (defaultValue == null && executable != null) {
            if (executable instanceof Method) {
                Method method = (Method) executable;
                defaultValue = Reflect.getDefaultValue(method.getReturnType());
            }
        }
        return defaultValue;
    }

    protected abstract E getExecutable(Class cls, Class[] parameterTypes);

    protected abstract Object execute(E executable, Object receiver, Object[] parameters) throws Throwable;

    protected abstract String getExecuteNotFoundMessage(Class cls);

}
