package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.ignore.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladimir Bogodukhov
 */
public class NamesCheck {

    @Test
    public void testNames() {
        Assert.assertEquals("Cached",
                Cached.class.getName());
        Assert.assertEquals("DefaultIgnoreCondition",
                DefaultIgnoreCondition.class.getName());
        Assert.assertEquals("IgnoreJ5",
                IgnoreJ5.class.getName());
        Assert.assertEquals("IgnoreCondition",
                IgnoreCondition.class.getName());
        Assert.assertEquals("IgnoreExtension",
                IgnoreExtension.class.getName());
    }


}