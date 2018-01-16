//package com.steammachine.org.junit5.extensions.guice.res.normal.shareddata2;
//
//import com.google.inject.Inject;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.RWSingletone;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import com.steammachine.org.junit5.extensions.guice.GuiceModules;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.AModule1;
//
///**
// *
// * @author Vladimir Bogodukhov
// *
// *
// * Неверное определение отсутствуют оба элемента
// */
//@GuiceModules(value = AModule1.class, space = "UNIVERSE_TWO")
//public class UnSharedDataSecond {
//
//    private RWSingletone rwSingletone = RWSingletone.NULL_OBJECT;
//    private static RWSingletone staticRwSingletone;
//
//
//    @Inject
//    public void setRwSingletone(RWSingletone rwSingletone) {
//        this.rwSingletone = rwSingletone;
//        /**
//         *
//         * Не очень удачный пример использования.  Показан тут в виде большого исключения.
//         */
//        staticRwSingletone = rwSingletone;
//    }
//
//    public static RWSingletone staticRwSingletone() {
//        /**
//         *
//         * Не очень удачный пример использования.  Показан тут в виде большого исключения.
//         */
//        return staticRwSingletone;
//    }
//
//    @Test
//    void second() {
//        /*Тесты намеренно прогоняются последовательно и этот выполняется второй*/
//        Assertions.assertEquals(null, rwSingletone.value());
//    }
//}
