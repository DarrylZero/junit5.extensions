package com.steammachine.org.junit5.extensions.expectedexceptions;


import com.steammachine.org.junit5.extensions.expectedexceptions.Expected;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public class ExpectedJupiterRun {

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
            throw new AssertionError() {};
        } else {
            throw new NullPointerException(){};
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


}
