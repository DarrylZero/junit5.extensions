package com.steammachine.org.junit5.extensions.dynamictests;

import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestFormationException;
import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsFactory;
import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsHelper;
import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsHelperBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vladimir Bogodukhov
 */
public class DynamicTestsNamesCheck {
    private static final List<Integer> VERSIONS = Arrays.asList(3, 4);

    @Test
    public void testDynamicTestsFactoryName() {
        Assert.assertEquals("DynamicTestsFactory",
                DynamicTestsFactory.class.getName());
    }

    @Test
    public void testDynamicTestsHelperName() {
        Assert.assertEquals("DynamicTestsHelper",
                DynamicTestsHelper.class.getName());
    }

    @Test
    public void testDynamicTestsHelperBaseName() {
        Assert.assertEquals("DynamicTestsHelperBase",
                DynamicTestsHelperBase.class.getName());
    }

    @Test
    public void testDynamicTestFormationExceptionName() {
        Assert.assertEquals("DynamicTestFormationException",
                DynamicTestFormationException.class.getName());
    }

    @Test
    public void testVersions10() {
        Assert.assertTrue(VERSIONS.contains(DynamicTestsFactory.version()));
    }

    @Test
    public void testVersions13() {
        Assert.assertEquals(DynamicTestsFactory.version(), DynamicTestsFactory.newInstance().version());
        Assert.assertEquals(
                DynamicTestsFactory.newInstance().version(),
                DynamicTestsFactory.newInstance().addPoint().version());

    }

    @Test
    public void testVersions20() {
        Assert.assertTrue(VERSIONS.contains(DynamicTestsFactory.newInstance().version()));
    }

    @Test
    public void testVersions30() {
        Assert.assertEquals(DynamicTestsFactory.version(),  DynamicTestsFactory.newInstance().version());
    }

    @Test
    public void testVersions40() {
        Assert.assertEquals(
                DynamicTestsFactory.newInstance().version(),
                DynamicTestsFactory.newInstance().addPoint().version());

    }

}