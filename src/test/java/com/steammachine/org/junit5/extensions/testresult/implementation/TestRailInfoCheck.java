package com.steammachine.org.junit5.extensions.testresult.implementation;

import org.junit.Assert;
import org.junit.Test;
import com.steammachine.org.junit5.extensions.testresult.TestRailInfo;

/**
 * Created by Vladimir Bogodukhov on 17/07/17.
 *
 * @author Vladimir Bogodukhov
 */
public class TestRailInfoCheck {

    @Test
    public void testNameIntegrity() {
        Assert.assertEquals("TestRailInfo",
                TestRailInfo.class.getName());
    }

    @Test
    public void testInfo10() {
        TestRailInfo info = TestRailInfo.info(1, 2, 3);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, info.caseIds());
        Assert.assertEquals(true, info.enabled());
    }

    @Test
    public void testInfo20() {
        TestRailInfo info = TestRailInfo.info(new int[]{1, 2, 3}, new String[]{"1", "2"});
        Assert.assertEquals(true, info.enabled());
        Assert.assertArrayEquals(new int[]{1, 2, 3}, info.caseIds());
        Assert.assertArrayEquals(new String[]{"1", "2"}, info.comments());
    }

    @Test
    public void testInfo30() {
        TestRailInfo info = TestRailInfo.info(new int[]{1, 2, 3}, new String[]{"1", "2"}, new String[]{"Filter1"});
        Assert.assertEquals(true, info.enabled());
        Assert.assertArrayEquals(new int[]{1, 2, 3}, info.caseIds());
        Assert.assertArrayEquals(new String[]{"1", "2"}, info.comments());
        Assert.assertArrayEquals(new String[]{"Filter1"}, info.filters());
    }


}