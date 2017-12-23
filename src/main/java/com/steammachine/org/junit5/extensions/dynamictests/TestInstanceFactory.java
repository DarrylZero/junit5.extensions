package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

/**
 * Интерфейс фабрика получения объектов динамических тестов
 * Created 21/12/16 17:21
 * @author Vladimir Bogodukhov
 **/
@FunctionalInterface
public interface TestInstanceFactory {

    /**
     * Фабричный метод получения теста
     *
     * @param displayName -
     * @param executable -
     * @return Динамический тест
     */
    DynamicTest newDynamicTest(String displayName, Executable executable);
}
