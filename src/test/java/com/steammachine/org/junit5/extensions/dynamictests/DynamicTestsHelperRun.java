package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import com.steammachine.org.junit5.extensions.common.DiscoverySelectorWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Bogodukhov
 */
@Tag("DebugRun")
public class DynamicTestsHelperRun {

    enum State {
        skipped,
        failed,
        success,
        aborted
    }

    @Test
    void test1() {
        Launcher launcher = LauncherFactory.create();

        Map<State, Integer> callCounts = new HashMap<>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionSkipped(TestIdentifier identifier, String reason) {

                if (identifier.isTest()) {
                    callCounts.putIfAbsent(State.skipped, 0);
                    callCounts.put(State.skipped, callCounts.get(State.skipped) + 1);
                }
            }

            @Override
            public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
                if (identifier.isTest()) {
                    State state = calcState(result);
                    callCounts.putIfAbsent(state, 0);
                    callCounts.put(state, callCounts.get(state) + 1);
                }
            }
        });


        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(JUnit4ParametrizedExample.class));
        LauncherDiscoveryRequest discoveryRequest = builder.build();

        TestPlan discover = launcher.discover(discoveryRequest);
        launcher.execute(discoveryRequest);
        /* We call class with two parametrized methods - each method is called three times.  One test always fails another always succeed
        And we get right result
        */

        Assertions.assertEquals(new HashMap<State, Integer>() {
            {
                put(State.success, 3); /* Three succedd */
                put(State.failed, 3);/* Three failed */
            }
        }, callCounts);
    }


    @Test
        // Test that fails but certainly mustn'nexus
    void test2() {
        Launcher launcher = LauncherFactory.create();

        Map<State, Integer> callCounts = new HashMap<>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionSkipped(TestIdentifier identifier, String reason) {

                if (identifier.isTest()) {
                    callCounts.putIfAbsent(State.skipped, 0);
                    callCounts.put(State.skipped, callCounts.get(State.skipped) + 1);
                }
            }

            @Override
            public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
                if (identifier.isTest()) {
                    State state = calcState(result);
                    callCounts.putIfAbsent(state, 0);
                    callCounts.put(state, callCounts.get(state) + 1);
                }
            }
        });

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectMethod(JUnit4ParametrizedExample.class.getName(), "test1"));
        LauncherDiscoveryRequest discoveryRequest = builder.build();

        TestPlan discover = launcher.discover(discoveryRequest);
        launcher.execute(discoveryRequest);
        /* we select  test1() method that always fails */



        /* if we call to test method that fails three times it is logical to get three failures as result */
        Assertions.assertEquals(new HashMap<State, Integer>() {
            {
                put(State.failed, 3);/* Three failed */
            }
        }, callCounts);
        /* However it is not so -  we get actually only one call */
    }

    @Test
        // Test that fails but certainly mustn'nexus.  The same as test2() but
        // another call is used
        // DiscoverySelectors.selectJavaMethod(JUnit4ParametrizedExample.class, JUnit4ParametrizedExample.class.getMethod("test1"))
        // instead of
        // DiscoverySelectors.selectJavaMethod(JUnit4ParametrizedExample.class, "test1")

    void test3() throws NoSuchMethodException {
        Launcher launcher = LauncherFactory.create();

        Map<State, Integer> callCounts = new HashMap<>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionSkipped(TestIdentifier identifier, String reason) {

                if (identifier.isTest()) {
                    callCounts.putIfAbsent(State.skipped, 0);
                    callCounts.put(State.skipped, callCounts.get(State.skipped) + 1);
                }
            }

            @Override
            public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
                if (identifier.isTest()) {
                    State state = calcState(result);
                    callCounts.putIfAbsent(state, 0);
                    callCounts.put(state, callCounts.get(state) + 1);
                }
            }
        });

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();



        builder.selectors(DiscoverySelectorWrapper.selectMethod(JUnit4ParametrizedExample.class.getName(),
                JUnit4ParametrizedExample.class.getMethod("test1").getName()));

        LauncherDiscoveryRequest discoveryRequest = builder.build();

        TestPlan discover = launcher.discover(discoveryRequest);
        launcher.execute(discoveryRequest);
        /* we select  test1() method that always fails */



        /* if we call to test method that fails three times it is logical to get three failures as result */
        Assertions.assertEquals(new HashMap<State, Integer>() {
            {
                put(State.failed, 3);/* Three failed */
            }
        }, callCounts);
        /* However it is not so -  we get actually only one call */
    }


    private static State calcState(TestExecutionResult result) {
        TestExecutionResult.Status status = result.getStatus();
        switch (status) {
            case ABORTED: {
                return State.aborted;
            }

            case FAILED: {
                return State.failed;
            }

            case SUCCESSFUL: {
                return State.success;
            }

            default: {
                throw new IllegalStateException("unknown state");
            }
        }
    }

}