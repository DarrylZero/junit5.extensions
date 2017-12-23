package com.steammachine.org.junit5.extensions.testresult.callbacks;

/**
 * Интерфейс регистрации
 * Created 20/12/16 10:15
 *
 * @author Vladimir Bogodukhov
 *         UNDER_CONSTRUCTION!!!
 **/
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
