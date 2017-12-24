package com.steammachine.org.junit5.extensions.testresult.experiments;

import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created 02/12/16 13:20
 *
 * @author Vladimir Bogodukhov
 **/
public class ClassRuleExperimentRun {

    /*
        static {
            CommonUtils.suppressWOResult(() -> {throw new IllegalStateException();});
        }
    */

    @ClassRule
    public static TestRule classRule = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            super.succeeded(description);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            super.skipped(e, description);
        }
    };


    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
        throw new IllegalStateException();
    }

    @Test
    public void test() {

    }


}
