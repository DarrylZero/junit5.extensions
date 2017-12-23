package com.steammachine.org.junit5.extensions.timeout;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;

import java.util.concurrent.TimeUnit;

/**
 * Created 29/09/16 19:58
 *
 * @author Vladimir Bogodukhov 
 **/
@Api(value = APILevel.experimental)
public class TestTimedOutException extends RuntimeException {

    private static final long serialVersionUID = 31935685163547539L;

    private final TimeUnit timeUnit;
    private final long timeout;

    /**
     * Creates exception with a standard message "test timed out after [timeout] [timeUnit]"
     *
     * @param timeout the amount of time passed before the test was interrupted
     * @param timeUnit the time unit for the timeout value
     */
    public TestTimedOutException(long timeout, TimeUnit timeUnit) {
        super(String.format("test timed out after %d %s",
                timeout, timeUnit.name().toLowerCase()));
        this.timeUnit = timeUnit;
        this.timeout = timeout;
    }

    /**
     * Gets the time passed before the test was interrupted
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * Gets the time unit for the timeout value
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
