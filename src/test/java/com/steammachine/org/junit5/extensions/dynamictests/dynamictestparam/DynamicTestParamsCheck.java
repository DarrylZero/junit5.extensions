package com.steammachine.org.junit5.extensions.dynamictests.dynamictestparam;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.steammachine.org.junit5.extensions.dynamictests.dynamictestparam.DynamicTestParams.test;

/**
 * {@link DynamicTestParams#dynamicTests(Function)}
 * {@link DynamicTestParams#dynamicTests(Function, Predicate)}
 * {@link DynamicTestParams#dynamicTests(Function, Function, BiFunction, Predicate)}
 * {@link DynamicTestParams#lock()}
 * <p>
 *
 * @author Vladimir Bogodukhov
 */
public class DynamicTestParamsCheck {

    private static final Executable DUMMY_EXEC = () -> {
    };

    private static class Param {
        private final String onlyData;

        Param(String onlyData) {
            this.onlyData = onlyData;
        }
    }

    @Test
    public void testFullName() {
        Assert.assertEquals("com.steammachine.org.junit5.extensions.dynamictests.dynamictestparam.DynamicTestParams",
                DynamicTestParams.class.getName());
    }

    @Test
    public void mainFunctionality() {
        DynamicTestParams<Param> testParams = DynamicTestParams.of(new Param("0")).
                param(new Param("1")).
                params(new Param("2"), new Param("3"), new Param("4"));

        List<String> collectedParams = testParams.
                paramsData().map(p -> p.onlyData).collect(Collectors.toList());

        Assert.assertEquals(
                Arrays.asList("0", "1", "2", "3", "4"),
                collectedParams
        );
        testParams.paramsData();
    }

    @Test
    public void checkTestNamesUniquiness10() {
        DynamicTestParams<Param> testParams = DynamicTestParams.of(new Param("0")).
                param(new Param("1")).
                params(new Param("2"), new Param("3"), new Param("4"));

        testParams.checkTestNamesUniquiness(true);
        testParams.lock().dynamicTests(p -> DynamicTest.dynamicTest(p.onlyData, DUMMY_EXEC));
    }

    @Test(expected = IllegalStateException.class)
    public void checkTestNamesUniquiness20() {
        DynamicTestParams<Param> testParams = DynamicTestParams.of(new Param("0")).
                param(new Param("2")).
                params(new Param("2"), new Param("3"), new Param("4"));

        testParams.checkTestNamesUniquiness(true);
        testParams.lock().dynamicTests(p -> DynamicTest.dynamicTest(p.onlyData, DUMMY_EXEC));
    }

    @Test(expected = IllegalStateException.class)
    public void checkTestNamesUniquiness40() {
        DynamicTestParams<Param> testParams = DynamicTestParams.of(new Param("0")).
                param(new Param("2")).
                params(new Param("2"), new Param("3"), new Param("4"));

        /*testParams.checkTestNamesUniquiness(true); Проверка что по умолчению режим проверки включен */
        testParams.lock().dynamicTests(p -> DynamicTest.dynamicTest(p.onlyData, DUMMY_EXEC));
    }

    @Test
    public void checkTestNamesUniquiness50() {
        DynamicTestParams<Param> testParams = DynamicTestParams.of(new Param("0")).
                param(new Param("2")).
                params(new Param("2"), new Param("3"), new Param("4"));

        testParams.checkTestNamesUniquiness(false).lock();
        testParams.dynamicTests(p -> DynamicTest.dynamicTest(p.onlyData, DUMMY_EXEC));
    }

    @Test
    public void checkLock10() {
        DynamicTestParams.of(new Param("0")).lock().dynamicTests(p -> DynamicTest.dynamicTest(p.onlyData, DUMMY_EXEC));
    }

    @Test(expected = IllegalStateException.class)
    public void checkLock20() {
        DynamicTestParams.of(new Param("0")).
                lock().
                checkTestNamesUniquiness(false). /* Тут изменяем после вызова lock() */
                dynamicTests(p -> DynamicTest.dynamicTest(p.onlyData, DUMMY_EXEC));
    }


    @Test
    public void paramsData10() {
        DynamicTestParams.of(new Param("0")).lock().paramsData();
    }


    /* ------------------------------------ code order -------------------------------------------------------------- */

    @Test
    public void codeOrder10() {
        Assert.assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
                DynamicTestParams.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14).
                        lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void codeOrder20() {
        Assert.assertEquals(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
                DynamicTestParams.of(1, 2, 3, 4).params(5, 6, 7, 8, 9).params(10, 11, 12).params(13, 14).
                        lock().paramsData().collect(Collectors.toList())
        );
    }

    /* ------------------------------------ generators -------------------------------------------------------- */

