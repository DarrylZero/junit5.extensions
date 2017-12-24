//package com.steammachine.org.junit5.extensions.ignore;
//
//import org.junit.Assert;
//import org.junit.Test;
//import com.steammachine.org.junit5.extensions.ignore.implementation.StatelessConditionCache;
//import com.steammachine.org.junit5.extensions.ignore.implementation.StatelessConditionCacheImpl;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static com.steammachine.org.junit5.extensions.ignore.StatelessConditionCacheCheck.StatelessConditionCacheImplHack.*;
//
///**
// *
// * @author Vladimir Bogodukhov
// */
//public class StatelessConditionCacheCheck {
//
//    public static class StatelessConditionCacheImplHack {
//        private static final LazyEval<MethodCaller> normalizeParams =
//                LazyEval.eval(() ->
//                        new MethodUtils.BaseMethodCaller(MethodUtils.findMethod(MethodUtils.Level.PRIVATE,
//                                StatelessConditionCacheImpl.class, "normalizeParams", List.class,
//                                String[].class)));
//
//        public static List<String> normalizeParams(String... params) {
//            return normalizeParams.value().invoke(null, (Object) params);
//        }
//    }
//
//
//    @Cached(true)
//    public static class Condition extends DefaultIgnoreCondition {
//        int i;
//        long l;
//
//        public void setI(int i) {
//            this.i = i;
//        }
//
//        public void setL(long l) {
//            this.l = l;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof Condition)) return false;
//
//            Condition condition = (Condition) o;
//
//            if (i != condition.i) return false;
//            return l == condition.l;
//
//        }
//
//        @Override
//        public int hashCode() {
//            int result = i;
//            result = 31 * result + (int) (l ^ (l >>> 32));
//            return result;
//        }
//
//        @Override
//        public String toString() {
//            return "Condition{" +
//                    "i=" + i +
//                    ", l=" + l +
//                    "} " + super.toString();
//        }
//    }
//
//
//    @Test
//    public void test10() {
//        StatelessConditionCache cache = new StatelessConditionCacheImpl();
//        Condition condition = cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20");
//        Assert.assertSame(condition, cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20"));
//    }
//
//
//    @Test
//    public void test20() {
//        StatelessConditionCache cache = new StatelessConditionCacheImpl();
//        Condition condition = cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20");
//        Assert.assertSame(condition, cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20"));
//        Assert.assertSame(condition, cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20"));
//        Assert.assertSame(condition, cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20"));
//        Assert.assertSame(condition, cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20"));
//        Assert.assertNotSame(condition, cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=21"));
//        Assert.assertSame(cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=40"),
//                cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=40"));
//    }
//
//    @Test
//    public void test30() {
//        StatelessConditionCacheImpl cache = new StatelessConditionCacheImpl();
//        Condition condition = cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=10", "l=20");
//        for (int i = 0; i < 10000; i++) {
//            Assert.assertNotSame(condition,
//                    cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=" + i, "l=21"));
//        }
//        Assert.assertEquals(10001, cache .registry().size());
//    }
//
//    @Test
//    public void testSpeed() throws Exception {
//        StatelessConditionCache cache = new StatelessConditionCacheImpl();
//        Condition condition = cache.retrieve(Condition.class, (c) -> suppress(c::newInstance), "i=20", "l=30");
//
//        CommonUtils.measureTime(() ->
//                Assert.assertSame(condition, cache.retrieve(Condition.class,
//                        (c) -> suppress(c::newInstance), "i=20", "l=30")), 10000);
//    }
//
//    /*  ----------------------------------------------- normalizeParams  ------------------------------------------- */
//
//    @Test
//    public void normalizeParams10() {
//        Assert.assertEquals(Collections.emptyList(), normalizeParams());
//    }
//
//    @Test
//    public void normalizeParams20() {
//        Assert.assertEquals(Arrays.asList("1", "10", "12", "2", "3"),
//                normalizeParams("1", "3", "2", "10", "12"));
//    }
//
//    @Test
//    public void normalizeParams30() {
//        Assert.assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"),
//                normalizeParams("5", "1", "6", "4", "3", "3", "3", "2"));
//    }
//
//    @Test
//    public void normalizeParams40() {
//        Assert.assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"),
//                normalizeParams("5", "1", "6", null, "4", "3", "3", "3", "2"));
//    }
//
//    @Test
//    public void normalizeParams50() {
//        Assert.assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"),
//                normalizeParams("5", "1", "6", "4", "", "3", "3", "2"));
//    }
//
//    @Test
//    public void normalizeParams60() {
//        Assert.assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"),
//                normalizeParams("5", "1", "6", "4", "     ", "3", "3", "2"));
//    }
//
//
//    @Test
//    public void normalizeParamsSpeed() throws Exception {
//        CommonUtils.measureTime(() ->
//                normalizeParams("1", "3", "2", "10", "12", "2", "10", "12"), 10000);
//
//    }
//
//
//}