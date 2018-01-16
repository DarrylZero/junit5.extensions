package com.steammachine.org.junit5.extensions.ignore;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public class IgnoredConditionExample {

    public static class IgnEx extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx.class)
    void test() {
    }

    protected static class IgnEx2 extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx2.class)
    void test2() {
    }

    static class IgnEx3 extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx3.class)
    void test3() {
    }

    private static class IgnEx4 extends DefaultIgnoreCondition {
        private IgnEx4() {
        }
    }

    @Test
    @Ignore(condition = IgnEx4.class)
    void test4() {
    }

    private static class IgnEx5 extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx5.class)
    void test5() {
    }

    public class IgnEx6 extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx6.class)
    void test6() {
    }

    class IgnEx7 extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx7.class)
    void test7() {
    }

    private class IgnEx8 extends DefaultIgnoreCondition {
    }

    @Test
    @Ignore(condition = IgnEx8.class)
    void test8() {
    }

    @Test
    @Ignore(condition = IgnoreCondition2345671.class)
    void test9() {
    }

}
