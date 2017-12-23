package com.steammachine.org.junit5.extensions.experiments.checks;

/**
 * Created 11/11/16 18:27
 *
 * @author Vladimir Bogodukhov
 **/

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;

import java.util.stream.Stream;

@TestResultNotification
public class JUnit5TestClassToTestOnDynamic {

    public static Object nex;

    /*  */
    public Object object = nex;


    @TestFactory
    public Stream<DynamicTest> factory() {
        return Stream.of(
                DynamicTest.dynamicTest("one", () -> {
                    throw new IllegalStateException();
                }),
                DynamicTest.dynamicTest("one", () -> {
                    throw new IllegalStateException();
                })
        );
    }

}
