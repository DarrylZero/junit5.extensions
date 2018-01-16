package com.steammachine.org.junit5.extensions.dynamictests.methodcomparator;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 *
 * @author Vladimir Bogodukhov
 **/
@Api(State.INTERNAL)
public class MethodComparatorFactoryHolder {

    private static class Holder {
        private static final MethodComparatorFactory INSTANCE = new AsInSourceCodeMetodComparatorV3();
    }

    public static MethodComparatorFactory factory() {
        return Holder.INSTANCE;
    }

}
