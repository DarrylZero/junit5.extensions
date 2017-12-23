package com.steammachine.org.junit5.extensions.testresult.experiments;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;

import java.lang.reflect.Method;

import static ru.socialquantum.common.utils.commonutils.CommonUtils.suppressWOResult;

/**
 * Created 02/12/16 13:20
 *
 * @author Vladimir Bogodukhov
 **/
@TestResultNotification
class ClassRuleJUnit5ExperimentRun {

    static {
        suppressWOResult(() -> {
          throw new IllegalStateException();
        });
    }


    public static class Watcher {
        void testSuccess(String testIdent, Object testInstance, Method testMethod) {
        }

        void testFailure(String testIdent, Object testInstance, Method method, Throwable t) {
        }
    }

    public static Object classRule = new Object() {
        protected void testClassSuccess(Class testClass) {
        }

        protected void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };

    public Object objectRule = new Watcher() {
        @Override
        void testSuccess(String testIdent, Object testInstance, Method testMethod) {
            super.testSuccess(testIdent, testInstance, testMethod);
        }

        @Override
        void testFailure(String testIdent, Object testInstance, Method method, Throwable t) {
            super.testFailure(testIdent, testInstance, method, t);
        }
    };

    @BeforeAll
    public static void beforeClass() {
    }

    @AfterAll
    public static void afterClass() {
      throw new IllegalStateException();
    }

    @Test
    public void test() {
    }

    @Test
    public void test2() {
    }

    @Test
    public void test3() {
    }

//    @Test
//    public void testFailure() {
//        throw new IllegalStateException();
//    }


}
