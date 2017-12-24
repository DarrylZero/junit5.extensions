package com.steammachine.org.junit5.extensions.testresult;

import com.steammachine.org.junit5.extensions.testresult.NotifierCollector;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 *
 * @author Vladimir Bogodukhov
 */
public class NotifierCollectorCheck {

    @Test
    public void testNameIntegrity() {
        Assert.assertEquals("NotifierCollector",
                NotifierCollector.class.getName());
    }

    @Test
    public void testMethodsIntegrity() {
        Assert.assertEquals(
                new HashSet<>(Arrays.asList(
                        "collectNotifiers",
                        "collectClassNotifiers")),
                Arrays.stream(NotifierCollector.class.getDeclaredMethods()).
                        filter(method -> Modifier.isStatic(method.getModifiers()) &&
                                Modifier.isPublic(method.getModifiers())).map(Method::getName).
                        collect(Collectors.toSet()));
    }


}