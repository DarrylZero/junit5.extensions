package com.steammachine.org.junit5.extensions.testresult;

import com.steammachine.org.junit5.extensions.testresult.TestRailInfo;
import com.steammachine.org.junit5.extensions.testresult.TestRailResultHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import ru.socialquantum.common.utils.commonutils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vladimir Bogodukhov on 18/07/17.
 *
 * @author Vladimir Bogodukhov
 */
public class TestRailResultHandlerCheck {

    @Test
    public void testNameIntegrity() {
        Assert.assertEquals("TestRailResultHandler",
                TestRailResultHandler.class.getName());
    }

    @Test
    public void testFuncs10() throws Throwable {

        List<Integer> list = new ArrayList<>();
        Object nexus = new Object() {
            Object notifier = new Object() {
                void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e) {
                    list.addAll(Arrays.stream(caseIds).mapToObj(value -> value).collect(Collectors.toList()));
                }
            };
        };
        Function<TestRailInfo, Executable> rail = TestRailResultHandler.rail(nexus, testRailInfo -> testRailInfo,
                testRailInfo -> () -> {
                    throw new IllegalStateException();
                });

        CommonUtils.skipAllExceptions(() -> rail.apply(TestRailInfo.info(1, 2)).execute());
        Assert.assertEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void testFuncs20() throws Throwable {

        List<Integer> list = new ArrayList<>();
        Object nexus = new Object() {
            Object notifier = new Object() {
                void testSuccessful(int[] caseIds, String[] comments, String[] filters) {
                    list.addAll(Arrays.stream(caseIds).mapToObj(value -> value).collect(Collectors.toList()));
                }

                void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e) {
                    list.addAll(Arrays.stream(caseIds).mapToObj(value -> value).collect(Collectors.toList()));
                }


            };
        };
        Function<TestRailInfo, Executable> rail = TestRailResultHandler.rail(nexus, testRailInfo -> testRailInfo,
                testRailInfo -> () -> {
                    throw new IllegalStateException();
                });

        CommonUtils.skipAllExceptions(() -> rail.apply(TestRailInfo.info(1, 2)).execute());
        Assert.assertEquals(Arrays.asList(1, 2), list);
    }

}