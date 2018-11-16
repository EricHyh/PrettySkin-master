package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Constructor;

/**
 * @author Administrator
 * @description
 * @data 2018/11/16
 */

public class RefConstructor extends RefExecutable<RefConstructor, Constructor> {


    public RefConstructor(Class cls, Throwable throwable) {
        super(cls, throwable);
    }

    @Override
    protected Constructor getExecutable(Class cls, Class[] parameterTypes) {
        return Reflect.getDeclaredConstructor(cls, parameterTypes);
    }

    @Override
    protected Object execute(Constructor constructor, Object receiver, Object[] parameters) throws Throwable {
        return constructor.newInstance(parameters);
    }

    @Override
    protected String getExecuteNotFoundMessage(Class cls) {
        StringBuilder parameterTypesStr = new StringBuilder();
        Class[] parameterTypes = getParameterTypes();
        if (parameterTypes != null && parameterTypes.length >= 0) {
            int length = parameterTypes.length;
            for (int index = 0; index < length; index++) {
                if (index < length - 1) {
                    parameterTypesStr.append(parameterTypes[index].getName()).append(", ");
                } else {
                    parameterTypesStr.append(parameterTypes[index].getName());
                }
            }
        }
        return "constructor: init(" + parameterTypesStr + ") not found in Class[" + cls + "]";
    }


    public Object newInstance() {
        return execute(null);
    }

    public Object newInstanceWithException() throws Throwable {
        return executeWithException(null);
    }
}
