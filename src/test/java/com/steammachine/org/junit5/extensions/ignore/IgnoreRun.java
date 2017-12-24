package com.steammachine.org.junit5.extensions.ignore;


import org.junit.jupiter.api.Test;

/**
 *
 * @author Vladimir Bogodukhov
 */
public class IgnoreRun {

    @Test
    @IgnoreJ5
    public void isStateless() {
    }

    @Test
    @IgnoreJ5
    public void isStateless1() {
    }

    @Test
    @IgnoreJ5
    public void isStateless2() {
    }

    @Test
    @IgnoreJ5(condition = IgnoreCondition2345671.class)
    public void isStateless3() {
    }


}