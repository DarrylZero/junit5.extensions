package com.steammachine.org.junit5.extensions.common;


import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author Vladimir Bogodukhov
 *         {@link ClassSourceWrapperCheck}
 */
public class ClassSourceWrapperCheck {

    @Test
    public void testClassName() {
        Assert.assertEquals("ClassSourceWrapperCheck",
                ClassSourceWrapperCheck.class.getName());
    }

    @Test
    public void testClassSource() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = loadOrNull("org.junit.platform.engine.support.descriptor.ClassSource");
        if (clazz != null) {
            Object classSource = clazz.getConstructor(String.class).newInstance(ClassSourceWrapperCheck.class.getName());
            ClassSourceWrapper wrapper = new ClassSourceWrapper(classSource);
            Assert.assertEquals("ClassSourceWrapperCheck", wrapper.getClassName());
            return;
        }

        clazz = loadOrNull("org.junit.platform.engine.support.descriptor.JavaClassSource");
        if (clazz != null) {
            Object classSource = clazz.getConstructor(Class.class).newInstance(ClassSourceWrapperCheck.class);
            ClassSourceWrapper wrapper = new ClassSourceWrapper(classSource);
            Assert.assertEquals("ClassSourceWrapperCheck", wrapper.getClassName());
            return;
        }

        throw new IllegalStateException();
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new ClassSourceWrapperCheck().testClassSource();
    }

    /* ------------------------------------------------- privates  ------------------------------------------------- */

    private static Class loadOrNull(String className) {
        Objects.requireNonNull(className);
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}