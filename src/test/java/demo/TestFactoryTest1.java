package demo;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

/**
 *
 * @author Vladimir Bogodukhov
 **/
@Api(State.INTERNAL)
public class TestFactoryTest1 {


    @TestFactory
    public Stream<DynamicTest> testFactory() {
        return Stream.of(
                DynamicTest.dynamicTest("testSuccess", () -> System.out.println("Success")),
                DynamicTest.dynamicTest("testFailure", () -> {
                    throw new IllegalStateException("failed");
                })
        );
    }


}
