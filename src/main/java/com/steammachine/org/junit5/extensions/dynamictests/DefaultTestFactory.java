package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.DynamicTest;

/**
 * Created 21/12/16 17:23
 *
 * @author Vladimir Bogodukhov
 **/
public class DefaultTestFactory {

    public static final TestInstanceFactory DEFAULT_FACTORY = DynamicTest::dynamicTest;

}
