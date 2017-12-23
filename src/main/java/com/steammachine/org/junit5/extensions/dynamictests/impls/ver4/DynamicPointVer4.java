package com.steammachine.org.junit5.extensions.dynamictests.impls.ver4;

import com.steammachine.org.junit5.extensions.dynamictests.DynamicTestFormationException;
import com.steammachine.org.junit5.extensions.dynamictests.TestInstanceFactory;
import com.steammachine.org.junit5.extensions.dynamictests.impls.DynamicTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;
import com.steammachine.org.junit5.extensions.dynamictests.DynamicPoint;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created 25/10/16 15:26
 *
 * @author Vladimir Bogodukhov
 **/
public class DynamicPointVer4 implements DynamicPoint {

    private final DynamicTestsHelperVer4 helper;
    private Object nexus;
    private List<Class> paramTypes;
    private final List<Object[]> params = new ArrayList<>();
    private List<DynamicTest> tests;
    private List<DynamicTest> addedDynamicTests = new ArrayList<>();
    private final TestInstanceFactory testInstanceFactory;

    public DynamicPointVer4(DynamicTestsHelperVer4 helper) {
        this.helper = Objects.requireNonNull(helper);
        this.testInstanceFactory = Objects.requireNonNull(helper.testInstanceFactory());
    }

    @Override
    public int version() {
        return Version4.CURRENT_VERSION;
    }

    @Override
    public DynamicTestsHelperVer4 helper() {
        return helper;
    }

    @Override
    public DynamicPointVer4 setNexus(Object nexus) {
        Objects.requireNonNull(nexus);
        if (this.nexus != nexus) {
            this.nexus = nexus;
            changed();
        }
        return this;
    }

    @Override
    public DynamicPointVer4 setParamTypes(Class<?>... paramTypes) {
        Objects.requireNonNull(paramTypes, " param types must not be null");
        List<Class> types = Arrays.asList(paramTypes);
        types.forEach(i -> Objects.requireNonNull(i, " param types must not contain null values " + types));
        if (!Objects.equals(this.paramTypes, types)) {
            this.paramTypes = types;
            changed();
        }
        return this;
    }

    @Override
    public DynamicPointVer4 addParams(Object... param) {
        params.add(param);
        changed();
        return this;
    }

    @Override
    public DynamicPointVer4 addParamSet(Collection<Object[]> paramSet) {
        Objects.requireNonNull(paramSet);
        for (Object[] params : paramSet) {
            addParams(params);
        }
        return this;
    }

    @Override
    public DynamicPointVer4 addParamSet(Object[]... paramSet) {
        Objects.requireNonNull(paramSet);
        for (Object[] params : paramSet) {
            addParams(params);
        }
        return this;
    }

    @Override
    public DynamicPointVer4 addDynamicTest(DynamicTest... dynamicTest) {
        Objects.requireNonNull(dynamicTest);
        Arrays.asList(dynamicTest).forEach(Objects::requireNonNull);

        Stream.of(dynamicTest).forEach((t) ->
                addedDynamicTests.add(testInstanceFactory.newDynamicTest(t.getDisplayName(), t.getExecutable())) );
        return this;
    }

    @Override
    public DynamicPointVer4 addDynamicTest(String displayName, Executable executable) {
        testInstanceFactory.newDynamicTest(displayName, executable);
        return addDynamicTest(DynamicTest.dynamicTest(displayName, executable));
    }

    @Override
    public DynamicPointVer4 nop() {
        return this;
    }

    @Override
    public DynamicPointVer4 checkTestCount(int expectedCount) {
        int actualSize = tests().size();
        Assertions.assertEquals(expectedCount, tests().size(),
                () -> "expected test count " + expectedCount + " is not equal to actual test count " +
                        actualSize);
        return this;
    }

    private void changed() {
        tests = null;
        helper().changed();
    }

    public String errorReport() {
        if (addedDynamicTests.size() == 0 & nexus == null) {
            return "nexus is not set";
        }

        if (addedDynamicTests.size() == 0 & paramTypes == null) {
            return "param types are not set";
        }


        /* Тут проверим что передаваемые параметры соответствуют типам */
        int j = 0;
        if (paramTypes != null) {
            for (Object[] objects : params) {
                if (paramTypes.size() != objects.length) {
                    return "param types " + paramTypes + " do not correspond at param set no. " + j +
                            Arrays.toString(objects);
                }
                j++;
            }
        }
        return null;
    }


    public List<DynamicTest> tests() {
        if (tests == null) {
            String errorReport = errorReport();
            if (errorReport != null) {
                throw new DynamicTestFormationException(errorReport);
            }
            tests = generate();
        }
        return tests;
    }

    private List<DynamicTest> generate() {
        List<DynamicTest> dynamicTests = new ArrayList<>();
        if (nexus != null) {
            dynamicTests.addAll(DynamicTestUtils.generateAsList(nexus, params, paramTypes, testInstanceFactory));
        }

        dynamicTests.addAll(addedDynamicTests);
        return dynamicTests;
    }

}
