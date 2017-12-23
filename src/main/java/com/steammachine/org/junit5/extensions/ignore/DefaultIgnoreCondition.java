package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.types.Api;
import com.steammachine.org.junit5.extensions.types.APILevel;

/**
 * Дефолтная реализация условия фильтрации проигнорированных тестов с  предустановленными значением задаваемым в
 * конструкторе.
 *
 * <p>
 * Created 28/09/16 09:52
 *
 * @author Vladimir Bogodukhov
 * @see IgnoreCondition
 **/
@SuppressWarnings("WeakerAccess")
@Api(APILevel.stable)
@Cached(true)
public class DefaultIgnoreCondition implements IgnoreCondition  {

    /**
     *
     * Значение необходимости проигнорировать тест.
     *
     */
    private final boolean value;

    /**
     * Конструктор с заданием значения игнорирования теста.
     * @param value - значение {@code true} игнорировать тест  {@code false} НЕ игнорировать тест.
     */
    protected DefaultIgnoreCondition(boolean value) {
        this.value = value;
    }

    /**
     *
     * no-args конструктор
     *
     */
    public DefaultIgnoreCondition() {
        this(true);
    }

    @Override
    public boolean evaluate() throws Exception {
        return value;
    }
}
