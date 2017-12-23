package com.steammachine.org.junit5.extensions.testresult;

import com.steammachine.org.junit5.extensions.testresult.TestStatus;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created 12/10/16 14:54
 *
 * @author Vladimir Bogodukhov 
 **/
public class TestStatusCheck {

    @Test
    public void testName() {
        Assert.assertEquals("TestStatus", TestStatus.class.getName());
    }
      
}
