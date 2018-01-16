package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Vladimir Bogodukhov
 **/
class SomeDynamicTests {


    @TestFactory
    Stream<DynamicTest> test() {
        return Stream.of(
                DynamicTest.dynamicTest("testName", () -> {
                }),
                DynamicTest.dynamicTest("testName", () -> {
                }),
                DynamicTest.dynamicTest("testName", () -> {
                }),
                DynamicTest.dynamicTest("testName", () -> {
                })
        );
    }

    @Test
    void testUniquiness() {
        test().collect(Collectors.toMap(DynamicTest::getDisplayName, d -> d));

    }



}
