package com.steammachine.org.junit5.extensions.experiments.checks.runs;


import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.expectedexceptions.Expected;
import com.steammachine.org.junit5.extensions.ignore.IgnoreJ5;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created 27/09/16 08:05
 *
 * @author Vladimir Bogodukhov
 **/
@TestResultNotification
public class MethodCallsJupiterRun {


    public Object object = null;

    public Object object2 = new Object() {
        public void testSuccess(String identifier, Object testInstance, Method testMethod) {
            System.out.println("" + testInstance + " " + testMethod + "  " + identifier);
        }
    };

    public Object object3 = new Object() {
        public void testFinished(String identifier, Object testInstance, Method testMethod) {
            System.out.println("" + testInstance + " " + testMethod + "  " + identifier);
        }
    };


    @Test
    @Expected(expected = NullPointerException.class)
    void test10() { // OK
        throw new NullPointerException();
    }

    @Test
    @Expected(expected = RuntimeException.class)
    void test15() {// Неверно НЕ должен падать
        throw new NullPointerException();
    }

    @Test
    @Expected(expected = NullPointerException.class)
    void test20() { // Неверно должен падать
    }

    @Test
    void test30() {// OK
        throw new NullPointerException();
    }

    @Test
    void test40() {
        throw new AssertionError();
    }

    @Test
    @Expected(expected = {NullPointerException.class, AssertionError.class})
    void test50() {
        if (new Random().nextInt(1000) < 500) {
            throw new AssertionError();
        } else {
            throw new NullPointerException();
        }
    }

    @Test
    @Expected(expected = {NullPointerException.class, AssertionError.class}, matchExactType = true)
    void test52() {
        if (new Random().nextInt(1000) < 500) {
            throw new AssertionError() {
            };
        } else {
            throw new NullPointerException() {
            };
        }
    }

    @Test
    @Expected(expected = {RuntimeException.class})
    void test60() {
        throw new IllegalStateException();
    }

    @Test
    @Expected(expected = {RuntimeException.class}, matchExactType = true)
    void test70() {
        throw new IllegalStateException();
    }

    @Test
    @Expected(expected = {IllegalStateException.class}, matchExactType = true)
    void test80() {
        throw new IllegalStateException();
    }


    @Test
    @IgnoreJ5
    void test90() {
        throw new IllegalStateException();
    }


}
