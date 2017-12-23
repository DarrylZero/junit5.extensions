//package com.steammachine.org.junit5.extensions.ignore;
//
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.platform.launcher.Launcher;
//import org.junit.platform.launcher.TestExecutionListener;
//import org.junit.platform.launcher.TestIdentifier;
//import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
//import org.junit.platform.launcher.core.LauncherFactory;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.*;
//
//import static com.steammachine.org.junit5.extensions.ignore.IgnoreExtensionCheck.IgnoreExtensionHack.createClassInstance;
//import static com.steammachine.org.junit5.extensions.ignore.IgnoreExtensionCheck.IgnoreExtensionHack.isStateless;
//
///**
// * Created by vladimirbogoduhov on 30/09/16.
// *
// * @author Vladimir Bogodukhov
// */
//public class IgnoreExtensionCheck {
//
//
//    private static final List<Integer> VERSIONS = Arrays.asList(1, 2, 3);
//
////    /**
////     * Вспомогательный класс для доступа к невидимым методам
////     */
////    public static final class IgnoreExtensionHack {
////
////        private static final LazyEval<MethodCaller> createClassInstance = LazyEval.eval(() ->
////                new BaseMethodCaller(findMethod(Level.PRIVATE, IgnoreExtension.class, "createClassInstance",
////                        IgnoreCondition.class, Class.class, Object.class, String[].class)));
////
////        private static final LazyEval<MethodCaller> fillProperties = LazyEval.eval(() ->
////                new BaseMethodCaller(findMethod(Level.PRIVATE, IgnoreExtension.class, "fillProperties",
////                        null, Object.class, String[].class)));
////
////        private static final LazyEval<MethodCaller> isStateless = LazyEval.eval(() ->
////                new BaseMethodCaller(findMethod(Level.PRIVATE, IgnoreExtension.class, "isStateless",
////                        Boolean.TYPE, Class.class)));
////
////
////        private final IgnoreCondition ignoreCondition;
////
////        public IgnoreExtensionHack(IgnoreCondition ignoreCondition) {
////            this.ignoreCondition = Objects.requireNonNull(ignoreCondition);
////        }
////
////        public IgnoreCondition ignoreCondition() {
////            return ignoreCondition;
////        }
////
////        public static <T extends IgnoreCondition> T createClassInstance(Class<T> clazz, Object testInstance,
////                                                                        String... settings) {
////            return createClassInstance.value().invoke(null, clazz, testInstance, settings);
////        }
////
////        public static boolean isStateless(Class<?> clazz) {
////            return isStateless.value().invoke(null, clazz);
////        }
////
////    }
//
//
//    @Test
//    public void checkVersion() {
//        Assert.assertTrue(VERSIONS.contains(IgnoreExtension.VERSION));
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void isStateless10() {
//        isStateless(null);
//    }
//
//    @Test
//    public void isStateless20() {
//        Assert.assertEquals(false, IgnoreExtensionHack.isStateless(Object.class));
//    }
//
//    private static class Stat {
//
//    }
//
//    @Test
//    public void isStateless30() {
//        Assert.assertEquals(false, IgnoreExtensionHack.isStateless(Stat.class));
//    }
//
//    private static class StatAnc extends Stat {
//
//    }
//
//    @Test
//    public void isStateless40() {
//        Assert.assertEquals(false, IgnoreExtensionHack.isStateless(StatAnc.class));
//    }
//
//    @Cached(false)
//    private static class S3 extends StatAnc {
//
//    }
//
//    @Test
//    public void isStateless50() {
//        Assert.assertEquals(false, IgnoreExtensionHack.isStateless(S3.class));
//    }
//
//    @Cached(true)
//    private static class S4 extends S3 {
//
//    }
//
//    @Test
//    public void isStateless60() {
//        Assert.assertEquals(true, IgnoreExtensionHack.isStateless(S4.class));
//    }
//
//    @Cached(false)
//    private static class S5 extends S4 {
//
//    }
//
//    @Test
//    public void isStateless70() {
//        Assert.assertEquals(false, IgnoreExtensionHack.isStateless(S5.class));
//    }
//
//
//    private static class SkippedListener implements TestExecutionListener {
//        public final Set<String> names = new HashSet<>();
//
//        @Override
//        public void executionSkipped(TestIdentifier id, String reason) {
//            if (id.isTest()) {
//                names.add(new MethodSourceWrapper(id.getSource().orElseThrow(IllegalStateException::new)).methodName());
//            }
//        }
//    }
//
//    /* ---------------------------------------------- integration ------------------------------------------------ */
//
//    @Test
//    public void integration10() {
//        Launcher launcher = LauncherFactory.create();
//        SkippedListener listener = new SkippedListener();
//        launcher.registerTestExecutionListeners(listener);
//        launcher.execute(
//                LauncherDiscoveryRequestBuilder.request().
//                        selectors(DiscoverySelectorWrapper.selectClass(IgnoredConditionExample.class)).build());
//
//        Assert.assertEquals(
//                new HashSet<>(Arrays.asList(
//                        "test",
//                        "test2",
//                        "test3",
//                        "test4",
//                        "test5",
//                        "test6",
//                        "test7",
//                        "test8",
//                        "test9"
//                )),
//                listener.names);
//
//    }
//
//    /* ---------------------------------------------- createClassInstance ------------------------------------------ */
//
//    public class Contained implements IgnoreCondition {
//        @Override
//        public boolean evaluate() throws Exception {
//            return false;
//        }
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void createClassInstance10() {
//        createClassInstance(Contained.class, null, new String[]{});
//    }
//
//    @Test
//    public void createClassInstance20() {
//        Contained invoke = createClassInstance(Contained.class, this, new String[]{});
//    }
//
//    public static class Contained2 implements IgnoreCondition {
//        @Override
//        public boolean evaluate() throws Exception {
//            return false;
//        }
//    }
//
//    @Test
//    public void createClassInstance30() {
//        createClassInstance(Contained2.class, null, new String[]{});
//    }
//
//    @Test
//    public void createClassInstance40() {
//        Contained2 invoke = createClassInstance(Contained2.class, this, new String[]{});
//    }
//
//    @Test
//    public void createClassInstance60() {
//        IgnoreCondition2345671 invoke = createClassInstance(IgnoreCondition2345671.class, this, new String[]{});
//    }
//
//
//    @Test
//    public void createClassInstance70() {
//        IgnoreCondition2345671 invoke = createClassInstance(IgnoreCondition2345671.class, this, new String[]{});
//    }
//
//    @Test
//    public void createClassInstance80() {
//
//        class MethodLocal implements IgnoreCondition {
//
//            public MethodLocal() {
//            }
//
//            @Override
//            public boolean evaluate() throws Exception {
//                return false;
//            }
//        }
//        MethodLocal invoke = createClassInstance(MethodLocal.class, this);
//    }
//
//    @Test
//    public void createClassInstance90() {
//
//        class MethodLocal implements IgnoreCondition {
//
//            public void setI(int i) {
//            }
//
//            public MethodLocal() {
//            }
//
//            @Override
//            public boolean evaluate() throws Exception {
//                return false;
//            }
//        }
//        MethodLocal invoke = createClassInstance(MethodLocal.class, this, "i=15");
//    }
//
//    @Test
//    public void createClassInstance100() {
//
//        ConditionWithProperties template = new ConditionWithProperties();
//        template.setNullableinteger(null);
//        template.setStr("str");
//        template.setBool(false);
//        template.setBoolO(true);
//        template.setB((byte) 1);
//        template.setBo((byte) 2);
//        template.setS((short) 3);
//        template.setSo((short) 4);
//        template.setI(5);
//        template.setIo(6);
//        template.setL(7);
//        template.setLo(8L);
//        String[] settings = {
//                "nullableinteger->null",
//                "str=str",
//                "bool=false",
//                "boolO=true",
//                "b=1",
//                "bo=2",
//                "s=3",
//                "so=4",
//                "i=5",
//                "io=6",
//                "l=7",
//                "lo=8"
//        };
//        ConditionWithProperties invoked = createClassInstance(ConditionWithProperties.class, this, settings);
//        Assert.assertNotSame(template, invoked);
//        Assert.assertEquals(template, invoked);
//    }
//
//
//    private class PrivateConditionWithProperties extends ConditionWithProperties {
//    }
//
//    @Test
//    public void createClassInstance110() {
//        PrivateConditionWithProperties template = new PrivateConditionWithProperties();
//        template.setNullableinteger(null);
//        template.setStr("str");
//        template.setBool(false);
//        template.setBoolO(true);
//        template.setB((byte) 1);
//        template.setBo((byte) 2);
//        template.setS((short) 3);
//        template.setSo((short) 4);
//        template.setI(5);
//        template.setIo(6);
//        template.setL(7);
//        template.setLo(8L);
//        String[] settings = {
//                "nullableinteger->null",
//                "str=str",
//                "bool=false",
//                "boolO=true",
//                "b=1",
//                "bo=2",
//                "s=3",
//                "so=4",
//                "i=5",
//                "io=6",
//                "l=7",
//                "lo=8"
//        };
//        PrivateConditionWithProperties invoked = createClassInstance(PrivateConditionWithProperties.class, this,
//                settings);
//        Assert.assertNotSame(template, invoked);
//        Assert.assertEquals(template, invoked);
//    }
//
//
//    private static class PrivateStaticConditionWithProperties extends ConditionWithProperties {
//    }
//
//    @Test
//    public void createClassInstance120() {
//        PrivateStaticConditionWithProperties template = new PrivateStaticConditionWithProperties();
//        template.setNullableinteger(null);
//        template.setStr("str");
//        template.setBool(false);
//        template.setBoolO(true);
//        template.setB((byte) 1);
//        template.setBo((byte) 2);
//        template.setS((short) 3);
//        template.setSo((short) 4);
//        template.setI(5);
//        template.setIo(6);
//        template.setL(7);
//        template.setLo(8L);
//        String[] settings = {
//                "nullableinteger->null",
//                "str=str",
//                "bool=false",
//                "boolO=true",
//                "b=1",
//                "bo=2",
//                "s=3",
//                "so=4",
//                "i=5",
//                "io=6",
//                "l=7",
//                "lo=8"
//        };
//        PrivateStaticConditionWithProperties invoked =
//                createClassInstance(PrivateStaticConditionWithProperties.class, this, settings);
//        Assert.assertNotSame(template, invoked);
//        Assert.assertEquals(template, invoked);
//    }
//
//
//    @Test
//    public void createClassInstance130() {
//
//        class LocalConditionWithProperties extends ConditionWithProperties {
//        }
//
//
//        LocalConditionWithProperties template = new LocalConditionWithProperties();
//        template.setNullableinteger(null);
//        template.setStr("str");
//        template.setBool(false);
//        template.setBoolO(true);
//        template.setB((byte) 1);
//        template.setBo((byte) 2);
//        template.setS((short) 3);
//        template.setSo((short) 4);
//        template.setI(5);
//        template.setIo(6);
//        template.setL(7);
//        template.setLo(8L);
//        String[] settings = {
//                "nullableinteger->null",
//                "str=str",
//                "bool=false",
//                "boolO=true",
//                "b=1",
//                "bo=2",
//                "s=3",
//                "so=4",
//                "i=5",
//                "io=6",
//                "l=7",
//                "lo=8"
//        };
//        LocalConditionWithProperties invoked =
//                createClassInstance(LocalConditionWithProperties.class, this, settings);
//        Assert.assertNotSame(template, invoked);
//        Assert.assertEquals(template, invoked);
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void createClassInstance140() {
//        createClassInstance(ConditionWithProperties.class, this, "iii=1");
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void createClassInstance150() {
//        createClassInstance(ConditionWithProperties.class, this, "i->null");
//    }
//
//    @Test(expected = NumberFormatException.class)
//    public void createClassInstance160() {
//        createClassInstance(ConditionWithProperties.class, this, "i=null");
//    }
//
//    @Test
//    public void createClassInstance170() {
//
//
//        class LocalClass1 implements IgnoreCondition {
//
//            private String s;
//            private int i;
//            private long l;
//            private double d;
//            private float f;
//            private BigDecimal bd;
//            private BigInteger bi;
//
//
//            public void setI(BigInteger bi) {
//                this.bi = bi;
//            }
//
//            public void setI(BigDecimal bd) {
//                this.bd = bd;
//            }
//
//            public void setI(float f) {
//                this.f = f;
//            }
//
//            public void setI(String s) {
//                this.s = s;
//            }
//
//            public void setI(int i) {
//                this.i = i;
//            }
//
//            public void setI(long l) {
//                this.l = l;
//            }
//
//            public void setI(double d) {
//                this.d = d;
//            }
//
//            @Override
//            public String toString() {
//                return "LocalClass{" +
//                        "s='" + s + '\'' +
//                        ", i=" + i +
//                        ", l=" + l +
//                        ", d=" + d +
//                        ", f=" + f +
//                        ", bd=" + bd +
//                        ", bi=" + bi +
//                        '}';
//            }
//
//            @Override
//            public boolean equals(Object o) {
//                if (this == o) return true;
//                if (!(o instanceof LocalClass1)) return false;
//
//                LocalClass1 that = (LocalClass1) o;
//
//                if (i != that.i) return false;
//                if (l != that.l) return false;
//                if (Double.compare(that.d, d) != 0) return false;
//                if (Float.compare(that.f, f) != 0) return false;
//                if (s != null ? !s.equals(that.s) : that.s != null) return false;
//                if (bd != null ? !bd.equals(that.bd) : that.bd != null) return false;
//                return bi != null ? bi.equals(that.bi) : that.bi == null;
//
//            }
//
//            @Override
//            public int hashCode() {
//                int result;
//                long temp;
//                result = s != null ? s.hashCode() : 0;
//                result = 31 * result + i;
//                result = 31 * result + (int) (l ^ (l >>> 32));
//                temp = Double.doubleToLongBits(d);
//                result = 31 * result + (int) (temp ^ (temp >>> 32));
//                result = 31 * result + (f != +0.0f ? Float.floatToIntBits(f) : 0);
//                result = 31 * result + (bd != null ? bd.hashCode() : 0);
//                result = 31 * result + (bi != null ? bi.hashCode() : 0);
//                return result;
//            }
//
//            @Override
//            public boolean evaluate() throws Exception {
//                return false;
//            }
//        }
//
//        LocalClass1 instance = createClassInstance(LocalClass1.class, this, "i=1");
//        instance = null;
//    }
//
//    @Test
//    public void createClassInstance180() throws Exception {
//
//
//        class LocalClass implements IgnoreCondition {
//
//            private String s;
//            private int i;
//            private long l;
//            private double d;
//            private float f;
//            private BigDecimal bd;
//            private BigInteger bi;
//
//
//            public void setI(BigInteger bi) {
//                this.bi = bi;
//            }
//
//            public void setI(BigDecimal bd) {
//                this.bd = bd;
//            }
//
//            public void setI(float f) {
//                this.f = f;
//            }
//
//            public void setI(String s) {
//                this.s = s;
//            }
//
//            public void setI(int i) {
//                this.i = i;
//            }
//
//            public void setI(long l) {
//                this.l = l;
//            }
//
//            public void setI(double d) {
//                this.d = d;
//            }
//
//            @Override
//            public String toString() {
//                return "LocalClass{" +
//                        "s='" + s + '\'' +
//                        ", i=" + i +
//                        ", l=" + l +
//                        ", d=" + d +
//                        ", f=" + f +
//                        ", bd=" + bd +
//                        ", bi=" + bi +
//                        '}';
//            }
//
//            @Override
//            public boolean equals(Object o) {
//                if (this == o) return true;
//                if (!(o instanceof LocalClass)) return false;
//
//                LocalClass that = (LocalClass) o;
//
//                if (i != that.i) return false;
//                if (l != that.l) return false;
//                if (Double.compare(that.d, d) != 0) return false;
//                if (Float.compare(that.f, f) != 0) return false;
//                if (s != null ? !s.equals(that.s) : that.s != null) return false;
//                if (bd != null ? !bd.equals(that.bd) : that.bd != null) return false;
//                return bi != null ? bi.equals(that.bi) : that.bi == null;
//
//            }
//
//            @Override
//            public int hashCode() {
//                int result;
//                long temp;
//                result = s != null ? s.hashCode() : 0;
//                result = 31 * result + i;
//                result = 31 * result + (int) (l ^ (l >>> 32));
//                temp = Double.doubleToLongBits(d);
//                result = 31 * result + (int) (temp ^ (temp >>> 32));
//                result = 31 * result + (f != +0.0f ? Float.floatToIntBits(f) : 0);
//                result = 31 * result + (bd != null ? bd.hashCode() : 0);
//                result = 31 * result + (bi != null ? bi.hashCode() : 0);
//                return result;
//            }
//
//            @Override
//            public boolean evaluate() throws Exception {
//                return false;
//            }
//        }
//
//
//        CommonUtils.measureTime(() -> createClassInstance(LocalClass.class, this, "i=1"), 10000);
//
//
//    }
//
//
//
//
//    /* fillProperties */
//
//
//}
