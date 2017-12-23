package com.steammachine.org.junit5.extensions.experiments.checks;

/**
 * Created 11/11/16 18:27
 *
 * @author Vladimir Bogodukhov 
 **/

import org.junit.jupiter.api.Test;
import com.steammachine.org.junit5.extensions.ignore.IgnoreJ5;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;

@TestResultNotification
public class JUnit5TestClassToTest2On {

    public static Object nex;

    /*  */
    public Object object = nex;

    @Test
    @IgnoreJ5
    void testIgnored() {
    }

}
