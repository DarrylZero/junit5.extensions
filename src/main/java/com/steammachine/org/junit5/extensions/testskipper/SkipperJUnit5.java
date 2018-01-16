package com.steammachine.org.junit5.extensions.testskipper;

import org.junit.jupiter.api.Assumptions;
import org.opentest4j.TestAbortedException;

/**
 *
 * @author Vladimir Bogodukhov
 * {@link com.steammachine.org.junit5.extensions.testskipper.SkipperJUnit5}
 * com.steammachine.org.junit5.extensions.testskipper.SkipperJUnit5
 **/
public class SkipperJUnit5 {


    /**
     * Пропустить тест JUnit5
     * основан на соглашении junit. Выброс в любой точке теста исключения
     * {@link TestAbortedException} приводит к завершению теста - при этом сам тест считается
     * успешно пройденным
     *
     */
    public static void skipTest(String assumption, Throwable t) {
        Assumptions.assumeTrue(false);
    }

    /**
     * см. {@link #skipTest(String, Throwable)}
     * @param assumption -
     */
    public static void skipTest(String assumption) {
        Assumptions.assumeTrue(false, assumption);
    }

    /**
     * см. {@link #skipTest(String, Throwable)}
     * пропустить  тест
     */
    public static void skipTest() {
        Assumptions.assumeTrue(false);
    }

}
