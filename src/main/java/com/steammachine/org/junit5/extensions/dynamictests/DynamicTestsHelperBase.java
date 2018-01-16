package com.steammachine.org.junit5.extensions.dynamictests;

import com.steammachine.org.junit5.extensions.dynamictests.impls.ver4.DynamicTestsHelperVer4;

/**
 *
 * @author Vladimir Bogodukhov
 * {@link com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsHelperBase}
 * com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsHelperBase
 **/
public class DynamicTestsHelperBase extends DynamicTestsHelperVer4 {

    public DynamicTestsHelperBase() {

    }

    public DynamicTestsHelperBase(TestInstanceFactory testInstanceFactory) {
        super(testInstanceFactory);
    }
}
