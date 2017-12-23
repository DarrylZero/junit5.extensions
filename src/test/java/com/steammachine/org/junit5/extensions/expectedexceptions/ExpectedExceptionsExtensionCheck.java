package com.steammachine.org.junit5.extensions.expectedexceptions;

import com.steammachine.org.junit5.extensions.expectedexceptions.ExpectedExceptionsExtension;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by vladimirbogoduhov on 27/09/16.
 *
 * @author Vladimir Bogodukhov
 */
public class ExpectedExceptionsExtensionCheck {

/* ------------------------------------------------  matches ---------------------------------------------- */


    @Test(expected = NullPointerException.class)
    public void matches10() {
        ExpectedExceptionsExtension.matches(null, null, false);
    }

    @Test(expected = NullPointerException.class)
    public void matches20() {
        ExpectedExceptionsExtension.matches(null, null, true);
    }

    @Test(expected = NullPointerException.class)
    public void matches30() {
        ExpectedExceptionsExtension.matches(null, Arrays.asList(Throwable.class, null), true);
    }

    @Test(expected = NullPointerException.class)
    public void matches40() {
        ExpectedExceptionsExtension.matches(null, Arrays.asList(Throwable.class, null), false);
    }

    @Test(expected = NullPointerException.class)
    public void matches50() {
        ExpectedExceptionsExtension.matches(RuntimeException.class, Arrays.asList(Throwable.class, null), true);
    }

    @Test(expected = NullPointerException.class)
    public void matches60() {
        ExpectedExceptionsExtension.matches(RuntimeException.class, Arrays.asList(Throwable.class, null), false);
    }

    @Test
    public void matches70() {
        Assert.assertTrue(ExpectedExceptionsExtension.matches(RuntimeException.class,
                Arrays.asList(Throwable.class), false));
    }

    @Test
    public void matches73() {
        Assert.assertTrue(ExpectedExceptionsExtension.matches(IllegalStateException.class,
                Arrays.asList(RuntimeException.class), false));
    }

    @Test
    public void matches80() {
        Assert.assertFalse(ExpectedExceptionsExtension.matches(RuntimeException.class,
                Arrays.asList(Throwable.class), true));
    }

    @Test
    public void matches83() {
        Assert.assertTrue(ExpectedExceptionsExtension.matches(RuntimeException.class,
                Arrays.asList(Throwable.class, RuntimeException.class), true));
    }

    @Test
    public void matches84() {
        Assert.assertFalse(ExpectedExceptionsExtension.matches(IllegalStateException.class,
                Arrays.asList(Throwable.class, RuntimeException.class), true));
    }

    @Test
    public void matches90() {
        Assert.assertFalse(ExpectedExceptionsExtension.matches(Throwable.class,
                Arrays.asList(RuntimeException.class), false));
    }

    @Test
    public void matches100() {
        Assert.assertFalse(ExpectedExceptionsExtension.matches(Throwable.class,
                Arrays.asList(RuntimeException.class), true));
    }

    @Test
    public void matches110() {
        Assert.assertFalse(ExpectedExceptionsExtension.matches(RuntimeException.class,
                Arrays.asList(Throwable.class), true));

        Assert.assertTrue(ExpectedExceptionsExtension.matches(RuntimeException.class,
                Arrays.asList(Throwable.class), false));
    }






}