package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import ru.socialquantum.common.lazyeval.LazyEval;
import ru.socialquantum.common.utils.commonutils.CommonUtils;
import ru.socialquantum.common.utils.metodsutils.MethodCaller;
import com.steammachine.org.junit5.extensions.common.DiscoverySelectorWrapper;
import com.steammachine.org.junit5.extensions.dynamictests.impls.DynamicTestUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.socialquantum.common.utils.metodsutils.MethodUtils.*;


/**
 * @author Vladimir Bogodukhov
 */
@SuppressWarnings("ALL")
public class DynamicTestsHelperCheck {

    /* ----------------------------------------- isMethodCompatible ----------------------------------------------- */


    @SuppressWarnings("unused")
    private static class ClassToCheckMethods {

        private static void privateStatic1(int i, long l) {
        }

        private void private2(int i, long l) {
        }

        private void private3(Integer i, Long l) {
        }

    }

    /* ----------------------------------------- generateDynamicTests -------------------------------------------- */


    @SuppressWarnings("unused")
    private static class KindATesst {
        void test(Long l1, long l2) {
        }

        void test2(Long l1, Long l2) {
        }

        void test3(Object l1, Object l2) {
        }

        public void test4(Object l1, Object l2) {
        }

        private void test5(Object l1, Object l2) {
        }
    }

