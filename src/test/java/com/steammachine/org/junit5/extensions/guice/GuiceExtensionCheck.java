package com.steammachine.org.junit5.extensions.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.steammachine.org.junit5.extensions.guice.GuiceExtension;
import com.steammachine.org.junit5.extensions.guice.GuiceModules;
import org.junit.Assert;
import org.junit.Test;
import com.steammachine.org.junit5.extensions.guice.implementation.ver1.GuiceJUnit5AssistantVersion1;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;


/**
 *
 * @author Vladimir Bogodukhov
 */
public class GuiceExtensionCheck {

    private static final List<Integer> VERSIONS = Arrays.asList(0, 1);

    /**
     * Вспомогательный интерфейс - обертка вокруг исполняемого кода.
     */
    @FunctionalInterface
    public interface Code {
        /**
         * @throws Throwable ошибка выполнения
         */
        void execute() throws Throwable;
    }

    private static final class GuiceJUnitRunnerAssistan extends GuiceJUnit5AssistantVersion1 {
    }

    public static final class Mod implements Module {

        public Mod() {
        }

        @Override
        public void configure(Binder binder) {
        }
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_1")
    public static class Test1 {
        @Test
        public void test() {
        }
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_2")
    public static class Test2 {
        @Test
        public void test() {
        }
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_3")
    public static class Test3 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_4")
    public static class Test4 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_5")
    public static class Test5 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_6")
    public static class Test6 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_7")
    public static class Test7 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_6")
    public static class Test8 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_9")
    public static class Test9 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_10")
    public static class Test10 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_11")
    public static class Test11 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_12")
    public static class Test12 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_13")
    public static class Test13 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_14")
    public static class Test14 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_15")
    public static class Test15 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_16")
    public static class Test16 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_17")
    public static class Test17 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_18")
    public static class Test18 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_19")
    public static class Test19 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_20")
    public static class Test20 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_31")
    public static class Test31 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_32")
    public static class Test32 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_33")
    public static class Test33 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_34")
    public static class Test34 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_35")
    public static class Test35 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_36")
    public static class Test36 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_37")
    public static class Test37 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_38")
    public static class Test38 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_39")
    public static class Test39 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_40")
    public static class Test40 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_41")
    public static class Test41 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_42")
    public static class Test42 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_43")
    public static class Test43 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_45")
    public static class Test44 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_46")
    public static class Test45 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_47")
    public static class Test46 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_48")
    public static class Test47 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_49")
    public static class Test48 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_50")
    public static class Test49 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_51")
    public static class Test50 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_51")
    public static class Test51 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_52")
    public static class Test52 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_53")
    public static class Test53 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_54")
    public static class Test54 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_55")
    public static class Test55 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_56")
    public static class Test56 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_57")
    public static class Test57 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_58")
    public static class Test58 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_59")
    public static class Test59 extends Test1 {
    }


    @GuiceModules(value = {Mod.class}, space = "LA VELOCIDAD_60")
    public static class Test60 extends Test1 {
    }


    public static final List<? extends Class> CLASS_LIST = Arrays.asList(
            Test1.class, Test2.class, Test3.class, Test4.class, Test5.class, Test6.class, Test7.class, Test8.class, Test9.class, Test10.class,
            Test11.class, Test12.class, Test13.class, Test4.class, Test5.class, Test6.class, Test7.class, Test8.class, Test9.class, Test10.class,
//            Test21.class, Test22.class, Test23.class, Test24.class, Test25.class, Test26.class, Test27.class, Test28.class, Test29.class, Test30.class
            Test31.class, Test32.class, Test43.class, Test44.class, Test45.class, Test46.class, Test47.class, Test48.class, Test49.class, Test40.class,
            Test41.class, Test42.class, Test43.class, Test44.class, Test45.class, Test46.class, Test47.class, Test48.class, Test49.class, Test50.class,
            Test51.class, Test52.class, Test53.class, Test54.class, Test55.class, Test56.class, Test57.class, Test58.class, Test59.class, Test60.class


    );

    @Test
    public void testGuiceExtensionName() {
        Assert.assertEquals("GuiceExtension",
                GuiceExtension.class.getName());
    }

    @Test
    public void testJunit5GuiceName() {
        Assert.assertEquals(true, VERSIONS.contains(GuiceExtension.VERSION));
    }

    @Test
    public void test() throws Throwable {
        for (Class clazz : CLASS_LIST) {
            GuiceJUnitRunnerAssistan.getInjector(clazz);
        }

        long measureTime = measureTime(new Code() {
            @Override
            public void execute() throws Throwable {
                GuiceJUnitRunnerAssistan.getInjector(Test1.class);
            }
        }, 1_000_000);
        System.out.println(measureTime);
        Assert.assertEquals("measureTime > 10000 --> " + measureTime, true, measureTime < 10000);
    }


    @Test
    public void test2() throws Throwable {
        for (Class clazz : CLASS_LIST) {
            GuiceJUnitRunnerAssistan.getInjector(clazz);
        }

        final CountDownLatch latch = new CountDownLatch(1);
        final Runnable target = new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i = 0; i < 100; i++) {
                        GuiceJUnitRunnerAssistan.getInjector(Test1.class);
                    }
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        };


        long measureTime = measureTime(() -> {
            Thread[] threads = new Thread[50];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(target);
            }
            for (Thread thread : threads) {
                thread.start();
            }
            latch.countDown(); /*  запускаем все ожидающие потоки на выполнение */
            for (Thread thread : threads) {
                thread.join();
            }
        }, 100);
        Assert.assertEquals("measureTime > 60000 --> " + measureTime, true, measureTime < 60000);
    }


    /**
     * измеряет время выполнения переданного кода
     *
     * @param code  - код
     * @param count количество выполнений кода
     * @return время выполнения
     * @throws Throwable - любая ошибка внутри выполняемого кода
     */
    private static long measureTime(Code code, int count) throws Throwable {
        Objects.requireNonNull(code, "code is null");
        if (count <= 0) {
            throw new IllegalArgumentException("count must be a positive number");
        }
        long l = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            code.execute();
        }
        return System.currentTimeMillis() - l;
    }


}