//package com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects;
//
//import com.google.inject.Inject;
//
//import java.util.Objects;
//
///**
// * @author Vladimir Bogodukhov
// */
//public class SingletoneImplementation implements ROSingletone, RWSingletone {
//
//    private final SingletoneImplementationSettings settings;
//
//    private String value;
//
//    @Inject
//    public SingletoneImplementation(SingletoneImplementationSettings settings) {
//        this.settings = Objects.requireNonNull(settings, "settings  is null");
//    }
//
//    public String value() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    @Override
//    public void failIfNotOverriden() {
//        //  в методе - который определен - все нормально - никакого исключения не выбрасывается
//    }
//}
