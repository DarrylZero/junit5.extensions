package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.org.junit5.extensions.testresult.callbacks.CallBacksSingleton;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vladimirbogoduhov on 20/12/16.
 *
 * @author Vladimir Bogoduhov
 */
public class CallBacksSingletonCheck {

    @Test
    public void test() {
        Assert.assertEquals("CallBacksSingleton",
                CallBacksSingleton.class.getName());
    }

}