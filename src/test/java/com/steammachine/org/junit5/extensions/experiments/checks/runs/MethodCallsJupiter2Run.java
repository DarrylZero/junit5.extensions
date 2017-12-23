package com.steammachine.org.junit5.extensions.experiments.checks.runs;


import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.ignore.IgnoreJ5;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;

import java.lang.reflect.Method;

/**
 * Created 27/09/16 08:05
 *
 * @author Vladimir Bogodukhov
 **/
@TestResultNotification
public class MethodCallsJupiter2Run {


    public Object object = new Object() {
        void testSuccess(String identifier, Object testInstance, Method testMethod) {
            System.out.println("testSuccess " + testInstance.getClass().getName() + "." + testMethod.getName() + "  " + identifier);
        }


        void testFailure(String identifier, Object testInstance, Method testMethod, Throwable throwable) {
            System.out.println("testFailure " + testInstance.getClass().getName() + "." + testMethod.getName() + "  " + identifier);
        }

        void testSkipped(String identifier, Object testInstance, Method testMethod) {
            System.out.println("testSkipped " + testInstance.getClass().getName() + "." + testMethod.getName() + "  " + identifier);
        }
    };

    @Test
    void failure() {
        throw new NullPointerException();
    }

    @Test
    void success2222() {
    }

    @Test
    void success2() {
    }

    @Test
    void success3() {

    }

    @Test
    void success4() {
    }

    @Test
    @IgnoreJ5("IGNORED !!!")
    void skipped() {
        throw new NullPointerException();
    }

}
