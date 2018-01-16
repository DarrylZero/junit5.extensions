package com.steammachine.org.junit5.extensions.ignore;


import org.junit.jupiter.api.Test;

/**
 *
 * @author Vladimir Bogodukhov
 */
public class IgnoreRun {

    @Test
    @Ignore
    public void isStateless() {
    }

    @Test
    @Ignore
    public void isStateless1() {
    }

    @Test
    @Ignore
    public void isStateless2() {
    }

    @Test
    @Ignore(condition = IgnoreCondition2345671.class)
    public void isStateless3() {
    }


}