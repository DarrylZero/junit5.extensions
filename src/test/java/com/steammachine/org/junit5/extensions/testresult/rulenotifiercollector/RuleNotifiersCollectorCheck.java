package com.steammachine.org.junit5.extensions.testresult.rulenotifiercollector;

import com.steammachine.org.junit5.extensions.testresult.implementation.testresultnotificationsextension.classes.*;
import org.junit.Assert;
import org.junit.Test;
import com.steammachine.org.junit5.extensions.testresult.ReflectionsCall;
import ru.socialquantum.junit5.addons.testresult.implementation.testresultnotificationsextension.classes.*;
import com.steammachine.org.junit5.extensions.testresult.rulenotifiercollector.classes.Notified2;

import java.util.*;
import java.util.stream.Collectors;

import static com.steammachine.org.junit5.extensions.testresult.ReflectionsCall.InvolvedMethod;
import static com.steammachine.org.junit5.extensions.testresult.ReflectionsCall.InvolvedMethod.containerFailure;
import static com.steammachine.org.junit5.extensions.testresult.ReflectionsCall.InvolvedMethod.containerSuccess;
import static com.steammachine.org.junit5.extensions.testresult.NotifierCollector.collectClassNotifiers;
import static com.steammachine.org.junit5.extensions.testresult.NotifierCollector.collectNotifiers;

/**
 * Created by Vladimir Bogodukhov on 14/07/17.
 *
 * @author Vladimir Bogodukhov
 */
public class RuleNotifiersCollectorCheck {

    private static class Notified5 {
        public Notifier1 notifier1 = new Notifier1();
    }

    private class Notified6 {
        public Notifier1 notifier1 = new Notifier1();
    }


    private static final Comparator<Set<ReflectionsCall.InvolvedMethod>> SET_LIST_COMPARATOR = (o1, o2) -> {
        if (o1 == o2) {
            return 0;
        }

        if (o1.size() > o2.size()) {
            return 1;
        }

        if (o1.size() < o2.size()) {
            return -1;
        }


        if (weight(o1) > weight(o2)) {
            return 1;
        }

        if (weight(o2) > weight(o1)) {
            return -1;
        }
        return 0;
    };

    private static final Map<ReflectionsCall.InvolvedMethod, Integer> INVOLVED_METHOD_INTEGER_MAP =
            Collections.unmodifiableMap(new HashMap<ReflectionsCall.InvolvedMethod, Integer>() {
                {
                    if (InvolvedMethod.values().length != 8) {
                        throw new IllegalStateException();
                    }
                    put(containerSuccess, 1);
                    put(containerFailure, 2);
                    put(InvolvedMethod.success, 4);
                    put(InvolvedMethod.failure, 8);
                    put(InvolvedMethod.aborted, 16);
                    put(InvolvedMethod.skipped, 32);
                    put(InvolvedMethod.successful, 64);
                    put(InvolvedMethod.failed, 128);
                }
            });


    /**
     * Проверка оповещения public поля у класса с package default модификатором
     */
    @Test
    public void collectRuleNotifiers40() {
        Assert.assertEquals(set(InvolvedMethod.success),
                only(collectNotifiers(new Notified2())).involvedMethods());
    }

    @Test
    public void collectRuleNotifiers20() {
        Assert.assertEquals(Collections.emptyList(), collectNotifiers(new Object()));
    }

    /**
     * Проверка оповещения public поля у public класса
     */
    @Test
    public void collectRuleNotifiers30() {
        Assert.assertEquals(set(InvolvedMethod.skipped),
                only(collectNotifiers(new Notified1())).involvedMethods());
    }


