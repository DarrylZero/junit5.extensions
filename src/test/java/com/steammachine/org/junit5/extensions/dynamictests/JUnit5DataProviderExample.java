package com.steammachine.org.junit5.extensions.dynamictests;

import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

/**
 *
 * @author Vladimir Bogodukhov
 *         <p>
 *         #EXAMPLE
 **/
public class JUnit5DataProviderExample {


    @Test
    void test() {

    }


    @TestFactory
    Stream<DynamicTest> dataprovidermethod() {
        return DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(this).
                setParamTypes(String.class, Integer.TYPE, Integer.TYPE).
                addParams("One", 0, 0).
                addParams("One", 0, 0).
                helper().
                stream();
    }

    void testMethod(String s, int i, int i2) {
        throw new IllegalStateException(); /* Тут проверяем что тест завершился с ошибкой. */
    }

}
