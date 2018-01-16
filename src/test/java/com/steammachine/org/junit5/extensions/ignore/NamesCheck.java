package com.steammachine.org.junit5.extensions.ignore;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladimir Bogodukhov
 */
public class NamesCheck {

    @Test
    public void testNames() {
        Assert.assertEquals("com.steammachine.org.junit5.extensions.ignore.Cached",
                Cached.class.getName());
        Assert.assertEquals("com.steammachine.org.junit5.extensions.ignore.DefaultIgnoreCondition",
                DefaultIgnoreCondition.class.getName());
        Assert.assertEquals("com.steammachine.org.junit5.extensions.ignore.Ignore",
                Ignore.class.getName());
        Assert.assertEquals("com.steammachine.org.junit5.extensions.ignore.IgnoreCondition",
                IgnoreCondition.class.getName());
        Assert.assertEquals("com.steammachine.org.junit5.extensions.ignore.IgnoreExtension",
                IgnoreExtension.class.getName());
    }


}