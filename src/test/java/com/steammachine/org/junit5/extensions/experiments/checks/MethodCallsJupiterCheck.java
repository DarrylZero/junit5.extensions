package com.steammachine.org.junit5.extensions.experiments.checks;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import ru.socialquantum.common.utils.commonutils.CommonUtils;
import com.steammachine.org.junit5.extensions.common.DiscoverySelectorWrapper;
import com.steammachine.org.junit5.extensions.testresult.TestResultNotification;
import com.steammachine.org.junit5.extensions.testresult.dynamictestfactory.SignaledDynamicFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.socialquantum.common.utils.commonutils.CommonUtils.*;

/**
 * Created 11/11/16 18:26
 *
 * @author Vladimir Bogodukhov
 **/
@SuppressWarnings("ALL")
public class MethodCallsJupiterCheck {

    /**
     * В данном тесте создается класс c большим количеством проигнорированных методов (5000).
     * прогоняется и проверяется что все оповещения отработали.
     *
     * @throws IOException            -
     * @throws CannotCompileException -
     * @throws NotFoundException      -
     */
    @Test(timeout = 23000)
    public void constructedClassTest10() throws IOException, CannotCompileException, NotFoundException {
        final Class transformedClass;
        final int methodsCount = 1000;
        try (InputStream stream = openClassResource(JUnit5TestClassToTest2On.class)) {
            ClassPool pool = ClassPool.getDefault();
            CtClass clazz = pool.makeClass(stream);

            CtMethod onlyIgnored = Stream.of(clazz.getDeclaredMethods()).
                    filter((m) -> suppress(() -> m.getAnnotation(org.junit.jupiter.api.Test.class)) != null).
                    reduce(null, CommonUtils::reduceUnique);
            clazz.removeMethod(onlyIgnored);

            @SuppressWarnings("unchecked")
            List<AnnotationsAttribute> annotations =
                    (List<AnnotationsAttribute>) onlyIgnored.getMethodInfo().getAttributes().stream().
                            filter((o) -> o instanceof AnnotationsAttribute).
                            map((o) -> (AnnotationsAttribute) o).
                            collect(Collectors.toList());

            for (int i = 0; i < methodsCount; i++) {
                CtMethod method = new CtMethod(onlyIgnored, clazz, null);
                method.setName("ignored" + i);
                annotations.forEach((a) -> method.getMethodInfo().addAttribute(a));
                clazz.addMethod(method);
            }


            clazz.setName(clazz.getName() + "_TRWWW");
            transformedClass = clazz.toClass();
        }


        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(transformedClass));


        List<String> list = new ArrayList<>();
        Object nex = new Object() {
            void testSkipped(String identifier, Object testInstance, Method testMethod) {
                list.add(testInstance.getClass().getName() + "." + testMethod.getName());
            }
        };
        suppressWOResult(() -> transformedClass.getField("nex").set(null, nex));
        LauncherDiscoveryRequest request = builder.build();
        // launcher.discover(request);
        launcher.execute(request);
        suppressWOResult(() -> transformedClass.getField("nex").set(null, null));

        Collections.sort(list);

        List<String> template = new ArrayList<>();
        for (int i = 0; i < methodsCount; i++) {
            template.add(transformedClass.getName() + "." + "ignored" + i);
        }
        Collections.sort(template);

