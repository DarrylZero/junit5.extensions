package com.steammachine.org.junit5.extensions.guice.res.normal;

import com.google.inject.Module;

import java.util.List;

/**
 * Created 10/12/15 15:17
 *
 * @author Vladimir Bogodukhov 
 **/
public class ModuleIteratorProtected1 {

    private static final List<Class<? extends Module>> MODULES = null;
//            Arrays.asList(AModule1.class);

    protected static Iterable<Class<? extends Module>> modules() {
        return MODULES;
    }

}
