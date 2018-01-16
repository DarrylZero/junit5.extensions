package com.steammachine.org.junit5.extensions.dynamictests.methodcomparator;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 *
 * @author Vladimir Bogodukhov
 **/
@Api(State.INTERNAL)
public interface MethodComparatorFactory {

    /**
     *
     * @param clazz class
     * @return comparator for class methods
     */
    Comparator<Method> methodComparator(Class<?> clazz);
}