        Assert.assertEquals(template, list);
    }


    @Test
    public void constructedClassTest20() throws IOException, CannotCompileException {
        final Class transformedClass;
        try (InputStream stream = openClassResource(JUnit5TestClassToTestOn.class)) {
            ClassPool pool = ClassPool.getDefault();
            CtClass clazz = pool.makeClass(stream);
            List<CtMethod> testMethods = Stream.of(clazz.getDeclaredMethods()).
                    filter((m) -> suppress(() -> m.getAnnotation(org.junit.jupiter.api.Test.class)) != null).
                    collect(Collectors.toList());
            testMethods.forEach((m) -> suppressWOResult(() -> clazz.removeMethod(m)));

            for (int i = testMethods.size() - 1; i >= 0; i--) {
                clazz.addMethod(testMethods.get(i));
            }

            clazz.setName(clazz.getName() + "_TR");
            transformedClass = clazz.toClass();
        }

        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(transformedClass));


        List<String> list = new ArrayList<>();
        Object nex = new Object() {
            void testSuccess(String identifier, Object testInstance, Method testMethod) {
                list.add("testSuccess " + testInstance.getClass().getName() + "." + testMethod.getName());
            }

            void testFailure(String identifier, Object testInstance, Method testMethod, Throwable throwable) {
                list.add("testFailure " + testInstance.getClass().getName() + "." + testMethod.getName());
            }

            void testSkipped(String identifier, Object testInstance, Method testMethod) {
                list.add("testSkipped " + testInstance.getClass().getName() + "." + testMethod.getName());
            }
        };
        suppressWOResult(() -> transformedClass.getField("nex").set(null, nex));
        LauncherDiscoveryRequest request = builder.build();
        launcher.discover(request);
        launcher.execute(request);
        suppressWOResult(() -> transformedClass.getField("nex").set(null, null));

        Collections.sort(list);

        ArrayList<String> template = new ArrayList<>(Arrays.asList(
                "testFailure " + transformedClass.getName() + ".testFailure",
                "testSkipped " + transformedClass.getName() + ".testIgnored",
                "testSuccess " + transformedClass.getName() + ".testSuccess"));
        Collections.sort(template);
        Assert.assertEquals(template, list);
    }

    @Test
    public void test() {

        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(JUnit5TestClassToTestOn.class));

        List<String> list = new ArrayList<>();
        JUnit5TestClassToTestOn.nex = new Object() {
            void testSuccess(String identifier, Object testInstance, Method testMethod) {
                list.add("testSuccess " + testInstance.getClass().getName() + "." + testMethod.getName());
            }

            void testFailure(String identifier, Object testInstance, Method testMethod, Throwable throwable) {
                list.add("testFailure " + testInstance.getClass().getName() + "." + testMethod.getName());
            }

            void testSkipped(String identifier, Object testInstance, Method testMethod) {
                list.add("testSkipped " + testInstance.getClass().getName() + "." + testMethod.getName());
            }
        };
        launcher.execute(builder.build());
        JUnit5TestClassToTestOn.nex = null;

        Collections.sort(list);

        ArrayList<String> template = new ArrayList<>(Arrays.asList(
                "testFailure " + JUnit5TestClassToTestOn.class.getName() + ".testFailure",
                "testSkipped " + JUnit5TestClassToTestOn.class.getName() + ".testIgnored",
                "testSuccess " + JUnit5TestClassToTestOn.class.getName() + ".testSuccess"));
        Collections.sort(template);

        Assert.assertEquals(template, list);
    }

    @Test
    public void test20() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(JUnit5TestClassToTestOn.class));

        List<String> list = new ArrayList<>();
        JUnit5TestClassToTestOn.nex = new Object() {
            void testSuccess1(String identifier, Object testInstance, Method testMethod) {
                list.add("testSuccess " + testInstance.getClass().getName() + "." + testMethod.getName());
            }

            void testFailure1(String identifier, Object testInstance, Method testMethod, Throwable throwable) {
                list.add("testFailure " + testInstance.getClass().getName() + "." + testMethod.getName());
            }

            void testSkipped(String identifier, Object testInstance, Method testMethod) {
                list.add("testSkipped " + testInstance.getClass().getName() + "." + testMethod.getName());
            }
        };
        launcher.execute(builder.build());
        Collections.sort(list);
        Assert.assertEquals(
                Arrays.asList("testSkipped JUnit5TestClassToTestOn.testIgnored"),
                list
        );
    }

    @Test
    public void test25() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(JUnit5TestClassToTestOn.class));
        JUnit5TestClassToTestOn.nex = null; /* тут проверяем что выполнение происходит  даже если поле null */
        launcher.execute(builder.build());
    }

    @Test
    public void test30() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(JUnit5TestClassToTestOn.class));

        List<String> list = new ArrayList<>();
        JUnit5TestClassToTestOn.nex = new Object() {
            void testSkipped(String identifier, Object testInstance, Method testMethod) {
                list.add("testSkipped " + testInstance.getClass().getName() + "." + testMethod.getName());
            }
        };
        launcher.execute(builder.build());
        Collections.sort(list);
        Assert.assertEquals(
                Arrays.asList("testSkipped JUnit5TestClassToTestOn.testIgnored"),
                list
        );
    }


    @TestResultNotification
    private static class ErrorOnTest {

        public static Object nex;
        public Object object = nex;

        @org.junit.jupiter.api.Test
        void test() {
            throw new RuntimeException();
        }
    }


    @Test
    public void errorOnTest10() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ErrorOnTest.class));

        AtomicReference<Throwable> reference = new AtomicReference<>();
        ErrorOnTest.nex = new Object() {
            void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
                reference.set(throwable);
            }
        };
        Assert.assertNull(reference.get());
        launcher.execute(builder.build());
        Assert.assertNotNull(reference.get());
        Assert.assertEquals(RuntimeException.class, reference.get().getClass());

    }


    @TestResultNotification
    private static class ErrorOnBeforeEach {

        public static Object nex;
        public Object object = nex;

        @BeforeEach
        public void beforeEach() throws IOException {
            throw new IOException();
        }

        @org.junit.jupiter.api.Test
        void test() {
            throw new RuntimeException();
        }
    }


    @Test
    public void errorOnBeforeEach10() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ErrorOnBeforeEach.class));

        AtomicReference<Throwable> reference = new AtomicReference<>();
        ErrorOnBeforeEach.nex = new Object() {
            void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
                reference.set(throwable);
            }
        };
        Assert.assertNull(reference.get());
        launcher.execute(builder.build());
        Assert.assertNotNull(reference.get());
        Assert.assertEquals(IOException.class, reference.get().getClass());

    }

    @TestResultNotification
    private static class ErrorOnAfterEach {

        public static Object nex;
        public Object object = nex;

        @AfterEach
        public void beforeEach() throws IOException {
            throw new FileNotFoundException();
        }

        @org.junit.jupiter.api.Test
        void test() {
        }
    }


    @Test
    public void errorOnAfterEach10() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ErrorOnAfterEach.class));

        AtomicReference<Throwable> reference = new AtomicReference<>();
        ErrorOnAfterEach.nex = new Object() {
            void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
                reference.set(throwable);
            }
        };
        Assert.assertNull(reference.get());
        launcher.execute(builder.build());
        Assert.assertNotNull(reference.get());
        Assert.assertEquals(FileNotFoundException.class, reference.get().getClass());

    }

    /* -------------------------------- JUnit5TestClassToTestOn class notification -------------------------------- */

    @TestResultNotification
    public static class ClassWithErrorInAfterAll {
        private static final int SOME_VERSION = 1;
        private static Object NOTIFIER1;

        @BeforeAll
        public static void beforeClass() {
        }

        @AfterAll
        public static void afterClass() {
            throw new IllegalStateException();
        }

        @org.junit.jupiter.api.Test
        void test() {
        }
    }

    @Test
    public void jUnit5TestClassToTestOnClassNotification20() {
        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ClassWithErrorInAfterAll.class));

        AtomicBoolean value = new AtomicBoolean(false);
        ClassWithErrorInAfterAll.NOTIFIER1 = new Object() {
            void testClassFailure(Class testClass, String testClassName, Throwable exception) {
                value.set(true);
            }
        };

        Assert.assertFalse(value.get());
        launcher.execute(builder.build());
        Assert.assertTrue(value.get());

    }

    @TestResultNotification
    public static class ClassWithErrorInBeforeAll {
        private static Object NOTIFIER1;

        @BeforeAll
        public static void beforeClass() {
            throw new IllegalStateException();
        }

        @AfterAll
        public static void afterClass() {
        }

        @org.junit.jupiter.api.Test
        void test() {
        }
    }

    @Test
    public void jUnit5TestClassToTestOnClassNotification30() {
        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ClassWithErrorInBeforeAll.class));

        AtomicBoolean value = new AtomicBoolean(false);
        ClassWithErrorInBeforeAll.NOTIFIER1 = new Object() {
            void testClassFailure(Class testClass, String testClassName, Throwable exception) {
                value.set(true);
            }
        };

        Assert.assertFalse(value.get());
        launcher.execute(builder.build());
        Assert.assertTrue(value.get());

    }

    @TestResultNotification
    public static class ClassWithAllRight {
        private static Object NOTIFIER1;

        @BeforeAll
        public static void beforeClass() {
        }

        @AfterAll
        public static void afterClass() {
        }

        @org.junit.jupiter.api.Test
        void test() {
        }
    }

    @Test
    public void jUnit5TestClassToTestOnClassNotification40() {

        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ClassWithAllRight.class));

        AtomicBoolean value = new AtomicBoolean(false);
        ClassWithAllRight.NOTIFIER1 = new Object() {
            void testClassSuccess(Class testClass) {
                value.set(true);
            }
        };

        Assert.assertFalse(value.get());
        launcher.execute(builder.build());
        Assert.assertTrue(value.get());
    }

    @TestResultNotification
    public static class ClassTahFailsOnLoad {
        private static Object NOTIFIER1;

        static {
            CommonUtils.suppressWOResult(() -> {
                throw new IllegalStateException();
            });
        }

        @org.junit.jupiter.api.Test
        void test() {
        }
    }

    /**
     * Этот тест не выполняется.
     * в нем производится попытка получить поле от класса, который не может быть загружен.
     */
    @Test
    @Ignore
    public void classThatFailsOnLoad() {
        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ClassTahFailsOnLoad.class));

        AtomicBoolean value = new AtomicBoolean(false);
        ClassWithErrorInAfterAll.NOTIFIER1 = new Object() {
            void testClassFailure(Class testClass, String testClassName, Throwable exception) {
                value.set(true);
            }
        };

        Assert.assertFalse(value.get());
        launcher.execute(builder.build());
        Assert.assertTrue(value.get());
    }


    private static class Trrt extends Throwable {
    }

    @TestResultNotification
    private static class ClassThatFailsOnBeforeAll {
        private static Object NOTIFIER1;

        @BeforeAll
        public static void beforeAll() throws Trrt {
            throw new Trrt();
        }

        @org.junit.jupiter.api.Test
        void test() {
        }
    }

    @Test
    public void classThatFailsBeforeAll() {
        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(ClassThatFailsOnBeforeAll.class));

        AtomicReference<Throwable> value = new AtomicReference<>();
        ClassThatFailsOnBeforeAll.NOTIFIER1 = new Object() {
            void testClassFailure(Class testClass, String testClassName, Throwable exception) {
                value.set(exception);
            }
        };

        Assert.assertNull(value.get());
        launcher.execute(builder.build());
        Assert.assertNotNull(value.get());
        Assert.assertEquals(Trrt.class, value.get().getClass());
    }


    @Test
    public void dynamicTest() {
        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(RailDynamic.class));

        Map<String, String> map = new HashMap<>();
        Object notifier = new Object() {
            void testFailure(String testIdent, Object testInstance, Method method, Throwable throwable) {
                map.put("testFailure", "testFailure");
            }

            void testSuccess(String testIdent, Object testInstance, Method testMethod) {
                map.put("testSuccess", "testSuccess");
            }
        };

        RailDynamic.nex = notifier;
        Assert.assertEquals(Collections.emptyMap(), map);
        launcher.execute(builder.build());
        RailDynamic.nex = null;
        Assert.assertEquals(new HashMap<String, String>() {
            {
                put("testFailure", "testFailure");
                put("testSuccess", "testSuccess");
            }
        }, map);
    }

    @Test
    public void dynamicTest20() {
        Launcher launcher = LauncherFactory.create();

        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(RailDynamics.class));

        Map<String, String> map = new HashMap<>();
        int[] counterSuccess = {0};
        int[] counterFailures = {0};
        Object notifier = new Object() {
            void testFailure(String testIdent, Object testInstance, Method method, Throwable throwable) {
                int i = counterSuccess[0]++;
                map.put("testFailure" + i, "testFailure" + i);
            }

            void testSuccess(String testIdent, Object testInstance, Method testMethod) {
                int i = counterFailures[0]++;
                map.put("testSuccess" + i, "testSuccess" + i);
            }
        };

        RailDynamics.nex = notifier;
        Assert.assertEquals(Collections.emptyMap(), map);
        launcher.execute(builder.build());
        RailDynamics.nex = null;
        Assert.assertEquals(new HashMap<String, String>() {
            {
                put("testFailure0", "testFailure0");
                put("testFailure1", "testFailure1");
                put("testSuccess0", "testSuccess0");
                put("testSuccess1", "testSuccess1");
            }
        }, map);
    }

    @TestResultNotification
    public static class TestedClass {
        private Object notifier = new Object() {
            void testFailure(String testIdent, Object testInstance, Method method, Throwable throwable) {
            }

            void testSuccess(String testIdent, Object testInstance, Method testMethod) {
            }
        };

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
    }


    @Test
    public void dynamicTestSpeed() throws Exception {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(TestedClass.class));
        long measuredTime = CommonUtils.measureTime(() -> launcher.execute(builder.build()), 1000);
        Assert.assertTrue(measuredTime < 15_000);
    }


    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface AnAnnotation {
    }

    @TestResultNotification
    public static class TestedClass2 {
        private Object notifier = new Object() {
            void testFailure(String testIdent, Object testInstance, Method method, Throwable throwable) {

            }

            void testSuccess(String testIdent, Object testInstance, Method testMethod) {
                AnAnnotation annotation = testMethod.getAnnotation(AnAnnotation.class);
            }
        };

        @TestFactory
        public Stream<DynamicTest> factory() {
            return Stream.of(
                    SignaledDynamicFactory.dynamicTest("one",
                            new Executable() {
                                @Override
                                @AnAnnotation
                                public void execute() throws Throwable {
                                }
                            }
                    ),
                    SignaledDynamicFactory.dynamicTest("one", () -> {
                        throw new IllegalStateException();
                    })
            );
        }
    }


    @Test
    public void dynamicTestSpeed20() throws Exception {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(TestedClass2.class));
        long measuredTime = CommonUtils.measureTime(() -> launcher.execute(builder.build()), 1000);
        Assert.assertTrue(measuredTime < 15_000);
    }


}
