package com.steammachine.org.junit5.extensions.dynamictests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created 25/10/16 14:27
 *
 * @author Vladimir Bogodukhov
 *         <p>
 *         #EXAMPLE
 **/
@RunWith(DataProviderRunner.class)
public class JUnit4DataProviderExample {


    @DataProvider
    public static Object[][] dataprovidermethod() {
        return new Object[][]{
                {"One", 0, 0},
        };
    }

    @UseDataProvider("dataprovidermethod")
    @Test
    public void testMethod(String s, int i, int i2) {
        throw new IllegalStateException(); /* Тут проверяем что тест завершился с ошибкой. */
    }

}
