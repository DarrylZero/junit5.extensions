package com.steammachine.org.junit5.extensions.testresult.callbacks;

/**
 * Вспомогательный интерфейс -  вызова
 * <p>
 * Created 20/12/16 10:53
 *
 * @author Vladimir Bogodukhov
 *         UNDER_CONSTRUCTION!!!
 **/
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
