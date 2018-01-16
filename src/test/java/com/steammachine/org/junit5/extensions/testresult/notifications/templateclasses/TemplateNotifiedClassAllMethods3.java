package com.steammachine.org.junit5.extensions.testresult.notifications.templateclasses;

import java.lang.reflect.Method;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public interface TemplateNotifiedClassAllMethods3 {

    default void testSuccess(String testId, Object testInstance, Method testMethod) {
    }

    default void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
    }

    default void testSkipped(String testId, Object testInstance, Method testMethod) {
    }

    default void testAborted(String testId, Object testInstance, Method testMethod) {
    }

}
