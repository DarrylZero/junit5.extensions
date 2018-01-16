package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Vladimir Bogodukhov
 *         #EXAMPLE
 **/
@RunWith(Parameterized.class)
public class JUnit4ParametrizedExample {

    final boolean b;
    final long l;
    final int i;

    @Parameterized.Parameters(name = "{0} ---> : Test")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {false, 1L, 21},
                {true, 21L, 11},
                {false, 10L, 21},
        });
    }

    public JUnit4ParametrizedExample(boolean b, long l, int i) {
        this.b = b;
        this.l = l;
        this.i = i;
    }


    @Test
    public void test1() {
        throw new IllegalStateException(); /* always fails */
    }

    @Test
    public void test2() {
        /* always succeed */
    }
}
