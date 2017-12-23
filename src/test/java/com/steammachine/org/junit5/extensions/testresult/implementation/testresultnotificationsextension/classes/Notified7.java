package com.steammachine.org.junit5.extensions.testresult.implementation.testresultnotificationsextension.classes;

/**
 * Created 02/12/16 15:08
 *
 * @author Vladimir Bogodukhov 
 **/
public class Notified7 {
    private static final Object notifier1 = new Notifier7();
    protected static final Object notifier2 = new Notifier7();
    static final Notifier7 Object = new Notifier7();
    public static final Object notifier4 = new Notifier7();
    static final Object notifier5 = new Object() {
        void testClassSuccess(Class clazz) {
        }

        void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };
    static final Object notifier6 = new Object() {
        void testClassFailure(Class testClass, String testClassName, Throwable exception) {
        }
    };
    static final Object notifier7 = new Object() {
        void testClassSuccess(Class clazz) {
        }
    };
    static final Object notifier8 = new Object() {
        private void testClassSuccess(Class clazz) {
        }
    };
    static Notifier4 notifier9 = null;

}
