package com.steammachine.org.junit5.extensions.dynamictests.methodcomparator;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Created 20/04/16 13:11
 *
 * @author Vladimir Bogodukhov
 **/
@Api(APILevel.internal)
public interface MethodComparatorFactory {

    /**
     *
     * @param clazz class
     * @return comparator for class methods
     */
    Comparator<Method> methodComparator(Class<?> clazz);
}
