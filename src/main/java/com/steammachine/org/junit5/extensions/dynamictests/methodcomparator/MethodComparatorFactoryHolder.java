package com.steammachine.org.junit5.extensions.dynamictests.methodcomparator;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;

/**
 * Created 26/10/16 13:48
 *
 * @author Vladimir Bogodukhov
 **/
@Api(APILevel.internal)
public class MethodComparatorFactoryHolder {

    private static class Holder {
        private static final MethodComparatorFactory INSTANCE = new AsInSourceCodeMetodComparatorV3();
    }

    public static MethodComparatorFactory factory() {
        return Holder.INSTANCE;
    }

}
