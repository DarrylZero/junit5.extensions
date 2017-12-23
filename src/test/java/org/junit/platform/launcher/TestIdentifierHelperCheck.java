package org.junit.platform.launcher;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vladimirbogoduhov on 21/12/16.
 *
 * @author Vladimir Bogoduhov
 */
public class TestIdentifierHelperCheck {

    @Test
    public void testPackageName() {
        /*  перемещать из этого пакета нельзя */
        Assert.assertEquals("org.junit.platform.launcher", TestIdentifierHelper.class.getPackage().getName());

    }

}