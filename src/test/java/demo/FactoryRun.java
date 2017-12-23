package demo;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import com.steammachine.org.junit5.extensions.common.DiscoverySelectorWrapper;

/**
 * Created 19/12/16 11:18
 *
 * @author Vladimir Bogodukhov
 **/
class FactoryRun {


    @Test
    void test10() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(TestFactoryTest1.class));


        launcher.registerTestExecutionListeners(new TestExecutionListener() {


            @Override
            public void dynamicTestRegistered(TestIdentifier testIdentifier) {

            }

            @Override
            public void executionFinished(TestIdentifier testIdentifier,
                                          TestExecutionResult executionResult) {
                MethodSource methodSource = (MethodSource) testIdentifier.getSource().orElse(null);
            }
        });


        launcher.execute(builder.build());

    }


}
