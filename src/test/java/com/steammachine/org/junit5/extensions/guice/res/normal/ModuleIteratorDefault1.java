//package com.steammachine.org.junit5.extensions.guice.res.normal;
//
//import  com.google.inject.Module;
//import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects.AModule1;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// *
// * @author Vladimir Bogodukhov
// */
// #BREAK
//public class ModuleIteratorDefault1 {
//
//    private static final List<Class<? extends Module>> MODULES =
//            Collections.unmodifiableList(
//                    new ArrayList<Class<? extends Module>>() {
//                        {
//                            add(AModule1.class);
//                        }
//                    });
//
//    static Iterable<Class<? extends Module>> modules() {
//        return MODULES;
//    }
//
//}
