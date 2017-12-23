package com.steammachine.org.junit5.extensions.experiments.checks;

/**
 * Created 11/11/16 18:27
 *
 * @author Vladimir Bogodukhov
 **/

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import ru.socialquantum.common.utils.commonutils.CommonUtils;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;
import com.steammachine.org.junit5.extensions.testresult.dynamictestfactory.SignaledDynamicFactory;

import java.util.stream.Stream;

@TestResultNotification
public class RailDynamic {

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

    @Override
    public String toString() {
        return "RailDynamic";
    }
}
