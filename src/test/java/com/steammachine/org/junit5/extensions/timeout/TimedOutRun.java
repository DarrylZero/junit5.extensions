package com.steammachine.org.junit5.extensions.timeout;

import com.steammachine.org.junit5.extensions.timeout.TimedOutExecutor;
import org.junit.Test;

/**
 * Created by vladimirbogoduhov on 29/09/16.
 *
 * @author Vladimir Bogodukhov
 */
public class TimedOutRun {


    @Test
    public void test() throws Throwable {
        TimedOutExecutor.timeoutExecution(3000,
                () -> {
                    try {
                        Thread.sleep(24440);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

}