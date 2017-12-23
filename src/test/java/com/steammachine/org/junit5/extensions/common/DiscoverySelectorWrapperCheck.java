package com.steammachine.org.junit5.extensions.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.engine.DiscoverySelector;

import static ru.socialquantum.common.utils.metodsutils.MethodUtils.findMethods;

/**
 * @author Vladimir Bogodukhov
 */
public class DiscoverySelectorWrapperCheck {

    @Test
    public void test() {
        /* Не удалять - ищется по имени */
    }

    /* --------------------------------------------------- selectMethod  ------------------------------------------- */

    @Test
    public void selectMethod() {
        DiscoverySelector selector = DiscoverySelectorWrapper.selectMethod(DiscoverySelectorWrapperCheck.class.getName(), "test");
        Assert.assertNotNull(selector);
    }

    /* --------------------------------------------------- selectClass --------------------------------------------- */

    @Test
    public void selectClass() {
        DiscoverySelector selector = DiscoverySelectorWrapper.selectClass(DiscoverySelectorWrapperCheck.class);
        Assert.assertNotNull(selector);
    }

    @Test
    public void selectClass10() {
        DiscoverySelector selector = DiscoverySelectorWrapper.selectClass(DiscoverySelectorWrapperCheck.class);
        Assert.assertNotNull(selector);
    }

/*
    public static void main(String[] args) {
        JUnit5VersionsUtilsCheck check = new JUnit5VersionsUtilsCheck();
        check.selectMethod();
        check.selectClass();
    }
*/

}