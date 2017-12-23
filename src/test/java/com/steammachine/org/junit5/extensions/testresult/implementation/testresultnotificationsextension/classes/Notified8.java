package com.steammachine.org.junit5.extensions.testresult.implementation.testresultnotificationsextension.classes;

/**
 * Created 02/12/16 15:08
 *
 * @author Vladimir Bogodukhov 
 **/
public class Notified8 {
    static final Object notifier1 = new Object() {
        private void testClassSuccess(Class clazz) {
        }

        private void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };
    static final Object notifier2 = new Object() {
        void testClassSuccess(Class clazz) {
        }

        void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };
    static final Object notifier3 = new Object() {
        protected void testClassSuccess(Class clazz) {
        }

        protected void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };
    static final Object notifier4 = new Object() {
        public void testClassSuccess(Class clazz) {
        }

        public void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };
    static Object notifier5 = null;



}
