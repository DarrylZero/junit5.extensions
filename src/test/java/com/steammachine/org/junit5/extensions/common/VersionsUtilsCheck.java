package com.steammachine.org.junit5.extensions.common;

import com.steammachine.org.junit5.extensions.common.VersionsUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author  Vladimir Bogodukhov
 */
public class VersionsUtilsCheck {


   /* --------------------------------------------------- isSubclassOf --------------------------------------------- */

    @Test(expected = NullPointerException.class)
    public void isSubclassOf() {
        VersionsUtils.isSubclassOf(null, null);
    }

    @Test
    public void isSubclassOf10() {
        Assert.assertFalse(VersionsUtils.isSubclassOf(null, "java.lang.Object"));
    }

    @Test
    public void isSubclassOf20() {
        Assert.assertTrue(VersionsUtils.isSubclassOf(String.class, "java.lang.Object"));
    }

    @Test
    public void isSubclassOf30() {
        Assert.assertTrue(VersionsUtils.isSubclassOf(String.class, "java.lang.String"));
    }

    @Test
    public void isSubclassOf40() {
        Assert.assertFalse(VersionsUtils.isSubclassOf(Object.class, "java.lang.String"));
    }


}