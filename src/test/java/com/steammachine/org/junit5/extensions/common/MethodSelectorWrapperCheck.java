package com.steammachine.org.junit5.extensions.common;

import com.steammachine.org.junit5.extensions.common.MethodSourceWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladimir Bogodukhov
 */
public class MethodSelectorWrapperCheck {


    @Test
    public void isMethodSource10() {
        Assert.assertTrue(MethodSourceWrapper.isMethodSource(findSourceClass()));
    }

    @Test
    public void isMethodSource20() {
        Assert.assertFalse(MethodSourceWrapper.isMethodSource(Object.class));
    }

    private static Class findSourceClass() {
        Class result;
        try {
            result = Class.forName("org.junit.platform.engine.support.descriptor.JavaMethodSource");
        } catch (ClassNotFoundException e) {
            result = null;
        }

        if (result == null) {
            try {
                result = Class.forName("org.junit.platform.engine.support.descriptor.MethodSource");
            } catch (ClassNotFoundException e) {
                result = null;
            }
        }

        if (result == null) {
            throw new IllegalStateException();
        }


        return result;
    }

}