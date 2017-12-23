package com.steammachine.org.junit5.extensions.ignore.predefined;

import com.steammachine.org.junit5.extensions.ignore.predefined.MinVersionCondition;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vladimir Bogodukhov
 */
public class MinVersionConditionCheck {

    private static final List<Integer> VERSIONS = Arrays.asList(1);

    @Test
    public void testName() {
        Assert.assertEquals("MinVersionCondition",
                MinVersionCondition.class.getName());
    }

    @Test
    public void testVersions() {
        Assert.assertTrue(VERSIONS.contains(MinVersionCondition.VERSION));
    }


}