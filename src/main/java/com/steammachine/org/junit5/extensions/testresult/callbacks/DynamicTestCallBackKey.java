package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 *
 * @author Vladimir Bogodukhov
 *
 * {@link DynamicTestCallBackKey}
 **/
@Api(State.INTERNAL)
@Deprecated
public class DynamicTestCallBackKey {

    public static final Object KEY = "DYNAMIC_TESTS_KEY";

}
