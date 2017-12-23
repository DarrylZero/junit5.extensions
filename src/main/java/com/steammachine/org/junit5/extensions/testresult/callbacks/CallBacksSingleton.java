package com.steammachine.org.junit5.extensions.testresult.callbacks;

/**
 * Created 20/12/16 11:58
 * <p>
 * Синглтон - хранилище данных по обратным вызовам -
 * Не лучшее, но пока что единственное решение.
 *
 * @author Vladimir Bogodukhov
 *         {@link CallBacksSingleton}
 **/
public class CallBacksSingleton {

    private static class HOLDER {
        private static final CommonCallBacks CALL_BACKS = new CommonCallBacks();
    }

    public static CommonCallBacks instance() {
        return HOLDER.CALL_BACKS;
    }
}
