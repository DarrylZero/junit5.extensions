package com.steammachine.org.junit5.extensions.experiments.checks;

/**
 * Created 11/11/16 18:27
 *
 * @author Vladimir Bogodukhov
 **/

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;
import com.steammachine.org.junit5.extensions.testresult.dynamictestfactory.SignaledDynamicFactory;

import java.util.stream.Stream;

@TestResultNotification
public class RailDynamics {

    public static Object nex;

    /*  */
    public Object object = nex;


    @TestFactory
    public Stream<DynamicTest> factory() {
        return Stream.of(
                SignaledDynamicFactory.dynamicTest("one",
                        () -> {
                        }
                ),
                SignaledDynamicFactory.dynamicTest("one", () -> {
                    throw new IllegalStateException();
                })
        );
    }

    @TestFactory
    public Stream<DynamicTest> factory2() {
        return Stream.of(
                SignaledDynamicFactory.dynamicTest("one",
                        () -> {
                        }
                ),
                SignaledDynamicFactory.dynamicTest("one", () -> {
                    throw new IllegalStateException();
                })
        );
    }

    @Override
    public String toString() {
        return "RailDynamic";
    }
}
