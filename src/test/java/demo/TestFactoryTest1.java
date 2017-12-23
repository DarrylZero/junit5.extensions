package demo;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

/**
 * Created 19/12/16 11:12
 *
 * @author Vladimir Bogodukhov
 **/
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
