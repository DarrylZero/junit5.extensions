package com.steammachine.org.junit5.extensions.expectedexceptions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import com.steammachine.org.junit5.extensions.common.DiscoverySelectorWrapper;
import com.steammachine.org.junit5.extensions.common.JUnit5VersionsUtils;
import com.steammachine.org.junit5.extensions.common.MethodSourceWrapper;

import java.util.HashMap;
import java.util.Map;

import static com.steammachine.org.junit5.extensions.common.MethodSourceWrapper.isMethodSource;

/**
 * Created 27/09/16 08:05
 *
 * @author Vladimir Bogodukhov
 **/
public class ExpectedCheck {

    @Test
    public void testName() {
        Assert.assertEquals("Expected",
                Expected.class.getName());
    }

    @Test
    public void testExpectedJupiterRunName() {
        Assert.assertEquals("ExpectedJupiterRun",
                ExpectedJupiterRun.class.getName());
    }


    @Test
    public void testJupiter10() {
        Launcher launcher = LauncherFactory.create();

        Map<String, TestExecutionResult.Status> map = new HashMap<>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
                if (testIdentifier.isTest() && testIdentifier.getSource().isPresent()
                        && isMethodSource(testIdentifier.getSource().get().getClass())) {

                    MethodSourceWrapper wrapper = new MethodSourceWrapper(testIdentifier.getSource().get());
                    String key = wrapper.className() + "." + wrapper.methodName() + "()";
                    map.put(key, testExecutionResult.getStatus());
                }
            }
        });


        launcher.execute(
                LauncherDiscoveryRequestBuilder.request().
                        selectors(DiscoverySelectorWrapper.selectClass(ExpectedJupiterRun.class)).build());

        Assert.assertEquals(new HashMap<String, TestExecutionResult.Status>() {
            {
                put(ExpectedJupiterRun.class.getName() + ".test80()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedJupiterRun.class.getName() + ".test15()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedJupiterRun.class.getName() + ".test10()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedJupiterRun.class.getName() + ".test20()", TestExecutionResult.Status.FAILED);
                put(ExpectedJupiterRun.class.getName() + ".test52()", TestExecutionResult.Status.FAILED);
                put(ExpectedJupiterRun.class.getName() + ".test30()", TestExecutionResult.Status.FAILED);
                put(ExpectedJupiterRun.class.getName() + ".test40()", TestExecutionResult.Status.FAILED);
                put(ExpectedJupiterRun.class.getName() + ".test50()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedJupiterRun.class.getName() + ".test60()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedJupiterRun.class.getName() + ".test70()", TestExecutionResult.Status.FAILED);
            }
        }, map);
    }

    @Test
    public void testVintage10() {
        Launcher launcher = LauncherFactory.create();

        Map<String, TestExecutionResult.Status> map = new HashMap<>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
                if (testIdentifier.isTest() && testIdentifier.getSource().isPresent()
                        && isMethodSource(testIdentifier.getSource().get().getClass())) {

                    MethodSourceWrapper wrapper = new MethodSourceWrapper(testIdentifier.getSource().get());
                    String key = wrapper.className() + "." + wrapper.methodName() + "()";
                    map.put(key, testExecutionResult.getStatus());
                }
            }
        });

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().
                selectors(JUnit5VersionsUtils.selectClass(ExpectedVintageRun.class)).build();
/*selectors(DiscoverySelectorWrapper.selectClass(ExpectedVintageRun.class)).build());*/
        launcher.execute(request);

        Assert.assertEquals(new HashMap<String, TestExecutionResult.Status>() {
            {
                put(ExpectedVintageRun.class.getName() + ".test10()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedVintageRun.class.getName() + ".test15()", TestExecutionResult.Status.SUCCESSFUL);
                put(ExpectedVintageRun.class.getName() + ".test40()", TestExecutionResult.Status.FAILED);
                put(ExpectedVintageRun.class.getName() + ".test30()", TestExecutionResult.Status.FAILED);
                put(ExpectedVintageRun.class.getName() + ".test20()", TestExecutionResult.Status.FAILED);
            }
        }, map);


    }

}
