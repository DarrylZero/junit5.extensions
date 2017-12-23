package com.steammachine.org.junit5.extensions.testresult.implementation;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.socialquantum.common.utils.commonutils.CommonUtils;
import com.steammachine.org.junit5.extensions.testresult.callbacks.CallBack;
import com.steammachine.org.junit5.extensions.testresult.callbacks.CommonCallBacks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vladimirbogoduhov on 19/12/16.
 *
 * @author Vladimir Bogoduhov
 */
public class CommonCallBacksTestCheck {

    @Test
    public void testName() {
        Assert.assertEquals("CommonCallBacks",
                CommonCallBacks.class.getName());
    }

    private static class TypeToTest {
        @Override
        public String toString() {
            return "TypeToTest";
        }
    }

    @Test
    public void test1() {
        CommonCallBacks commonCallBacks = new CommonCallBacks();

        Set<String> strings = new HashSet<>();
        CallBack<TypeToTest> callBack = new CallBack<TypeToTest>() {
            @Override
            public void railTestSuccess(TypeToTest info) {
                strings.add("railTestSuccess" + info);
            }

            @Override
            public void railTestFailure(TypeToTest info, Throwable throwable) {
                strings.add("railTestFailure" + info);
            }
        };

        commonCallBacks.register(TypeToTest.class, callBack);
        commonCallBacks.fireFailure(TypeToTest.class, new TypeToTest(), new Throwable());
        commonCallBacks.fireSuccess(TypeToTest.class, new TypeToTest());

        Assert.assertEquals(
                new HashSet<>(Arrays.asList("railTestSuccessTypeToTest", "railTestFailureTypeToTest")),
                strings);
    }

    @FunctionalInterface
    private interface SuccessCallBack extends CallBack<TypeToTest> {
        void railTestSuccess(TypeToTest info);
    }

    @Test
    public void testSpeed10() throws Exception {
        CommonCallBacks commonCallBacks = new CommonCallBacks();

        for (int i = 0; i < 1000; i++) {
            commonCallBacks.register(TypeToTest.class, (SuccessCallBack) info -> {
                Assert.assertTrue(true);
            });
        }


        TypeToTest info = new TypeToTest();

        long measureTime = CommonUtils.measureTime(() -> {
            commonCallBacks.fireSuccess(TypeToTest.class, info);
        }, 100_000);

        Assert.assertTrue(measureTime < 10000);


        commonCallBacks.unregisterForKey(TypeToTest.class);
    }

    @Test
    public void testSpeed20() throws Exception {
        CommonCallBacks commonCallBacks = new CommonCallBacks();

        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < 1000; i++) {
            commonCallBacks.register(TypeToTest.class, new SuccessCallBack() {
                @Override
                public void railTestSuccess(TypeToTest info) {
                    commonCallBacks.unregister(TypeToTest.class, this);
                    count.incrementAndGet();
                }
            });
        }


        TypeToTest info = new TypeToTest();

        long measureTime = CommonUtils.measureTime(() -> {
            commonCallBacks.fireSuccess(TypeToTest.class, info);
        }, 100_000);

        Assert.assertEquals(1000, count.get());
        Assert.assertTrue(measureTime < 4000);


        commonCallBacks.unregisterForKey(TypeToTest.class);
    }

    @Test
    @Ignore
    public void testMultyThread10() throws Exception {
        CommonCallBacks commonCallBacks = new CommonCallBacks();

        AtomicInteger count = new AtomicInteger();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    commonCallBacks.register(TypeToTest.class, new SuccessCallBack() {
                        @Override
                        public void railTestSuccess(TypeToTest info) {
                            count.incrementAndGet();
                            commonCallBacks.unregister(TypeToTest.class, this);
                        }
                    });
                }
            }
        });


        TypeToTest info = new TypeToTest();
        thread.start();
        long measureTime = CommonUtils.measureTime(() -> {
            commonCallBacks.fireSuccess(TypeToTest.class, info);
        }, 100_000);

        thread.join();
        Assert.assertEquals(10_000, count.get());
        Assert.assertTrue(measureTime < 4000);
    }


}