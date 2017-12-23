package com.steammachine.org.junit5.extensions.dynamictests;


import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

/**
 * Created 20/10/16 16:51
 *
 * @author Vladimir Bogodukhov
 * #EXAMPLE
 **/
public class JUnit5ParametrizedExample {

    @TestFactory
    public Stream<DynamicTest> data() {
        return DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(this).
                setParamTypes(Boolean.TYPE, Long.TYPE, Integer.TYPE).
                addParams(false, 1L, 21).
                addParams(true, 21L, 11).
                addParams(false, 10L, 21).
                helper().
                stream();
    }

    void test1(boolean b, long l, int i) {
        throw new IllegalStateException(); /* always fails */
    }

    void test2(boolean b, long l, int i) {
        /* always succeeds */
    }
}
