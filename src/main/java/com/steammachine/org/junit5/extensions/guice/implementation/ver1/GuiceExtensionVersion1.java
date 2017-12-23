//package com.steammachine.org.junit5.extensions.guice.implementation.ver1;
//
//import com.google.inject.Injector;
//
//import static ru.socialquantum.guice.injection.InjectionUtils.makeInjection;
//
///**
// * Created 08/08/17 14:31
// *
// * @author Vladimir Bogodukhov
// *
// * Inspired by work of Fabio Strozzi.
// **/
//public class GuiceExtensionVersion1 implements TestInstancePostProcessor/*, ParameterResolver*/ {
//
//    public static final int VERSION = 1;
//
//    @Override
//    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
//        Injector injector = GuiceJUnit5AssistantVersion1.getInjector(testInstance.getClass());
//        makeInjection(injector, testInstance);
//    }
//
///*
//    @Override
//    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
//        return false;
//    }
//
//    @Override
//    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
//        return null;
//    }
//*/
//}
