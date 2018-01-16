//package com.steammachine.org.junit5.extensions.guice.res.error.shareddata;
//
//import com.google.inject.Inject;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.AModule2;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.RWSingletone;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import com.steammachine.org.junit5.extensions.guice.GuiceModules;
//
///**
// *
// * @author Vladimir Bogodukhov
// *
// *
// * Неверное определение отсутствуют оба элемента
// */
// #BREAK
//@GuiceModules(value = AModule2.class, space = "SHARED DATA EXPERIMENT")
//public class ActionSharedDataErrorSecond {
//
//    @Inject
//    private final RWSingletone rwSingletone = RWSingletone.NULL_OBJECT;
//
//    @Test
//    void second() {
//        /*Тесты намеренно прогоняются последовательно и этот выполняется второй*/
//        Assertions.assertEquals("El Tio Vasilevs", rwSingletone.value());
//    }
//}
