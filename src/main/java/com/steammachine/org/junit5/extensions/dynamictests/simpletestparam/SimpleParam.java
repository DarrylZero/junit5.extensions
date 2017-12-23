package com.steammachine.org.junit5.extensions.dynamictests.simpletestparam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

/**
 * Created 02/02/17 10:45
 *
 * @author Vladimir Bogodukhov
 **/
public class SimpleParam {

    private static final SimpleParam UNUSED = new SimpleParam(null, null, false);

    private final boolean used;
    private final String testName;
    private final Executable executable;

    public boolean used() {
        return used;
    }

    private SimpleParam(String testName, Executable executable, boolean used) {
        this.testName = testName;
        this.executable = executable;
        this.used = used;
    }

    public String testName() {
        return testName == null ? "" + this : testName;
    }

    private SimpleParam(String testName, Executable executable) {
        this(testName, executable, true);
    }

    /**
     * Создание нового Простого параметра для динамического теста.
     *
     * @param testName   наименование теста
     * @param executable исполняемый тест
     * @return
     */
    public static SimpleParam simpleTest(String testName, Executable executable) {
        return new SimpleParam(testName, executable);
    }

    public SimpleParam ignore() {
        return unused();
    }


    public static SimpleParam unused() {
        return UNUSED;
    }

    public static SimpleParam delimer(@SuppressWarnings("unused") String data) {
        return unused();
    }


    public static DynamicTest dynamicTest(SimpleParam p) {
        return DynamicTest.dynamicTest(p.testName, p.executable);
    }

    @Override
    public String toString() {
        return "SimpleParam{" +
                "used=" + used +
                ", testName='" + testName + '\'' +
                ", executable=" + executable +
                '}';
    }
}
