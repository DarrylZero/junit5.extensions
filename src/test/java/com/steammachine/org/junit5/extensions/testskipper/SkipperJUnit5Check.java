package com.steammachine.org.junit5.extensions.testskipper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladimir Bogoduhov
 */
public class SkipperJUnit5Check {

    @Test
    public void testNameIntegrity() {
        Assert.assertEquals("com.steammachine.org.junit5.extensions.testskipper.SkipperJUnit5",
                SkipperJUnit5.class.getName());
    }


}