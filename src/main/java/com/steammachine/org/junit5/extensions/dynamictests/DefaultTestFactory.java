package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.DynamicTest;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public class DefaultTestFactory {

    public static final TestInstanceFactory DEFAULT_FACTORY = DynamicTest::dynamicTest;

}
