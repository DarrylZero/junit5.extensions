package com.steammachine.org.junit5.extensions.executable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Method;

/**
 * Created 20/12/16 11:04
 *
 * @author Vladimir Bogodukhov 
 **/
public class ExecutableCheck {

    @Test
    public void testName() {
        Assert.assertEquals("org.junit.jupiter.api.function.Executable",
                Executable.class.getName());
    }

    @Test
    public void testMethodPresence() throws NoSuchMethodException {
        Method execute = Executable.class.getMethod("execute");
    }

      
}
