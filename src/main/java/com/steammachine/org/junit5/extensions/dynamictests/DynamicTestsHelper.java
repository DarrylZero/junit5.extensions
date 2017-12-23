package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.DynamicTest;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created 21/10/16 15:46
 *
 * @author Vladimir Bogodukhov
 **/
public interface DynamicTestsHelper {

    /**
     * Версия
     * значение монотонно возрастающее (более новая версия имеет больший номер)
     *
     * @return Версия of newInstance
     */
    int version();

    /**
     * @return Добвленная точка
     * смотреть - {@link DynamicPoint}
     */
    DynamicPoint addPoint();

    /**
     * Проверить правильность формирования объекта. В процессе вызова метода проверяется,
     * что переданные данные
     * <p>
     * <p>
     * /**
     * проверить правильность формирования
     *
     * @return ссылка на этот объект.
     */
    DynamicTestsHelper check();


    /**
     * Проверить что при формиривании объекта с динамическими тестами формируется именно-то количество тестов,
     * которое ожидется. Метод может быть полезен в том случае когда требуется обезопситься от случайного изменения
     * сингнатуры методов тестов. Так как с изменением сигнатуры меняется конечное количество тестов
     *
     * @param expectedCount ожидаемое значение количества
     * @return ссылка на этот объект.
     */
    DynamicTestsHelper checkTestCount(int expectedCount);


    /**
     * @return Пустая операция. Используется для разделения.
     */
    DynamicTestsHelper nop();


    /***
     * Получить тесты в виде потока {@link Stream} {@link DynamicTest}
     * @return сформированные тесты в виде потока - {всегда не null}
     * @see #collection()
     * @see #iterable()
     * @see #iterator()
     */
    Stream<DynamicTest> stream();

    /**
     * Получить сформированные тесты в виде коллекции {@link Collection}.
     *
     * @return сформированные тесты в виде коллекции.{всегда не null}
     * @see #stream()
     * @see #iterable()
     * @see #iterator()
     */
    Collection<DynamicTest> collection();

    /**
     * Получить сформированные тесты в виде iterable {@link Iterable}
     *
     * @return {всегда не null}
     * @see #stream()
     * @see #collection()
     * @see #iterator()
     */
    Iterable<DynamicTest> iterable();


    /**
     * Получить сформированные тесты в виде iterator {@link Iterator}
     *
     * @return {всегда не null}
     * @see #stream()
     * @see #collection()
     * @see #iterable()
     */
    Iterator<DynamicTest> iterator();
}
