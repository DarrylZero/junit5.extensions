package com.steammachine.org.junit5.extensions.guice.res.normal;

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
@GuiceModules(iterators = ModuleIteratorPrivate1.class, space = "GUSTAVO CERRATI")
public class ActionNormalModuleIteratorPrivate1 {

    @Test
    void test1() {
    }
}
