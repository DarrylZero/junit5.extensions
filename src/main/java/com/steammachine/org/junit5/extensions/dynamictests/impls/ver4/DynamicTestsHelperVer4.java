package com.steammachine.org.junit5.extensions.dynamictests.impls.ver4;

import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestsHelper;
import com.steammachine.org.junit5.extensions.dynamictests.TestInstanceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import com.steammachine.org.junit5.extensions.dynamictests.DefaultTestFactory;

import java.util.*;
import java.util.stream.Stream;

/**
 * Тут описывается вспомогательный класс для быстрого формирования
 * аналогов параметризированных тестов в JUnit5.
 * <p>
 * <p>
 * <p>
 * <p>
 * Created 20/10/16 16:46
 *
 * @author Vladimir Bogodukhov
 **/
public class DynamicTestsHelperVer4 implements DynamicTestsHelper {

    public static final int VERSION = Version4.CURRENT_VERSION;

    private List<DynamicPointVer4> points = new ArrayList<>();
    private List<DynamicTest> dynamicTests;
    private final TestInstanceFactory testInstanceFactory;

    public DynamicTestsHelperVer4(TestInstanceFactory testInstanceFactory) {
        this.testInstanceFactory = Objects.requireNonNull(testInstanceFactory);
    }

    public DynamicTestsHelperVer4() {
        this.testInstanceFactory = DefaultTestFactory.DEFAULT_FACTORY;
    }

    @Override
    public DynamicPointVer4 addPoint() {
        DynamicPointVer4 point = new DynamicPointVer4(this);
        points.add(point);
        changed();
        return point;
    }

    @Override
    public DynamicTestsHelperVer4 nop() {
        return this;
    }

    public DynamicTestsHelperVer4 check() {
        tests();
        return this;
    }

    public void changed() {
        dynamicTests = null;
    }


    public DynamicTestsHelperVer4 checkTestCount(int expectedCount) {
        int actualSize = tests().size();
        Assertions.assertEquals(expectedCount, tests().size(),
                () -> "expected test count " + expectedCount + " is not equal to actual test count " +
                        actualSize);
        return this;
    }


    @Override
    public Stream<DynamicTest> stream() {
        return tests().stream();
    }

    @Override
    public Collection<DynamicTest> collection() {
        return tests();
    }

    @Override
    public Iterable<DynamicTest> iterable() {
        return tests();
    }

    @Override
    public Iterator<DynamicTest> iterator() {
        return tests().iterator();
    }

    @Override
    public int version() {
        return Version4.CURRENT_VERSION;
    }

    public TestInstanceFactory testInstanceFactory() {
        return testInstanceFactory;
    }

    /* ------------------------------------------------- privates -------------------------------------------------  */

    private List<DynamicTest> tests() {
        if (dynamicTests != null) {
            return dynamicTests;
        }

        List<DynamicTest> generate = new ArrayList<>();
        for (DynamicPointVer4 point : this.points) {
            generate.addAll(point.tests());
        }
        dynamicTests = generate;
//        this.dynamicTests = this.points.stream().map((i) -> tests()).reduce(emptyList(), DynamicTestsHelperVer4::reduceLists);
        return dynamicTests;
    }

    private static List<DynamicTest> reduceLists(List<DynamicTest> dt1, List<DynamicTest> dt2) {
        List<DynamicTest> list = new ArrayList<>();
        list.addAll(dt1);
        list.addAll(dt2);
        return list;
    }

}
