package com.steammachine.org.junit5.extensions.testresult.notifications.templateclasses;

import java.lang.reflect.Method;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public interface TemplateNotifiedClassAllMethods2 {

    void testSuccess(String testId, Object testInstance, Method testMethod);

    void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable);

    void testSkipped(String testId, Object testInstance, Method testMethod);

    void testAborted(String testId, Object testInstance, Method testMethod);

    void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e);

    void testSuccessful(int[] caseIds, String[] comments, String[] filters);


}
