package com.steammachine.org.junit5.extensions.testskipper;

import com.steammachine.org.junit5.extensions.testskipper.SkipperJUnit5;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vladimir Bogoduhov on 17/07/17.
 *
 * @author Vladimir Bogoduhov
 */
public class SkipperJUnit5Check {

    @Test
    public void testNameIntegrity() {
        Assert.assertEquals("SkipperJUnit5",
                SkipperJUnit5.class.getName());
    }


}