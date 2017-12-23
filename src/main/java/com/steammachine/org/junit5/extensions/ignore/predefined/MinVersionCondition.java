package com.steammachine.org.junit5.extensions.ignore.predefined;

import com.steammachine.org.junit5.extensions.ignore.predefined.implementation.MinVersionConditionVer1;

/**
 * Класс предок для условий игрнорирования тестов по минимальной версии.
 * @author Vladimir Bogodukhov
 *         {@link MinVersionCondition}
 **/
public class MinVersionCondition extends MinVersionConditionVer1 {


    /***
     *
     * @param currentVersion текущая версия
     */
    public MinVersionCondition(int currentVersion) {
        super(currentVersion);
    }


    /**
     *
     * конструктор без параметров
     */
    public MinVersionCondition() {
    }
}

