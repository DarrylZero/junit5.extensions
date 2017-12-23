package com.steammachine.org.junit5.extensions.ignore;

import org.junit.jupiter.api.Test;

/**
 * Created 25/10/16 12:58
 *
 * @author Vladimir Bogodukhov
 **/
public class IgnoredConditionExample {

    public static class IgnEx extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx.class)
    void test() {
    }

    protected static class IgnEx2 extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx2.class)
    void test2() {
    }

    static class IgnEx3 extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx3.class)
    void test3() {
    }

    private static class IgnEx4 extends DefaultIgnoreCondition {
        private IgnEx4() {
        }
    }

    @Test
    @IgnoreJ5(condition = IgnEx4.class)
    void test4() {
    }

    private static class IgnEx5 extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx5.class)
    void test5() {
    }

    public class IgnEx6 extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx6.class)
    void test6() {
    }

    class IgnEx7 extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx7.class)
    void test7() {
    }

    private class IgnEx8 extends DefaultIgnoreCondition {
    }

    @Test
    @IgnoreJ5(condition = IgnEx8.class)
    void test8() {
    }

    @Test
    @IgnoreJ5(condition = IgnoreCondition2345671.class)
    void test9() {
    }

}
