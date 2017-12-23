package com.steammachine.org.junit5.extensions.guice.res.normal;

import org.junit.Test;
import com.steammachine.org.junit5.extensions.guice.GuiceModules;

/**
 * @author Vladimir Bogodukhov
 * Created 10/12/15 11:46
 * <p>
 * <p>
 * Неверное определение отсутствуют оба элемента.
 */
/* @GuiceModules(value = AModule1.class, space = "GUSTAVO CERRATI") <---  В этом случае просто никакого внедоения не производится */
//
@GuiceModules(space = "GUSTAVO CERRATI")
public class ActionNormal2 {

    @Test
    public void test1() {
    }
}
