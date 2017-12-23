package com.steammachine.org.junit5.extensions.expectedexceptions;


import org.junit.Test;

/**
 * Created 27/09/16 08:05
 *
 * @author Vladimir Bogodukhov 
 **/
public class ExpectedVintageRun {

    @Test(expected = NullPointerException.class)
    public void test10() {
        throw new NullPointerException();
    }

    @Test(expected = RuntimeException.class)
    public void test15() {
        throw new NullPointerException();
    }

    @Test(expected = NullPointerException.class)
    public void test20() {
        // throw new NullPointerException();
    }

    @Test
    public void test30() {
        throw new NullPointerException();
    }

    @Test
    public void test40() {
        throw new AssertionError();
    }

}