    @Test
    public void generator10() {
        Assert.assertEquals(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
                DynamicTestParams.of(0).generator(() -> Stream.iterate(1, i -> i + 1).limit(14)).
                        lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void generator20() {
        Assert.assertEquals(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
                DynamicTestParams.ofType(Integer.class).generator(() -> Stream.iterate(0, i -> i + 1).limit(15)).
                        lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void generator30() {
        Assert.assertEquals(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
                DynamicTestParams.ofType(Integer.class).generator(() -> Stream.iterate(0, i -> i + 1).limit(15)).
                        lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void generator40() {
        Assert.assertEquals(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
                DynamicTestParams.ofType(Integer.class).
                        generator(() -> Stream.iterate(0, i -> i + 1).limit(5)).
                        generator(() -> Stream.iterate(5, i -> i + 1).limit(5)).
                        generator(() -> Stream.iterate(10, i -> i + 1).limit(5)).
                        lock().paramsData().collect(Collectors.toList())
        );
    }


    /* ------------------------------------ null params ------------------------------------------------------ */

    @Test
    public void nullParams10() {
        Assert.assertEquals(
                Arrays.asList(1, null, 3),
                DynamicTestParams.of(1, null, 3).lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void nullParams20() {
        Assert.assertEquals(
                Arrays.asList(1, null, 3),
                DynamicTestParams.ofType(Integer.class).params(1, null, 3).lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void nullParams30() {
        Assert.assertEquals(
                Arrays.asList(1, null, 3),
                DynamicTestParams.of(1).params(null, 3).lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void nullParams40() {
        Assert.assertEquals(
                Arrays.asList(1, null, 3),
                DynamicTestParams.of(1, null).params(3).lock().paramsData().collect(Collectors.toList())
        );
    }

    @Test
    public void nullParams50() {
        Assert.assertEquals(
                Arrays.asList(null, 2, 3),
                DynamicTestParams.of((Integer) null).params(2, 3).lock().paramsData().collect(Collectors.toList())
        );
    }

    /* ----------------------------------------------------------------------------------------------------------- */

    @Test(expected = IllegalStateException.class)
    public void testGroup() {
        List<Integer> integers = new ArrayList<>();
        DynamicTestParams.ofType(Integer.class).params(1, 2, 3, 4, 5, 6).
                testGroup("Test1", (s, i) -> s + i, test(i -> () -> integers.add(i)),  i -> true).
                testGroup("Test1", (s, i) -> s + i, test(i -> () -> integers.add(i)),  i -> true);

    }

//    @Test
//    public void testGroup20() {
//        List<Integer> integers = new ArrayList<>();
//        DynamicTestParams.ofType(Integer.class).params(1, 2, 3, 4, 5, 6).
//                testGroup("Test1", (s, i) -> s + i, test(i -> () -> integers.add(i)),i -> true).
//                testGroup("Test2", (s, i) -> s + i, test(i -> () -> integers.add(i * 2)), i -> true).
//                lock().form().forEachOrdered(dt -> CommonUtils.suppressAllWOResult(() -> dt.getExecutable().execute()));
//        Assert.assertEquals(
//                Arrays.asList(1, 2, 3, 4, 5, 6, 2, 4, 6, 8, 10, 12),
//                integers
//        );
//    }

//
//    @Test
//    public void testGroup30() {
//        List<Integer> integers = new ArrayList<>();
//        DynamicTestParams.ofType(Integer.class).params(1, 2, 3, 4, 5, 6).
//                testGroup("Test1", (s, i) -> s + i, test(i -> () -> integers.add(i)), i -> true).
//                testGroup("Test2", (s, i) -> s + i, test(i -> () -> integers.add(i * 2)), i -> true).
//                lock().form().forEachOrdered(dt -> CommonUtils.suppressAllWOResult(() -> dt.getExecutable().execute()));
//        Assert.assertEquals(
//                Arrays.asList(1, 2, 3, 4, 5, 6, 2, 4, 6, 8, 10, 12),
//                integers
//        );
//    }

/* ---------------------------------------------- copyParams ------------------------------------------------ */

//    @Test
//    public void copyParams10() {
//        List<Integer> integers = new ArrayList<>();
//        DynamicTestParams.ofType(Integer.class).params(1, 2, 3).
//                lock( ).
//                copyParams().params(4, 5, 6).
//                testGroup("Test1", (s, i) -> s + i, test(i -> () -> integers.add(i)), i -> true).lock().form().
//                map(DynamicTest::getExecutable).forEachOrdered(dt -> CommonUtils.suppressAllWOResult(dt::execute));
//
//        Assert.assertEquals(
//                Arrays.asList(1, 2, 3, 4, 5, 6),
//                integers
//        );
//    }
}