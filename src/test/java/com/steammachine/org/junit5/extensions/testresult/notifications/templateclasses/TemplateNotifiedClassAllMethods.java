package com.steammachine.org.junit5.extensions.testresult.notifications.templateclasses;

import java.lang.reflect.Method;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public class TemplateNotifiedClassAllMethods {

    public void testClassSuccess(Class testClass) {
    }

    public void testClassFailure(Class testClass, String testClassName, Throwable exception) {
    }

    public void testSuccess(String testId, Object testInstance, Method testMethod) {
    }

    public void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
    }

    public void testSkipped(String testId, Object testInstance, Method testMethod) {
    }

    public void testAborted(String testId, Object testInstance, Method testMethod) {
    }


}
