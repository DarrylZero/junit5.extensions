package com.steammachine.org.junit5.extensions.testresult.implementation;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created 19/12/16 13:12
 *
 * @author Vladimir Bogodukhov
 *         <p>
 *         UNDER_CONSTRUCTION!!!
 **/
public interface MethodData {
    Class clazz();

    Method method();

    String methodName();

    Object addition();

    static MethodData data(Class clazz, String methodName, Method method, Object addition) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(methodName);
        Objects.requireNonNull(method);

        return new MethodData() {
            @Override
            public Class clazz() {
                return clazz;
            }

            @Override
            public Method method() {
                return method;
            }

            @Override
            public String methodName() {
                return methodName;
            }

            @Override
            public Object addition() {
                return addition;
            }
        };
    }

    static MethodData data(Class clazz, String methodName, Method method) {
        return data(clazz, methodName, method, null);
    }

    static MethodData updateData(MethodData data, Object addition) {
        return data(data.clazz(), data.methodName(), data.method(), addition);
    }

}

