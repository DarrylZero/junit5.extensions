package com.steammachine.org.junit5.extensions.ignore;


import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для временного отключения теста. (junit5)
 * Тестовый класс или тестовый метод помеченный такой аннотацией исключается из прогона.
 * Аннотация предоставляет полную поддержку поведения Ignore для junit4 с дополнительными свойствами.
 *
 * @author Vladimir Bogodukhov
 *
 * {@link com.steammachine.org.junit5.extensions.ignore.Ignore}
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(IgnoreExtension.class)
@Api(value = State.MAINTAINED)
public @interface Ignore {


    /**
     * Дефолтная реализация условия исключения из прогона.
     * Предоставляет константное условия постоянного исключения.
     * Класс используется во всех аннотациях где явно не указан класс условия.
     */
    @Cached(true)
    final class Stub implements IgnoreCondition {

        /**
         * @return Default behavior is [always ignored]
         */
        @Override
        public boolean evaluate() throws Exception {
            return true;
        }
    }

    /**
     * The optional reason why the test is ignored.
     * Дополнительное неробязательная строка - причина игнорирования теста
     */
    String value() default "";

    /**
     * Класс условия описывает - условие отключения теста из прогона.
     * Условия не выполнения - игнорирования теста - описаны
     * в виде класса поддерживающего функциональный интерфейс
     * {@link IgnoreCondition}
     * <p>
     * Если экземпляр реализующий интерйейс IgnoreCondition выдает true
     * в методе {@link IgnoreCondition#evaluate()}, то такой тест исключается из прогона.
     * <p>
     *
     * @return - класс условия исключения из прогона.
     */
    Class<? extends IgnoreCondition> condition() default Stub.class;


    /**
     * Параметры инициализации объекта условия игнорирования теста.
     * Представляют собой набор строк с указанием значений свойств.
     * <p>
     * <p>
     * У созданного объекта условия устанавливаются свойства
     * Для
     * <p>
     * Записи property=1
     * <p>
     * у объекта условия будут вызываться ВСЕ методы setProperty с единственным параметром
     * <p>
     * Значение свойства (1) будет разбираться в соответствующий тип параметра.
     * Если значение невозможно привести к типу параметра выбрасывается исключение.
     * <p>
     * Для Записи property->null у объекта условия будут вызываться все методы setProperty с единственным параметром
     * Значение свойства null.
     * <p>
     * Попытка установить null в метод, с параметром примитивом - выбросит исключение
     * <p>
     * property=1
     * <p>
     * property - наименование свойства - метод setProperty в объекте
     * = разделитель
     * 1 значение - все что расположено начиная от разделителя и до конца строки
     * <p>
     * <p>
     * property->null
     * property - наименование свойства - метод setProperty в объекте
     * ->null  обозначение null значения
     *
     * @return массив параметров.
     */
    String[] params() default {};

}