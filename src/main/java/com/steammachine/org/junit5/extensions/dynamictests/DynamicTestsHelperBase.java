package com.steammachine.org.junit5.extensions.dynamictests;

import com.steammachine.org.junit5.extensions.dynamictests.impls.ver4.DynamicTestsHelperVer4;

/**
 * Created 21/10/16 15:50
 *
 * @author Vladimir Bogodukhov
 **/
public class DynamicTestsHelperBase extends DynamicTestsHelperVer4 {

    public DynamicTestsHelperBase() {

    }

    public DynamicTestsHelperBase(TestInstanceFactory testInstanceFactory) {
        super(testInstanceFactory);
    }
}
