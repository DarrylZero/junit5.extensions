package com.steammachine.org.junit5.extensions.ignore.implementation;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;
import com.steammachine.org.junit5.extensions.ignore.Cached;

import com.steammachine.org.junit5.extensions.ignore.IgnoreCondition;


import java.util.function.Function;

/**
 * Кэш экземпляров условий
 * Внутреннее хранилище для экземпляров кэшируемых
 * ({@link Cached}) классов
 * Объект выбирается из кэша по его классу и набору параметров.
 *
 * @author Vladimir Bogodukhov
 **/
@Api(value = State.EXPERIMENT)
public interface StatelessConditionCache {

    /**
     * Получить экземпляр по его классу и набору параметров.
     *
     * @param clazz класс
     * @param function функция получения экземпляра
     *                 (используется только в том случае, если кэш еще не содержит объекта с параметрами)
     * @param params параметры
     * @param <T>
     * @return экземпляр класса clazz
     */
    <T extends IgnoreCondition> T retrieve(Class<T> clazz, Function<Class<T>, T> function, String... params);


}
