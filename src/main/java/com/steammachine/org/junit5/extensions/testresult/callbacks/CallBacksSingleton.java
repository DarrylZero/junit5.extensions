package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 * <p>
 * Синглтон - хранилище данных по обратным вызовам -
 * Не лучшее, но пока что единственное решение.
 *
 * @author Vladimir Bogodukhov
 *         {@link CallBacksSingleton}
 **/
@Api(State.INTERNAL)
@Deprecated
public class CallBacksSingleton {

    private static class HOLDER {
        private static final CommonCallBacks CALL_BACKS = new CommonCallBacks();
    }

    public static CommonCallBacks instance() {
        return HOLDER.CALL_BACKS;
    }
}