    /**
     * Проверка оповещения public поля у класса с package default модификатором
     */
    @Test
    public void collectRuleNotifiers50_v2() {
        List<ReflectionsCall> calls = collectNotifiers(new Notified3());
        Assert.assertEquals(4, calls.size());
        List<Set<ReflectionsCall.InvolvedMethod>> involvedMethodsSet =
                calls.stream().map(ReflectionsCall::involvedMethods).collect(Collectors.toList());
        involvedMethodsSet.sort(SET_LIST_COMPARATOR);

        List<Set<ReflectionsCall.InvolvedMethod>> expected =
                new ArrayList<>(Arrays.asList(
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success)
                ));
        expected.sort(SET_LIST_COMPARATOR);
        Assert.assertEquals(expected, involvedMethodsSet);
    }


    @Test
    public void collectClassRuleNotifiers50() {
        List<Set<ReflectionsCall.InvolvedMethod>> actual =
                new ArrayList<>(collectClassNotifiers(Notified7.class).stream().
                        map(ReflectionsCall::involvedMethods).
                        collect(Collectors.toList()));
        actual.sort(SET_LIST_COMPARATOR);

        List<Set<ReflectionsCall.InvolvedMethod>> expected =
                new ArrayList<>(Arrays.asList(
                        set(containerFailure),
                        set(containerSuccess),
                        set(containerSuccess),
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure)
                ));
        expected.sort(SET_LIST_COMPARATOR);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Проверка оповещения public поля у класса с private модификатором
     */
    @Test
    public void collectRuleNotifiers45() {
        Assert.assertEquals(set(InvolvedMethod.skipped),
                only(collectNotifiers(new Notified5())).involvedMethods());
    }


    /**
     * Проверка оповещения public поля у класса с private модификатором
     */
    @Test
    public void collectRuleNotifiers46() {
        Assert.assertEquals(set(InvolvedMethod.skipped),
                only(collectNotifiers(new Notified6())).involvedMethods());
    }

    @Test
    public void collectClassRuleNotifiers20() {
        Assert.assertEquals(Collections.emptyList(), collectClassNotifiers(Object.class));
    }

    @Test
    public void collectRuleNotifiers60_v2() {
        List<ReflectionsCall> calls = collectNotifiers(new Notified4());
        Assert.assertEquals(7, calls.size());
        List<Set<ReflectionsCall.InvolvedMethod>> involvedMethodsSets =
                new ArrayList<>(calls.stream().map(ReflectionsCall::involvedMethods).collect(Collectors.toList()));
        involvedMethodsSets.sort(SET_LIST_COMPARATOR);

        List<Set<ReflectionsCall.InvolvedMethod>> expected =
                new ArrayList<>(Arrays.asList(
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.success),
                        set(InvolvedMethod.skipped)
                ));
        expected.sort(SET_LIST_COMPARATOR);
        Assert.assertEquals(expected, involvedMethodsSets);
    }

    @Test(expected = NullPointerException.class)
    public void collectClassRuleNotifiers10() {
        collectClassNotifiers(null);
    }

    private static class Clazz1 {
    }

    @Test
    public void collectClassRuleNotifiers30() {
        Assert.assertEquals(Collections.emptyList(), collectClassNotifiers(Clazz1.class));
    }

    @SuppressWarnings("unused")
    private static class Clazz2 {
        private static final Object n1 = new Object() {
            protected void testClassSuccess(Class testClass) {
            }

            protected void testClassFailure(Class testClass, String testClassName, Throwable exception) {
            }
        };

        static final Object n2 = new Object() {
            protected void testClassSuccess(Class testClass) {
            }
        };

        protected static final Object n3 = new Object() {
            protected void testClassFailure(Class testClass, String testClassName, Throwable exception) {
            }
        };

        static final Object n4 = new Object() {
            protected void testClassFailure(Class testClass, String testClassName, Throwable exception) {
            }
        };
    }


    @Test
    public void collectClassRuleNotifiers40() {
        List<Set<InvolvedMethod>> actual = new ArrayList<>(collectClassNotifiers(Clazz2.class).stream().
                map(ReflectionsCall::involvedMethods).
                collect(Collectors.toList()));
        actual.sort(SET_LIST_COMPARATOR);

        List<Set<InvolvedMethod>> expected =
                new ArrayList<>(Arrays.asList(
                        set(containerSuccess),
                        set(containerFailure),
                        set(containerFailure),
                        set(containerSuccess, containerFailure)
                ));
        expected.sort(SET_LIST_COMPARATOR);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void collectClassRuleNotifiers60() {
        List<Set<InvolvedMethod>> actual = new ArrayList<>(collectClassNotifiers(Notified8.class).stream().
                map(ReflectionsCall::involvedMethods).
                collect(Collectors.toList()));
        actual.sort(SET_LIST_COMPARATOR);

        List<Set<InvolvedMethod>> expected =
                new ArrayList<>(Arrays.asList(
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure),
                        set(containerSuccess, containerFailure)
                ));
        expected.sort(SET_LIST_COMPARATOR);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void collectRuleNotifiers10() {
        collectNotifiers(null);
    }

    /* ------------------------------------------------ privates ------------------------------------------------ */


    private static int weight(Set<ReflectionsCall.InvolvedMethod> methods) {
        return methods.stream().
                map(Objects::requireNonNull).
                map((INVOLVED_METHOD_INTEGER_MAP::get)).
                map(Objects::requireNonNull).
                reduce(0, (i, i2) -> i + i2);
    }

    private static <T> T only(List<T> list) {
        Objects.requireNonNull(list);
        if (list.size() != 1) {
            throw new IllegalStateException();
        }
        return list.get(0);
    }

    private static <T> Set<T> set(T... items) {
        return new HashSet<T>(Arrays.asList(items));
    }

}