package com.steammachine.org.junit5.extensions.testresult.callbacks;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 * Интерфейс регистрации
 *
 * @author Vladimir Bogodukhov
 *         UNDER_CONSTRUCTION!!!
 **/
@Api(State.INTERNAL)
public interface CallBacks {

    /**
     * Зарегистрировать call back для ключа.
     *
     * @param key      - ключ (не null)
     * @param callBack call back (не null)
     * @param <T>
     */
    <T> void register(Object key, CallBack<? extends T> callBack);

    /**
     * Отменить регистрирацию call back для ключа.
     *
     * @param key      - ключ (не null)
     * @param callBack call back (не null)
     * @param <T>
     */
    <T> void unregister(Object key, CallBack<? extends T> callBack);


    /**
     * Отменить регистрирацию call back для ключа.
     *
     * @param key - ключ (не null)
     * @param <T>
     */
    <T> void unregisterForKey(Object key);


}
