package com.hyh.prettyskin.utils.reflect;

import java.lang.reflect.Method;

/**
 * @author Administrator
 * @description
 * @data 2018/11/15
 */

public class RefMethod extends RefExecutable<RefMethod, Method> {

    private String methodName;

    public RefMethod(Class cls, String methodName, Throwable throwable) {
        super(cls, throwable);
        this.methodName = methodName;
    }

    @Override
    protected Method getExecutable(Class cls, Class[] parameterTypes) {
        return Reflect.getDeclaredMethod(cls, methodName, parameterTypes);
    }

    @Override
    protected Object execute(Method method, Object receiver, Object[] parameters) throws Throwable {
        return method.invoke(receiver, parameters);
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
        return "method:" + methodName + "(" + parameterTypesStr + ") not found in Class[" + cls + "]";
    }


    public Object invoke(Object receiver) {
        return execute(receiver);
    }

    public Object invokeWithException(Object receiver) throws Throwable {
        return executeWithException(receiver);
    }
}
