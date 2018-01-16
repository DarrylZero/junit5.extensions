package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 *
 * Вспомогательный интерфейс обратного вызова
 *
 * @author Vladimir Bogodukhov
 *
 *  UNDER_CONSTRUCTION!!!
 **/
@Api(State.INTERNAL)
public interface CallBack<T> {

    /**
     * Вызывается в момент когда тест выполнился нормально
     * @param info - дополнительные данные
     */
    default void railTestSuccess(T info) {
    }

    /**
     * Вызывается в момент когда тест выполнился не нормально
     * @param info - дополнительные данные
     */
    default void railTestFailure(T info, Throwable throwable) {
    }

}
