package com.steammachine.org.junit5.extensions.timeout;

import com.steammachine.org.junit5.extensions.timeout.TestTimedOutException;
import com.steammachine.org.junit5.extensions.timeout.TimedOutExecution;
import com.steammachine.org.junit5.extensions.timeout.TimedOutExecutor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladimir Bogodukhov
 */
public class TimeOutNamesCheck {

    @Test
    public void testName() {
        Assert.assertEquals("TimedOutExecution",
                TimedOutExecution.class.getName());
        Assert.assertEquals("TestTimedOutException",
                TestTimedOutException.class.getName());
        Assert.assertEquals("TimedOutExecutor",
                TimedOutExecutor.class.getName());
    }



}