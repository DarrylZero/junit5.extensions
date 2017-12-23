package com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * Created 10/12/15 15:17
 *
 * @author Vladimir Bogodukhov 
 **/
public class AModule1 implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ROSingletone.class).to(SingletoneImplementation.class).in(Scopes.SINGLETON);
        binder.bind(RWSingletone.class).to(SingletoneImplementation.class).in(Scopes.SINGLETON);
    }
}
