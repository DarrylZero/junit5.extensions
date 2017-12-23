package com.steammachine.org.junit5.extensions.timeout;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;

/**
 * Created 30/09/16 12:38
 *
 * @author Vladimir Bogodukhov 
 **/
@FunctionalInterface
@Api(value = APILevel.experimental)
public interface TimedOutExecution {

    /**
     * произвольные выолняемые действия
     *
     * @throws Exception
     */
    void evaluate() throws Exception;

}
