package com.steammachine.org.junit5.extensions.testresult;

import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created 12/10/16 14:56
 *
 * @author Vladimir Bogodukhov
 **/
public class TestResultNotificationCheck {
    @Test
    public void testName() {
        Assert.assertEquals("TestResultNotification",
                TestResultNotification.class.getName());
    }
}
