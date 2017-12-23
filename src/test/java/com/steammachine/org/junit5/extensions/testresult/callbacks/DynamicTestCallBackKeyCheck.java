package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.org.junit5.extensions.testresult.callbacks.DynamicTestCallBackKey;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladimir Bogodukhov
 *         <p>
 *         Created by vladimirbogoduhov on 21/12/16.
 */
public class DynamicTestCallBackKeyCheck {

    @Test
    public void testName() {
        Assert.assertEquals("DynamicTestCallBackKey",
                DynamicTestCallBackKey.class.getName());
    }

    @Test
    public void testKey() {
        Assert.assertEquals("DYNAMIC_TESTS_KEY", DynamicTestCallBackKey.KEY);
    }

}