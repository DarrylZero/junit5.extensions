package com.steammachine.org.junit5.extensions.ignore;


import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

/**
 * Дефолтная реализация условия фильтрации проигнорированных тестов с  предустановленными значением задаваемым в
 * конструкторе.
 *
 * <p>
 *
 * @author Vladimir Bogodukhov
 * @see IgnoreCondition
 *
 * {@link com.steammachine.org.junit5.extensions.ignore.DefaultIgnoreCondition }
 * com.steammachine.org.junit5.extensions.ignore.DefaultIgnoreCondition
 *
 **/
@Api(State.MAINTAINED)
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
