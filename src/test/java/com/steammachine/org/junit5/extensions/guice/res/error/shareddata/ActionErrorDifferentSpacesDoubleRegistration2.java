package com.steammachine.org.junit5.extensions.guice.res.error.shareddata;

import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.AModule2;
import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.guice.GuiceModules;

/**
 *
 * @author Vladimir Bogodukhov
 * Created 10/12/15 11:46
 *
 *
 * Неверное определение отсутствуют оба элемента
 */
@GuiceModules(value = AModule2.class, space = "GUSTAVO CERRATI")
public class ActionErrorDifferentSpacesDoubleRegistration2 {
    @Test
    public void test1() {
    }
}
