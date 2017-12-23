package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;

/**
 * Интерфейс условия фильтрации проигнорированных тестов.
 * <p>
 * <p>
 * <p>
 * Класс реализации должен иметь no-args конструктор
 * Created 28/09/16 09:52
 *
 * @author Vladimir Bogodukhov
 **/
@Api(APILevel.stable)
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
