package com.steammachine.org.junit5.extensions.guice;

import com.steammachine.org.junit5.extensions.guice.res.error.shareddata.*;
import com.steammachine.org.junit5.extensions.guice.res.normal.*;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.ActionSharedDataNormalFirst;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata2.UnSharedDataSecond;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import com.steammachine.org.junit5.extensions.guice.res.error.ActionErrorTwoElementsAreAbsent;
import com.steammachine.org.junit5.extensions.guice.res.error.ActionErrorTwoElementsArePresent;
import ru.socialquantum.junit5.addons.guice.res.error.shareddata.*;
import ru.socialquantum.junit5.addons.guice.res.normal.*;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.ActionSharedDataNormalSecond;
import com.steammachine.org.junit5.extensions.guice.res.normal.shareddata2.UnSharedDataFirst;

import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Vladimir Bogodukhov
 */
public class GuiceJUnit5ExtensionCheck {

    private static class FailureListener implements TestExecutionListener {
        private boolean success = true;

        @Override
        public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
            success = success && testExecutionResult.getStatus() != TestExecutionResult.Status.FAILED;
        }

        public boolean success() {
            return success;
        }
    }

    /*  ---------------------------------------------------- error ----------------------------------------------- */

    @Test(expected = IllegalStateException.class)
    public void error_twoElementsAreAbsent10() {
        checkJunit5Tests(ActionErrorTwoElementsAreAbsent.class);
        checkJunit5TestsOrdered(ActionErrorTwoElementsAreAbsent.class);
    }

    @Test(expected = IllegalStateException.class)
    public void error_twoElementsArePresent40() {
        checkJunit5Tests(
                ActionErrorDifferentSpacesDoubleRegistration1.class,
                ActionErrorDifferentSpacesDoubleRegistration2.class
        );
    }

    @Test(expected = IllegalStateException.class)
    public void error_twoElementsArePresent10() {
        checkJunit5Tests(ActionErrorTwoElementsArePresent.class);
    }

    @Test(expected = IllegalStateException.class)
    public void error_ActionSharedDataErrort10() {
        checkJunit5Tests(
                ActionSharedDataErrorFirst.class,
                ActionSharedDataErrorSecond.class
        );
    }

    @Test(expected = IllegalStateException.class)
    public void error_ActionSharedDataErrort20() {
        checkJunit5Tests(
                ActionSharedDataErrorFirst.class,
                ActionSharedDataErrorThird.class
        );
    }

    @Test(expected = IllegalStateException.class)
    public void error_ActionSharedDataErrort30() {
        checkJunit5Tests(/*ActionSharedDataErrorFirst.class, */ActionSharedDataErrorThird.class);
    }

    @Test(expected = IllegalStateException.class)
    public void error_twoElementsArePresent30() {
        checkJunit5Tests(ActionErrorTwoElementsArePresent.class);
    }

    /*  ------------------------------------- normal execution --------------------------------------------------- */

    @Test
    public void normal10() {
        checkJunit5Tests(ActionNormal1.class);
    }

    @Test
    public void normal13() {
        checkJunit5Tests(ActionNormal2.class);
    }

    @Test
    public void normal20() {
        checkJunit5Tests(ActionNormalModuleIteratorPublic1.class);
    }

    @Test
    public void normal30() {
        checkJunit5Tests(ActionNormalModuleIteratorPrivate1.class);
    }

    @Test
    public void normal40() {
        checkJunit5Tests(ActionNormalModuleIteratorDefault1.class);
    }

    @Test
    public void normal50() {
        checkJunit5Tests(ActionNormalModuleIteratorProtected1.class);
    }

    /**
     * Этот метод вызывает последовательно 2 теста - в первом тесте устанавливается значение состояния объекта
     * во втором тесте проверяется установленное значение
     * Использование  классов ActionSharedDataNormalFirst и ActionSharedDataNormalSecond может быть только тут -
     * в виду особенностей теста.
     */
    @Test
    public void normalCheckSharedData1() {
        Assert.assertEquals(null, ActionSharedDataNormalFirst.staticRwSingletone());
        Assert.assertEquals(null, ActionSharedDataNormalSecond.staticRwSingletone());
        checkJunit5Tests(ActionSharedDataNormalFirst.class, ActionSharedDataNormalSecond.class);
        Assert.assertNotNull(ActionSharedDataNormalFirst.staticRwSingletone());
        Assert.assertNotNull(ActionSharedDataNormalSecond.staticRwSingletone());


        /**
         *
         * Главная проверка того что объекты, загруженные в РАЗНЫХ тестах одни и те же.
         *
         */
        Assert.assertEquals(
                ActionSharedDataNormalFirst.staticRwSingletone(), ActionSharedDataNormalSecond.staticRwSingletone());
    }


    /**
     * Этот метод вызывает последовательно 2 теста - в первом тесте устанавливается значение состояния объекта
     * во втором тесте проверяется установленное значение  Объекты расположены в разных пространствах
     * Использование  классов UnSharedDataFirst и UnSharedDataSecond может быть только тут - в виду особенностей теста.
     */
    @Test
    public void normalCheckUnSharedData1() {
        Assert.assertEquals(null, UnSharedDataFirst.staticRwSingletone());
        Assert.assertEquals(null, UnSharedDataSecond.staticRwSingletone());
        checkJunit5Tests(UnSharedDataFirst.class, UnSharedDataSecond.class);
        Assert.assertNotNull(UnSharedDataFirst.staticRwSingletone());
        Assert.assertNotNull(UnSharedDataSecond.staticRwSingletone());
        Assert.assertNotEquals(UnSharedDataFirst.staticRwSingletone(), UnSharedDataSecond.staticRwSingletone());
    }


    public static void checkJunit5Tests(Class<?>... classes) {
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(Stream.of(classes).map(DiscoverySelectors::selectClass).collect(Collectors.toList()));

        FailureListener failureListener = new FailureListener();
        launcher.registerTestExecutionListeners(failureListener);
        LauncherDiscoveryRequest discoveryRequest = builder.build();
        launcher.execute(discoveryRequest);
        if (!failureListener.success()) throw new IllegalStateException();
    }

    /**
     * Выполнение классов тестов в порядке, определенном в массиве
     *
     * @param classes классы тестов
     */
    public static void checkJunit5TestsOrdered(Class<?>... classes) {
        Stream.of(classes).forEachOrdered(GuiceJUnit5ExtensionCheck::checkJunit5Tests);
    }

}