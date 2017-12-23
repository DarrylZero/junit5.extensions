package com.steammachine.org.junit5.extensions.guice.res.error.shareddata;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.RWSingletone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.guice.GuiceModules;

import java.util.List;

/**
 * @author Vladimir Bogodukhov
 *         Created 10/12/15 11:46
 *         <p>
 *         <p>
 *         Неверное определение отсутствуют оба элемента
 */
@GuiceModules(iterators = ActionSharedDataErrorThird.ModuleClassIterator.class,
        space = "SHARED DATA EXPERIMENT")
public class ActionSharedDataErrorThird {

    static class ModuleClassIterator {
        private static final List<Class<? extends Module>> CLASSES = null;
                //Arrays.asList(AModule2.class);


        private static Iterable<Class<? extends Module>> methodOfKhabensky() {
            return CLASSES;
        }
    }

    @Inject
    private final RWSingletone rwSingletone = RWSingletone.NULL_OBJECT;

    @Test
    void second() {
        /*Тесты намеренно прогоняются последовательно и этот выполняется второй*/
        Assertions.assertEquals("El Tio Vasilevs", rwSingletone.value());
    }
}
