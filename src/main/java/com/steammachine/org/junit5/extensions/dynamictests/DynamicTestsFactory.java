package com.steammachine.org.junit5.extensions.dynamictests;

/**
 * Тут описывается вспомогательный класс для быстрого формирования
 * аналогов параметризированных тестов в JUnit5.
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author Vladimir Bogodukhov
 *
 * {@link com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsFactory}
 * com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsFactory
 **/
public class DynamicTestsFactory {

    private DynamicTestsFactory() {
    }

    /**
     * Получить новый экземпляр хелпера - построителя
     *
     * @return -
     */
    public static DynamicTestsHelper newInstance() {
        return new DynamicTestsHelperBase();
    }

    /**
     * Получить новый экземпляр хелпера - построителя
     *
     * @return -
     */
    public static DynamicTestsHelper newInstance(TestInstanceFactory testInstanceFactory) {
        return new DynamicTestsHelperBase(testInstanceFactory);
    }

    /**
     * @return Версия фабрики
     */
    public static int version() {
        return DynamicTestsHelperBase.VERSION;
    }
}

