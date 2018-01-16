package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 * Вспомогательный интерфейс -  вызова
 * <p>
 *
 * @author Vladimir Bogodukhov
 *         UNDER_CONSTRUCTION!!!
 **/
@Api(value = State.INTERNAL)
public interface Events {

    /**
     * отправить сообщение об успехе
     *
     * @param key  - ключ
     * @param info - дополнительные данные
     * @param <T>  -
     */
    <T> void fireSuccess(Object key, T info);

    /**
     * отправить сообщение о неуспехе выполнения.
     *
     * @param key       - ключ
     * @param info      - дополнительные данные
     * @param throwable - сообщение
     * @param <T>       -
     */
    <T> void fireFailure(Object key, T info, Throwable throwable);

}
