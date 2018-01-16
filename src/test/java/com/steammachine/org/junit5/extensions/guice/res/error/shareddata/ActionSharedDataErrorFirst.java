//package com.steammachine.org.junit5.extensions.guice.res.error.shareddata;
//
////import com.google.inject.Inject;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.RWSingletone;
//import org.junit.Assert;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import com.steammachine.org.junit5.extensions.guice.GuiceModules;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.AModule1;
//
///**
// * @author Vladimir Bogodukhov
// *         <p>
// *         <p>
// *         Неверное определение отсутствуют оба элемента
// */
// #BREAK

//@GuiceModules(value = AModule1.class, space = "SHARED DATA EXPERIMENT")
//public class ActionSharedDataErrorFirst {
//
//    @Inject
//    private final RWSingletone rwSingletone = RWSingletone.NULL_OBJECT;
//
//    @Test
//    public void first() {
//        // Тесты намеренно прогоняются последовательно и этот выполняется первый
//        Assertions.assertEquals(null, rwSingletone.value());
//        rwSingletone.setValue("El Tio Vasilevs");
//        Assert.assertEquals("El Tio Vasilevs", rwSingletone.value());
//    }
//}