    @Test
    public void generateDynamicTests10() {
        Stream<DynamicTest> stream = DynamicTestsFactory.newInstance().
                addPoint().
                setParamTypes(Long.TYPE, Long.TYPE).
                setNexus(new KindATesst()).
                addParams(1L, 2L).
                addParams(3L, 4L).
                addParams(5L, 6L).
                addParams(null, null).
                helper().stream();

        List<String> list = sortList(stream.map(DynamicTest::getDisplayName).collect(Collectors.toList()));
        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test(1L, 2L)",
                        "test(3L, 4L)",
                        "test(5L, 6L)",
                        "test2(1L, 2L)",
                        "test2(3L, 4L)",
                        "test2(5L, 6L)",
                        "test2(null, null)",
                        "test3(1L, 2L)",
                        "test3(3L, 4L)",
                        "test3(5L, 6L)",
                        "test3(null, null)",
                        "test4(1L, 2L)",
                        "test4(3L, 4L)",
                        "test4(5L, 6L)",
                        "test4(null, null)"
                )),
                list
        );


    }


    @Test
    public void generateDynamicTests20() {
        KindATesst nexus = new KindATesst();
        Stream<DynamicTest> stream = DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(nexus).
                setParamTypes(Long.TYPE, Long.TYPE).
                addParams(1L, 2L).
                addParams(3L, 1L).
                addParams(3L, 1L).
                helper().
                stream();

        stream.forEach(t -> CommonUtils.suppressAll(() -> {
            t.getExecutable().execute();
            return null;
        }));
    }

    // Methref
    enum En {
        A, B, C
    }

    @SuppressWarnings("unused")
    private static class KindATesst2 {
        void test(Long l1, long l2, En en) {
            fire(l1, l2, en);
        }

        void test2(Long l1, Long l2, En en) {
            fire(l1, l2, en);
        }

        void test3(Object l1, Object l2, En en) {
            fire(l1, l2, en);
        }

        public void test4(Object l1, Object l2, En en) {
            fire(l1, l2, en);
        }

        private void test5(Object l1, Object l2, En en) {
            fire(l1, l2, en);
        }

        public void fire(Object... o) {
        }
    }


    @Test
    public void generateDynamicTests30() {
        Stream<DynamicTest> stream = DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new KindATesst2()).
                setParamTypes(Long.class, Long.class, En.class).
                addParams(1L, 2L, En.A).
                addParams(3L, 4L, En.B).
                addParams(5L, 6L, En.C).
                addParams(null, null, En.C).
                helper().
                stream();

        List<String> list = sortList(stream.map(DynamicTest::getDisplayName).collect(Collectors.toList()));
        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test(1L, 2L, DynamicTestsHelperCheck.En.A)",
                        "test(3L, 4L, DynamicTestsHelperCheck.En.B)",
                        "test(5L, 6L, DynamicTestsHelperCheck.En.C)",
                        "test2(1L, 2L, DynamicTestsHelperCheck.En.A)",
                        "test2(3L, 4L, DynamicTestsHelperCheck.En.B)",
                        "test2(5L, 6L, DynamicTestsHelperCheck.En.C)",
                        "test2(null, null, DynamicTestsHelperCheck.En.C)",
                        "test3(1L, 2L, DynamicTestsHelperCheck.En.A)",
                        "test3(3L, 4L, DynamicTestsHelperCheck.En.B)",
                        "test3(5L, 6L, DynamicTestsHelperCheck.En.C)",
                        "test3(null, null, DynamicTestsHelperCheck.En.C)",
                        "test4(1L, 2L, DynamicTestsHelperCheck.En.A)",
                        "test4(3L, 4L, DynamicTestsHelperCheck.En.B)",
                        "test4(5L, 6L, DynamicTestsHelperCheck.En.C)",
                        "test4(null, null, DynamicTestsHelperCheck.En.C)")
                ),
                list
        );
    }

    @Test(expected = DynamicTestFormationException.class)
    public void generateDynamicTests40() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setParamTypes(Long.TYPE, Long.TYPE).
                setNexus(new KindATesst2()).
                addParams(1L, 2L).
                addParams(5L, 6L, En.C).
                helper().
                stream();
    }

    @Test(expected = DynamicTestFormationException.class)
    public void generateDynamicTests50() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new KindATesst2()).
                addParams(1L, 2L, En.A).
                addParams(3L, 4L, "").
                helper().
                check();
    }




    /* ----------------------------------------- DynamicTestsHelper.generateDynamicTests-------------------------------------------- */


    @Test
    public void generateDynamicTests03() {
        DynamicTestsFactory.newInstance().stream();
    }

    @Test
    public void dynamicTestsHelperGenerateDynamicTests02() {
        DynamicTestsFactory.newInstance().iterator();
    }

    @Test
    public void dynamicTestsHelperGenerateDynamicTests03() {
        DynamicTestsFactory.newInstance().collection();
    }

    @Test
    public void dynamicTestsHelperGenerateDynamicTests04() {
        DynamicTestsFactory.newInstance().iterable();
    }

    @Test
    public void dynamicTestsHelperGenerateDynamicTests05() {
        Stream<DynamicTest> tests = DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new JUnit5ParametrizedExample()).
                setParamTypes(Boolean.TYPE, Long.TYPE, Integer.TYPE).
                addParams(false, 1L, 21).
                addParams(true, 21L, 11).
                addParams(false, 10L, 21).
                helper().
                stream();
        tests.forEach((t) -> {
        });
    }

    enum Status {
        skipped,
        failed,
        success,
        aborted
    }


    @Test
    public void dynamicTestsHelperGenerateDynamicTests10() {
        Launcher launcher = LauncherFactory.create();
        Map<Status, Integer> callCounts = new HashMap<>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionSkipped(TestIdentifier identifier, String reason) {
                if (identifier.isTest()) {
                    callCounts.putIfAbsent(Status.skipped, 0);
                    callCounts.put(Status.skipped, callCounts.get(Status.skipped) + 1);
                }
            }

            @Override
            public void executionFinished(TestIdentifier identifier, TestExecutionResult result) {
                if (identifier.isTest()) {
                    Status state = calcState(result);
                    callCounts.putIfAbsent(state, 0);
                    callCounts.put(state, callCounts.get(state) + 1);
                }
            }
        });


        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(JUnit5ParametrizedExample.class));
        LauncherDiscoveryRequest discoveryRequest = builder.build();

        TestPlan discover = launcher.discover(discoveryRequest);
        launcher.execute(discoveryRequest);
        Assert.assertEquals(new HashMap<Status, Integer>() {
            {
                put(Status.success, 3); /* Three succedd */
                put(Status.failed, 3);/* Three failed */
            }
        }, callCounts);
    }


    @Test
    public void dynamicTestsHelperGenerateDynamicTests30() {
        Launcher launcher = LauncherFactory.create();
        final LauncherDiscoveryRequestBuilder builder = LauncherDiscoveryRequestBuilder.request();
        builder.selectors(DiscoverySelectorWrapper.selectClass(SomeDynamicTests.class));
        LauncherDiscoveryRequest discoveryRequest = builder.build();
        TestPlan discover = launcher.discover(discoveryRequest);


        List<String> strings = new ArrayList<String>();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void dynamicTestRegistered(TestIdentifier testIdentifier) {
            }

            @Override
            public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
                strings.add(testIdentifier.getUniqueId());
            }
        });

        launcher.execute(discoveryRequest);
    }



    /*  -------------------------------------------- filter tests -------------------------------------------------- */

    @SuppressWarnings("unused")
    public static class Class2BFiltered {
        public void test() {
        }

        protected void test2() {
        }

        void test3() {
        }

        private void test4() {
        }

        static void test5() {
        }

        public void test(int i) {
        }

        protected void test2(int i) {
        }

        void test3(int i) {
        }

        private void test4(int i) {
        }

        static void test5(int i) {
        }

        void test6(Integer i) {
        }

        void test(Integer i, Integer i2) {

        }

        void test11(int i, int i2) {
        }
    }

    @Test
    public void filterTests10() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes().
                addParams().
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test()",
                        "test2()",
                        "test3()"
                )), list
        );
    }

    @Test
    public void filterTests20() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.TYPE).
                addParams(1).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test(1)",
                        "test2(1)",
                        "test3(1)",
                        "test6(1)")),
                list
        );
    }


    @Test
    public void filterTests25() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.TYPE, Integer.class).
                addParams(null, null).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList("test(null, null)")),
                list
        );
    }

    @Test
    public void filterTests26() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.TYPE, Integer.TYPE).
                addParams(null, null).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList("test(null, null)")),
                list
        );
    }

    @Test
    public void filterTests26_5() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.TYPE, Integer.TYPE).
                addParams(1, 2).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test(1, 2)",
                        "test11(1, 2)")),
                list
        );
    }

    @Test
    public void filterTests26_6() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.TYPE, Integer.TYPE).
                addParams(1, 2).
                addParams(1, null).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test(1, 2)",
                        "test(1, null)",
                        "test11(1, 2)")
                ),
                list
        );
    }

    @Test(expected = DynamicTestFormationException.class)
    public void filterTests27() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.TYPE, Integer.class).
                addParams(null, null, null).
                addParams(null, null).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toSet());
    }

    @Test
    public void filterTests301() {
        Collections.sort(
                Arrays.asList(
                        "test6(Integer)[0]<param values are 1:Integer:Integer>",
                        "test2(int)[0]<param values are 1:Integer:Integer>",
                        "test3(int)[0]<param values are 1:Integer:Integer>",
                        "test(int)[0]<param values are 1:Integer:Integer>")
        );
    }


    @Test
    public void filterTests30() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Class2BFiltered()).
                setParamTypes(Integer.class).
                addParams(1).
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test(1)",
                        "test2(1)",
                        "test3(1)",
                        "test6(1)"))

                ,

                list
        );
    }

    private static class SortedMethods {
        void test() {
        }

        void test1() {
        }

        void test3() {
        }

        void test2() {
        }

        void test5() {
        }
    }

    @Test
    public void filterTests40() {
        List<String> list = sortList(DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new SortedMethods()).
                setParamTypes().
                addParams().
                addParams().
                helper().
                stream().map(DynamicTest::getDisplayName).
                collect(Collectors.toList()));

        Assert.assertEquals(
                sortList(Arrays.asList(
                        "test()",
                        "test()",
                        "test1()",
                        "test1()",
                        "test3()",
                        "test3()",
                        "test2()",
                        "test2()",
                        "test5()",
                        "test5()"
                ))
                , list
        );
    }

    /*  -------------------------------------------- consistency checks ------------------------------------------------------- */

    @Test
    public void consistencyСhecks() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(this).
                setParamTypes(Boolean.TYPE, Long.TYPE, Integer.TYPE).
                addParams("", 1L, 21).
                helper().
                stream();
    }

    @Test
    public void consistencyСhecks20() {
        //  DynamicTestsFactory.newInstance().addPoint().
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(this).
                setParamTypes(Boolean.TYPE, Long.TYPE, Integer.TYPE).
                addParams("", 1L, 21).
                checkTestCount(0).
                helper().
                stream();
    }

    /*  -------------------------------------------- checkTestCount ------------------------------------------------- */


    class CLF {
        void test(int i) {
        }

        void test2(int i) {
        }

        void test3(int i) {
        }

        void test4(int i) {
        }

        void test5(int i) {
        }

        void test() {
        }
    }

    @Test
    public void checkTestCount10() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new CLF()).
                setParamTypes(Integer.TYPE).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).checkTestCount(25);

    }

    @Test
    public void checkTestCount12() {
        CLF nexus = new CLF();
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(nexus).
                setParamTypes(Integer.TYPE).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                checkTestCount(25)./* количество для первой точки */
                nop().
                nop().
                nop().
                helper().
                addPoint().
                setNexus(nexus).
                setParamTypes(Integer.TYPE).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                checkTestCount(25)./* количество для второй точки */
                helper().
                checkTestCount(50); /* Общее количество */
    }

    @Test
    public void checkTestCount14() {
        CLF nexus = new CLF();
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(nexus).
                setParamTypes().
                checkTestCount(0)./* количество для первой точки */
                nop().
                nop().
                nop().
                helper().
                addPoint().
                setNexus(nexus).
                setParamTypes(Integer.TYPE).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                addParams(21).
                checkTestCount(25)./* количество для второй точки */
                helper().
                nop().
                nop().
                nop().
                checkTestCount(25); /* Общее количество */
    }

    @Test
    public void checkTestCount15() {
        CLF nexus = new CLF();
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(nexus).
                setParamTypes().
                checkTestCount(0)./* количество для первой точки */
                nop().
                nop().
                nop().
                helper().
                addPoint().
                setNexus(nexus).
                setParamTypes().
                addParams().
                addParams().
                addParams().
                addParams().
                addParams().
                checkTestCount(5)./* количество для второй точки */
                helper().
                nop().
                nop().
                nop().
                checkTestCount(5); /* Общее количество */
    }

    @Test
    public void checkTestCount20() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new CLF()).
                setParamTypes().
                checkTestCount(0);
    }


    /*  --------------------------------------------     addDynamicTest ------------------------------------------- */

    @Test
    public void addDynamicTest10() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new CLF()).
                addDynamicTest("test", () -> {
                }).
                setParamTypes().
                checkTestCount(1);
    }

    @Test
    public void addDynamicTest20() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new CLF()).
                addDynamicTest("test", () -> {
                }).
                addDynamicTest("test", () -> {
                }).
                addDynamicTest("test1", () -> {
                }).
                addDynamicTest("test2", () -> {
                }).
                addDynamicTest("test3", () -> {
                }).
                addDynamicTest("test4", () -> {
                }).
                setParamTypes().
                checkTestCount(6);
    }

    class Clazz {
        void test(int i) {
            throw new IllegalStateException();
        }

        void test2(int i) {
        }

        void test3(int i) {
        }
    }


    @Test
    public void addDynamicTest30() {
        DynamicTestsFactory.newInstance().
                addPoint().
                nop().
                nop().
                nop().
                addDynamicTest("test", () -> {
                }).
                addDynamicTest("test", () -> {
                }).
                addDynamicTest("test1", () -> {
                }).
                addDynamicTest("test2", () -> {
                }).
                addDynamicTest("test3", () -> {
                }).
                addDynamicTest("test4", () -> {
                }).
                checkTestCount(6);
    }

    @Test
    public void addDynamicTest40() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(Integer.TYPE).
                addParams(1).
                addParams(2).
                addDynamicTest("test", () -> {
                }).
                addDynamicTest("test", () -> {
                }).
                addDynamicTest("test1", () -> {
                }).
                addDynamicTest("test2", () -> {
                }).
                addDynamicTest("test3", () -> {
                }).
                addDynamicTest("test4", () -> {
                }).
                helper().
                nop().
                nop().
                nop().
                checkTestCount(12);
    }

    @Test
    public void addDynamicTest50() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(Integer.TYPE).
                addParams(1).
                addParams(2).
                addDynamicTest(DynamicTest.dynamicTest("test", () -> {
                })).
                addDynamicTest(DynamicTest.dynamicTest("test", () -> {
                })).
                addDynamicTest(DynamicTest.dynamicTest("test1", () -> {
                })).
                addDynamicTest("test2", () -> {
                }).
                addDynamicTest("test3", () -> {
                }).
                addDynamicTest("test4", () -> {
                }).
                helper().
                nop().
                nop().
                nop().
                checkTestCount(12);
    }

    @Test
    public void addDynamicTest60() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(Integer.TYPE).
                addParams(1).
                addParams(2).
                addDynamicTest(DynamicTest.dynamicTest("test", () -> {
                }), DynamicTest.dynamicTest("test", () -> {
                })).
                addDynamicTest(DynamicTest.dynamicTest("test1", () -> {
                })).
                addDynamicTest("test2", () -> {
                }).
                addDynamicTest("test3", () -> {
                }).
                addDynamicTest("test4", () -> {
                }).
                helper().
                nop().
                nop().
                nop().
                checkTestCount(12);
    }


    /*  -------------------------------------------- addParamSet ---------------------------------------------- */
    @Test
    public void addParamSet10() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(Integer.TYPE).
                addParamSet(Arrays.asList(new Object[]{2}, new Object[]{2}, new Object[]{2})).
                checkTestCount(9);
    }


    @Test
    public void addParamSet20() {
        DynamicTestsFactory.newInstance().
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(Integer.TYPE).
                addParamSet(new Object[][]{new Object[]{2}, new Object[]{2}}).
                checkTestCount(6);
    }



    /*  -------------------------------------------- methodWithParams ---------------------------------------------- */


    private static final LazyEval<MethodCaller> methodWithParams =
            LazyEval.eval(() ->
                    new BaseMethodCaller(findMethod(Level.PRIVATE, DynamicTestUtils.class, "methodWithParams",
                            String.class, Method.class, List.class, Integer.TYPE)));


    private String methodWithParams(Method method, List<Object> params, int position) {
        return (String) methodWithParams.value().invoke(null, method, params, position);
    }


    @Test(expected = NullPointerException.class)
    public void methodWithParams10() {
        methodWithParams(null, null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void methodWithParams20() {
        methodWithParams(null, Arrays.asList("1", 2L, 3), 1);
    }

    @Test
    public void methodWithParams30() {
        Method method = findMethod(Level.PRIVATE, DynamicTestUtils.class, "methodWithParams",
                String.class, Method.class, List.class, Integer.TYPE);

        Assert.assertEquals("methodWithParams(\"1\", 2L, 3)",
                methodWithParams(method, Arrays.asList("1", 2L, 3), 12));
    }

    @Test
    public void methodWithParams40() {
        Method method = findMethod(Level.PRIVATE, DynamicTestUtils.class, "methodWithParams",
                String.class, Method.class, List.class, Integer.TYPE);

        List<Object> params = Arrays.asList(
                false, Boolean.TRUE,
                (byte) 1, new Byte((byte) 2),
                (short) 3, new Short((short) 4),
                (int) 5, new Integer((int) 6),
                (long) 7, new Long((long) 8),
                (float) 9, new Float((float) 10),
                (double) 11, new Double((double) 12),
                (char) 'c', new Character((char) 'C'),
                "String"
        );

        Assert.assertEquals(
                "methodWithParams(false, true, 1, 2, 3, 4, 5, 6, 7L, 8L, 9.0, 10.0, 11.0, 12.0, 'c', 'C', \"String\")",
                methodWithParams(method, params, 1)
        );
    }

    /*  ------------------------------ DynamicTestsFactory.newInstance(testInstanceFactory) ------------------------ */

    @Test
    public void newInstanceWithFactory10() {
        TestInstanceFactory testInstanceFactory = new TestInstanceFactory() {
            @Override
            public DynamicTest newDynamicTest(String displayName, Executable executable) {
                return DefaultTestFactory.DEFAULT_FACTORY.newDynamicTest(displayName, executable);
            }
        };

        Stream<DynamicTest> stream = DynamicTestsFactory.newInstance(testInstanceFactory).
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(int.class).
                addParams(1).
                addParams(2).
                addParams(3).
                helper().
                checkTestCount(9).
                stream();

        stream.forEach((i) -> CommonUtils.skipAllExceptions(() -> i.getExecutable().execute()));
    }

    @Test
    public void newInstanceWithFactory20() {


        class ExecutableZ implements Executable {
            final Executable nex;

            ExecutableZ(Executable nex) {
                this.nex = Objects.requireNonNull(nex);
            }

            @Override
            public final void execute() throws Throwable {
                try {
                    nex.execute();
                    success();
                } catch (Throwable throwable) {
                    failure();
                    throw  throwable;
                }
            }

            public void success() {
            }

            public void failure() {
            }
        }


        AtomicInteger successfull = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        TestInstanceFactory testInstanceFactory = new TestInstanceFactory() {
            @Override
            public DynamicTest newDynamicTest(String displayName, Executable executable) {
                return DynamicTest.dynamicTest(displayName, new ExecutableZ(executable) {
                    @Override
                    public void success() {
                        successfull.incrementAndGet();
                    }

                    @Override
                    public void failure() {
                        failed.incrementAndGet();
                    }
                });
            }
        };

        Stream<DynamicTest> stream = DynamicTestsFactory.newInstance(testInstanceFactory).
                addPoint().
                setNexus(new Clazz()).
                setParamTypes(int.class).
                addParams(1).
                addParams(2).
                addParams(3).
                helper().
                checkTestCount(9).
                stream();

        stream.forEach((i) -> CommonUtils.skipAllExceptions(() -> i.getExecutable().execute()));

        /* таким образом мы проверяем что работает наша фабрика динамических тестов. */
        Assert.assertEquals(6,  successfull.get());
        Assert.assertEquals(3,  failed.get());
    }


    @Test
    public void newInstanceWithFactory30() {


        class ExecutableZ implements Executable {
            final Executable nex;

            ExecutableZ(Executable nex) {
                this.nex = Objects.requireNonNull(nex);
            }

            @Override
            public final void execute() throws Throwable {
                try {
                    nex.execute();
                    success();
                } catch (Throwable throwable) {
                    failure();
                    throw  throwable;
                }
            }

            public void success() {
            }

            public void failure() {
            }
        }


        AtomicInteger successfull = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        TestInstanceFactory testInstanceFactory = new TestInstanceFactory() {
            @Override
            public DynamicTest newDynamicTest(String displayName, Executable executable) {
                return DynamicTest.dynamicTest(displayName, new ExecutableZ(executable) {
                    @Override
                    public void success() {
                        successfull.incrementAndGet();
                    }

                    @Override
                    public void failure() {
                        failed.incrementAndGet();
                    }
                });
            }
        };

        Stream<DynamicTest> stream = DynamicTestsFactory.newInstance(testInstanceFactory).
                addPoint().
                addDynamicTest("qweqweqweeqw", () -> {

                }).
                addDynamicTest("qweqweqweeqw", () -> {
                   throw new IllegalStateException();
                }).
                checkTestCount(2).
                helper().
                checkTestCount(2).
                stream();

        stream.forEach((i) -> CommonUtils.skipAllExceptions(() -> i.getExecutable().execute()));

        /* таким образом мы проверяем что работает наша фабрика динамических тестов. */
        Assert.assertEquals(1,  successfull.get());
        Assert.assertEquals(1,  failed.get());
    }




    /*  -------------------------------------------- privates ------------------------------------------------------- */

    private static Status calcState(TestExecutionResult result) {
        TestExecutionResult.Status status = result.getStatus();
        switch (status) {
            case ABORTED: {
                return Status.aborted;
            }

            case FAILED: {
                return Status.failed;
            }

            case SUCCESSFUL: {
                return Status.success;
            }

            default: {
                throw new IllegalStateException("unknown state");
            }
        }
    }


    private static <T> List<T> sortList(List<T> list) {
        Objects.requireNonNull(list);
        List<T> result = new ArrayList<>(list);
        result.sort(null);
        return result;
    }

}
