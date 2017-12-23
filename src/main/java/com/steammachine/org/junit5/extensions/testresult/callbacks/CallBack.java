package com.steammachine.org.junit5.extensions.testresult.callbacks;

/**
 *
 * Вспомогательный интерфейс обратного вызова
 *
 * Created 19/12/16 13:33
 * @author Vladimir Bogodukhov
 *
 *  UNDER_CONSTRUCTION!!!
 **/
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
