package com.steammachine.org.junit5.extensions.guice.res.normal.shareddata;

import com.google.inject.Inject;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.RWSingletone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.guice.GuiceModules;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.AModule1;

/**
 * @author Vladimir Bogodukhov
 *         Created 10/12/15 11:46
 *         <p>
 *         <p>
 *         Неверное определение отсутствуют оба элемента
 */
@GuiceModules(value = AModule1.class, space = "SHARED_DATA_ONE")
public class ActionSharedDataNormalFirst {

    private RWSingletone rwSingletone = RWSingletone.NULL_OBJECT;
    private static RWSingletone staticRwSingletone;

    @Inject
    public void setRwSingletone(RWSingletone rwSingletone) {
        /**
         *
         * Не очень удачный пример использования.  Показан тут в виде большого исключения.
         */
        this.rwSingletone = rwSingletone;
        staticRwSingletone = rwSingletone;
    }

    public static RWSingletone staticRwSingletone() {
        /**
         *
         * Не очень удачный пример использования.  Показан тут в виде большого исключения.
         */
        return staticRwSingletone;
    }

    @Test
    void first() {
        // Тесты намеренно прогоняются последовательно и этот выполняется первый
        Assertions.assertEquals(null, rwSingletone.value());
        rwSingletone.setValue("El Tio Vasilevs");
        Assertions.assertEquals("El Tio Vasilevs", rwSingletone.value());
    }
}
