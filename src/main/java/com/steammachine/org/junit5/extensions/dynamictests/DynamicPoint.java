package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;

/**
 * Точка, раздел или часть динамических тестов, задаваемых на основе методов.
 * <p>
 * Created 21/10/16 15:46
 *
 * @author Vladimir Bogodukhov
 **/
public interface DynamicPoint {

    /**
     * Версия
     * значение монотонно возрастающее (более новая версия имеет больший номер)
     *
     * @return Версия of newInstance
     */
    int version();


    /**
     * Ссылка на helper
     *
     * @return Ссылка на helper
     */
    DynamicTestsHelper helper();

    /**
     * Установить значение объекта, на котором вызываются тестовые методы.
     *
     * @param nexus объект - всегда не null
     * @return ссылка на этот объект.
     */
    DynamicPoint setNexus(Object nexus);

    /**
     * Установка типов параметров вызываемых тестов.
     * Если типы параметров установлены - в объекте nexus см. {@link #setNexus(Object)}
     * производится поиск и использование всех не static и не private методов, возвращающих void
     * (пустое значение) если метод {@link #setParamTypes(Class...)}} не вызывается,
     * это ошибочное поведение.
     *
     * @param paramTypes - типы параметров.
     * @return ссылка на этот объект.
     * @see #addParams(Object...)
     * @see DynamicTestsHelper#iterator()
     * @see DynamicTestsHelper#stream()
     * @see DynamicTestsHelper#iterable()
     * @see DynamicTestsHelper#collection()
     */
    DynamicPoint setParamTypes(Class<?>... paramTypes);

    /**
     * Добавление набора параметров для тестов.
     * параметры добавленые для теста должны быть совместимы по типам с параметрами переданными в других
     * вызовах.
     * <p>
     * addParams(false, "one", 1L).
     * addParams(false, "one", 1L).
     * <p>
     * NB! при вызове любого метода получения тестов
     * <p>
     * Будут выбраны только те методы, которые могут быть вызваны с соотв параметрами.
     * Метод выбирается в том случае если фактический передаваемый параметр совместим с сигнатурой.
     *
     * @param param - параметры для одного теста
     * @return - ссылка на этот объект.
     * @see DynamicTestsHelper#iterator()
     * @see DynamicTestsHelper#stream()
     * @see DynamicTestsHelper#iterable()
     * @see DynamicTestsHelper#collection()
     */
    DynamicPoint addParams(Object... param);

    /**
     * Добавление набора параметров для тестов.
     * параметры добавленые для теста должны быть совместимы по типам с параметрами переданными в других
     * вызовах.
     * <p>
     *
     *
     *
     * addParams(false, "one", 1L).
     * addParams(false, "one", 1L).
     * <p>
     * NB! при вызове любого метода получения тестов
     * <p>
     * Будут выбраны только те методы, которые могут быть вызваны с соотв параметрами.
     * Метод выбирается в том случае если фактический передаваемый параметр совместим с сигнатурой.
     *
     * @param paramSet - параметры для тестов
     * @return - ссылка на этот объект.
     * @see #addParams(Object...)
     * @see DynamicTestsHelper#iterator()
     * @see DynamicTestsHelper#stream()
     * @see DynamicTestsHelper#iterable()
     * @see DynamicTestsHelper#collection()
     */
    DynamicPoint addParamSet(Collection<Object[]> paramSet);

    /**
     * Добавление набора параметров для тестов.
     * параметры добавленые для теста должны быть совместимы по типам с параметрами переданными в других
     * вызовах.
     * <p>
     *
     *
     *
     * addParams(false, "one", 1L).
     * addParams(false, "one", 1L).
     * <p>
     * NB! при вызове любого метода получения тестов
     * <p>
     * Будут выбраны только те методы, которые могут быть вызваны с соотв параметрами.
     * Метод выбирается в том случае если фактический передаваемый параметр совместим с сигнатурой.
     *
     * @param paramSet - параметры для тестов
     * @return - ссылка на этот объект.
     * @see #addParams(Object...)
     * @see DynamicTestsHelper#iterator()
     * @see DynamicTestsHelper#stream()
     * @see DynamicTestsHelper#iterable()
     * @see DynamicTestsHelper#collection()
     */
    DynamicPoint addParamSet(Object[][] paramSet);




    /**
     *
     * @param displayName
     * @param executable
     * @return ссылка на этот объект.
     * @see DynamicTest#dynamicTest(String, Executable) for more info
     */
    DynamicPoint addDynamicTest(String displayName, Executable executable);

    /**
     *
     *
     *
     * @param dynamicTest
     * @return ссылка на этот объект.
     * @see DynamicTest for more info
     */
    DynamicPoint addDynamicTest(DynamicTest ...dynamicTest);


    /**
     * Не выполнить никакой операции - используется для удобного разделения методов
     *
     * @return ссылка на этот объект.
     */
    DynamicPoint nop();

    /**
     * Проверить что при формиривании объекта с динамическими тестами формируется именно-то количество тестов,
     * которое ожидется. Метод может быть полезен в том случае когда требуется обезопситься от случайного изменения
     * сингнатуры методов тестов. Так как с изменением сигнатуры меняется конечное количество тестов
     *
     * @param expectedCount ожидаемое значение количества
     * @return ссылка на этот объект.
     */
    DynamicPoint checkTestCount(int expectedCount);


}
