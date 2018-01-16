package com.steammachine.org.junit5.extensions.ignore;


import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 * Интерфейс условия фильтрации проигнорированных тестов.
 * <p>
 * <p>
 * <p>
 * Класс реализации должен иметь no-args конструктор
 *
 * @author Vladimir Bogodukhov
 *
 * {@link com.steammachine.org.junit5.extensions.ignore.IgnoreCondition}
 * com.steammachine.org.junit5.extensions.ignore.IgnoreCondition
 **/
@Api(State.MAINTAINED)
@FunctionalInterface
public interface IgnoreCondition {

    /**
     * Вычисление условия игнорирования теста
     *
     * @return {@code true} - игнорировать выполнение {@code false} - не игнорировать выполнение
     * @throws Exception исключение в процессе выполнения вычисления.
     */
    boolean evaluate() throws Exception;

}
