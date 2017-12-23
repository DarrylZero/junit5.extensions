package com.steammachine.org.junit5.extensions.testresult.notifications;

import com.steammachine.org.junit5.extensions.testresult.notifications.templateclasses.*;
import org.junit.Assert;
import org.junit.Test;
import com.steammachine.org.junit5.extensions.testresult.ReflectionsCall;
import com.steammachine.org.junit5.extensions.testresult.ReflectionsCall.InvolvedMethod;
import com.steammachine.org.junit5.extensions.testresult.ReflectionsCallsFactory;
import ru.socialquantum.junit5.addons.testresult.notifications.templateclasses.*;

import java.lang.reflect.Method;
import java.util.*;

import static ru.socialquantum.common.utils.commonutils.CommonUtils.measureTime;
import static ru.socialquantum.common.utils.commonutils.CommonUtils.suppress;
import static ru.socialquantum.common.utils.metodsutils.MethodUtils.Level;
import static ru.socialquantum.common.utils.metodsutils.MethodUtils.findMethod;
import static com.steammachine.org.junit5.extensions.testresult.ReflectionsCall.InvolvedMethod.*;
import static com.steammachine.org.junit5.extensions.testresult.ReflectionsCallsFactory.ALL_METHODS;
import static com.steammachine.org.junit5.extensions.testresult.ReflectionsCallsFactory.DYNAMIC_METHODS;

/**
 * Created 06/10/16 11:50
 *
 * @author Vladimir Bogodukhov
 **/
public class ReflectionsCallsFactoryCheck {

    enum TestResultStatus {
        CLASSSUCCESSFUL,
        CLASSFAILED,
        SUCCESSFUL,
        FAILED,
        SKIPPED,
        ABORTED,
    }

    private static final class CallInfo {
        final TestResultStatus status;
        final String testId;
        final Class testClass;
        final Object testInstance;
        final Method testMethod;

        public CallInfo(TestResultStatus status, String testId, Object testInstance, Method testMethod) {
            this.status = Objects.requireNonNull(status);
            this.testId = Objects.requireNonNull(testId);
            this.testInstance = Objects.requireNonNull(testInstance);
            this.testMethod = Objects.requireNonNull(testMethod);
            this.testClass = testInstance.getClass();
        }

        public CallInfo(TestResultStatus status, Class testClass) {
            this.status = Objects.requireNonNull(status);
            this.testClass = Objects.requireNonNull(testClass);
            this.testMethod = null;
            this.testId = null;
            this.testInstance = null;
        }

        public static CallInfo callInfo(TestResultStatus status, String testId, Object testInstance, Method testMethod) {
            return new CallInfo(status, testId, testInstance, testMethod);
        }

        public static CallInfo callInfo(TestResultStatus status, Class testClass) {
            return new CallInfo(status, testClass);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CallInfo)) return false;

            CallInfo callInfo = (CallInfo) o;

            if (status != callInfo.status) return false;
            if (testId != null ? !testId.equals(callInfo.testId) : callInfo.testId != null) return false;
            if (testClass != null ? !testClass.equals(callInfo.testClass) : callInfo.testClass != null) return false;
            if (testInstance != null ? !testInstance.equals(callInfo.testInstance) : callInfo.testInstance != null)
                return false;
            return testMethod != null ? testMethod.equals(callInfo.testMethod) : callInfo.testMethod == null;
        }

        @Override
        public int hashCode() {
            int result = status != null ? status.hashCode() : 0;
            result = 31 * result + (testId != null ? testId.hashCode() : 0);
            result = 31 * result + (testClass != null ? testClass.hashCode() : 0);
            result = 31 * result + (testInstance != null ? testInstance.hashCode() : 0);
            result = 31 * result + (testMethod != null ? testMethod.hashCode() : 0);
            return result;
        }
    }


/* ------------------------------------------------- isCandidate --------------------------------------------------- */

    @Test(expected = NullPointerException.class)
    public void testIsCandidate10() {
        ReflectionsCallsFactory.getInvolvedMethods(null);
    }

    @Test
    public void testIsCandidate20() {
        Assert.assertFalse(!ReflectionsCallsFactory.getInvolvedMethods(TemplateNotANotifier.class).isEmpty());
    }

    @Test
    public void testIsCandidate40() {
        class T extends TemplateNotANotifier {
        }

        Assert.assertFalse(!ReflectionsCallsFactory.getInvolvedMethods(T.class).isEmpty());
    }

    @Test
    public void testIsCandidate50() {
        Assert.assertFalse(!ReflectionsCallsFactory.getInvolvedMethods(TemplateNotANotifier2.class).isEmpty());
    }


    /**
     * Тест показывает возможность получения оповещений в анонимном классе.
     */
    @Test
    public void testIsCandidate60() {
        Set<InvolvedMethod> calledMethods = new HashSet<>();

        Object o = new Object() {
            void testClassSuccess(Class testClass) {
                calledMethods.add(containerSuccess);
            }

            void testClassFailure(Class testClass, String testClassName, Throwable exception) {
                calledMethods.add(containerFailure);
            }

            void testSuccess(String identifier, Object testInstance, Method testMethod) {
                calledMethods.add(success);
            }

            void testFailure(String identifier, Object testInstance, Method testMethod, Throwable t) {
                calledMethods.add(failure);
            }

            void testAborted(String identifier, Object testInstance, Method testMethod) {
                calledMethods.add(aborted);
            }

            void testSkipped(String identifier, Object testInstance, Method testMethod) {
                calledMethods.add(skipped);
            }

            void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e) {
                calledMethods.add(failed);
            }

            void testSuccessful(int[] caseIds, String[] comments, String[] filters) {
                calledMethods.add(successful);
            }
        };

        Assert.assertTrue(ReflectionsCallsFactory.getInvolvedMethods(o.getClass()).containsAll(ALL_METHODS));
        Assert.assertEquals(calledMethods, set());

        ReflectionsCall call = ReflectionsCallsFactory.create(o);

        call.testClassSuccess(null);
        Assert.assertEquals(calledMethods, set(containerSuccess));

        call.testClassFailure(null, "", null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure));


        call.testSuccess(null, null, null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure, success));

        call.testFailure(null, null, null, null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure, success, failure));

        call.testAborted(null, null, null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure, success, failure, aborted));

        call.testSkipped(null, null, null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure, success, failure, aborted, skipped));

        call.testFailed(null, null, null, null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure, success, failure,
                aborted, skipped, failed));

        call.testSuccessful(null, null, null);
        Assert.assertEquals(calledMethods, set(containerSuccess, containerFailure, success, failure, aborted,
                skipped, failed, successful));
    }

    /* ---------------------------------- TemplateNotifiedClassAllMethods10 ------------------------------- */

    @Test
    public void test_TemplateNotifiedClassAllMethods10() {
        Assert.assertTrue(!ReflectionsCallsFactory.getInvolvedMethods(TemplateNotifiedClassAllMethods.class).isEmpty());
    }

    @Test
    public void test_TemplateNotifiedClassAllMethods20() {

        List<CallInfo> results = new ArrayList<>();
        TemplateNotifiedClassAllMethods nexus = new TemplateNotifiedClassAllMethods() {
            @Override
            public void testClassSuccess(Class testClass) {
                results.add(new CallInfo(TestResultStatus.CLASSSUCCESSFUL, testClass));
            }

            @Override
            public void testClassFailure(Class testClass, String testClassName, Throwable exception) {
                results.add(new CallInfo(TestResultStatus.CLASSFAILED, testClass));
            }

            @Override
            public void testSuccess(String testId, Object testInstance, Method testMethod) {
                results.add(new CallInfo(TestResultStatus.SUCCESSFUL, testId, testInstance, testMethod));
            }

            @Override
            public void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
                results.add(new CallInfo(TestResultStatus.FAILED, testId, testInstance, testMethod));
            }

            @Override
            public void testSkipped(String testId, Object testInstance, Method testMethod) {
                results.add(new CallInfo(TestResultStatus.SKIPPED, testId, testInstance, testMethod));
            }

            @Override
            public void testAborted(String testId, Object testInstance, Method testMethod) {
                results.add(new CallInfo(TestResultStatus.ABORTED, testId, testInstance, testMethod));
            }
        };


        final Method testSuccess =
                findMethod(Level.PROTECTED, nexus.getClass(), "testSuccess", null,
                        String.class, Object.class, Method.class);
        final Method testFailed =
                findMethod(Level.PROTECTED, nexus.getClass(), "testFailure", null,
                        String.class, Object.class, Method.class, Throwable.class);
        final Method testSkipped =
                findMethod(Level.PROTECTED, nexus.getClass(), "testSkipped", null,
                        String.class, Object.class, Method.class);
        final Method testAborted =
                findMethod(Level.PROTECTED, nexus.getClass(), "testAborted", null,
                        String.class, Object.class, Method.class);


        ReflectionsCall call = ReflectionsCallsFactory.create(nexus);

        call.testClassSuccess(nexus.getClass());
        call.testClassFailure(nexus.getClass(), nexus.getClass().getName(), new RuntimeException());
        call.testSuccess("ident", nexus, testSuccess);
        call.testFailure("ident", nexus, testFailed, new RuntimeException());
        call.testSkipped("ident", nexus, testSkipped);
        call.testAborted("ident", nexus, testAborted);

        Assert.assertEquals(
                Arrays.asList(
                        CallInfo.callInfo(TestResultStatus.CLASSSUCCESSFUL, nexus.getClass()),
                        CallInfo.callInfo(TestResultStatus.CLASSFAILED, nexus.getClass()),
                        CallInfo.callInfo(TestResultStatus.SUCCESSFUL, "ident", nexus, testSuccess),
                        CallInfo.callInfo(TestResultStatus.FAILED, "ident", nexus, testFailed),
                        CallInfo.callInfo(TestResultStatus.SKIPPED, "ident", nexus, testSkipped),
                        CallInfo.callInfo(TestResultStatus.ABORTED, "ident", nexus, testAborted)

                ), results);
    }


    @Test
    public void testIsCandidate70() {
        Assert.assertEquals(false, ReflectionsCallsFactory.getInvolvedMethods(TemplateNotifiedClassAllMethods.class).isEmpty());
    }

    @Test
    public void testIsCandidate80() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(TemplateNotifiedClassSuccess.class).isEmpty());
    }

    @Test
    public void testIsCandidate90() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(TemplateNotifiedClassFailure.class).isEmpty());
    }

    @Test
    public void testIsCandidate100() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(TemplateNotifiedClassSkipped.class).isEmpty());
    }

    @Test
    public void testIsCandidate110() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(TemplateNotifiedClassAborted.class).isEmpty());
    }


    public class LocalTemplateNotifiedClassAborted1 {
        public void testAborted(String testId, Object testInstance, Method testMethod) {
        }
    }

    @Test
    public void testIsCandidate120() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(LocalTemplateNotifiedClassAborted1.class).isEmpty());

    }

    public class LocalTemplateNotifiedClassAborted2 {
        void testAborted(String testId, Object testInstance, Method testMethod) {
        }
    }

    @Test
    public void testIsCandidate130() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(LocalTemplateNotifiedClassAborted2.class).isEmpty());
    }

    protected class LocalTemplateNotifiedClassAborted3 {
        protected void testAborted(String testId, Object testInstance, Method testMethod) {
        }
    }

    @Test
    public void testIsCandidate140() {
        Assert.assertEquals(true, !ReflectionsCallsFactory.getInvolvedMethods(LocalTemplateNotifiedClassAborted3.class).isEmpty());
    }

    @Test
    public void testIsCandidate150() {
        TemplateNotifiedClassAllMethods2 nexus = new TemplateNotifiedClassAllMethods2() {

            @Override
            public void testSuccess(String testId, Object testInstance, Method testMethod) {
            }

            @Override
            public void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
            }

            @Override
            public void testSkipped(String testId, Object testInstance, Method testMethod) {
            }

            @Override
            public void testAborted(String testId, Object testInstance, Method testMethod) {
            }

            @Override
            public void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e) {
            }

            @Override
            public void testSuccessful(int[] caseIds, String[] comments, String[] filters) {
            }

        };
        Assert.assertEquals(true, ReflectionsCallsFactory.getInvolvedMethods(nexus.getClass()).containsAll(DYNAMIC_METHODS));
    }

    @Test
    public void testIsCandidate155() {
        TemplateNotifiedClassAllMethods2 nexus = new TemplateNotifiedClassAllMethods2() {

            void testClassSuccess(Class testClass) {
            }

            void testClassFailure(Class testClass, String testClassName, Throwable exception) {
            }

            @Override
            public void testSuccess(String testId, Object testInstance, Method testMethod) {
            }

            @Override
            public void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {
            }

            @Override
            public void testSkipped(String testId, Object testInstance, Method testMethod) {
            }

            @Override
            public void testAborted(String testId, Object testInstance, Method testMethod) {
            }

            @Override
            public void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e) {
            }

            @Override
            public void testSuccessful(int[] caseIds, String[] comments, String[] filters) {
            }
        };
        Assert.assertEquals(true, ReflectionsCallsFactory.getInvolvedMethods(nexus.getClass()).containsAll(ALL_METHODS));
    }

    /**
     * Этот тест, где используется interface только с default методами , на данный момент
     * работает так как описано.
     * Это задуманное поведение(пока что).
     */
    @Test
    public void testIsCandidate160() {
        TemplateNotifiedClassAllMethods3 nexus = new TemplateNotifiedClassAllMethods3() {
        };
        Assert.assertEquals(true, ReflectionsCallsFactory.getInvolvedMethods(nexus.getClass()).isEmpty());
    }

/* ------------------------------------------------------- create -------------------------------------------------  */

    @Test(expected = NullPointerException.class)
    public void testCreation() {
        ReflectionsCallsFactory.create(null);
    }

    @Test
    public void testCreation10() {
        ReflectionsCallsFactory.create(new TemplateNotANotifier());
    }

    @Test
    public void testCreation20() {
        ReflectionsCallsFactory.create(new TemplateNotANotifier2());
    }

    @Test
    public void testCreation40() {
        ReflectionsCallsFactory.create(new TemplateNotANotifier2() {
        });
    }

    @Test
    public void testCreation50() {
        ReflectionsCallsFactory.create(new TemplateNotifiedClassAllMethods());
    }


    @Test
    public void testCreation60() {
        ReflectionsCallsFactory.create(new TemplateNotifiedClassAborted());
    }

    @Test
    public void testCreation70() {
        ReflectionsCallsFactory.create(new LocalTemplateNotifiedClassAborted1());
    }

    @Test
    public void testCreation80() {
        ReflectionsCallsFactory.create(new LocalTemplateNotifiedClassAborted2());
    }

    @Test
    public void testCreation90() {
        ReflectionsCallsFactory.create(new LocalTemplateNotifiedClassAborted3());
    }

    @Test
    public void testCreation100() {
        TemplateNotifiedClassAllMethods2 nexus = new TemplateNotifiedClassAllMethods2() {

            @Override
            public void testSuccess(String testId, Object testInstance, Method testMethod) {

            }

            @Override
            public void testFailure(String testId, Object testInstance, Method testMethod, Throwable throwable) {

            }

            @Override
            public void testSkipped(String testId, Object testInstance, Method testMethod) {

            }

            @Override
            public void testAborted(String testId, Object testInstance, Method testMethod) {

            }

            @Override
            public void testFailed(int[] caseIds, String[] comments, String[] filters, Throwable e) {

            }

            @Override
            public void testSuccessful(int[] caseIds, String[] comments, String[] filters) {

            }
        };

        ReflectionsCallsFactory.create(nexus);
    }


    /**
     * Этот тест, где используется interface только с default методами , на данный момент
     * работает вот так >>>--->>
     * <p>
     * <p>
     * ReflectionsCallsFactory.create(nexus)  бросает исключение IllegalStateException
     * Это задуманное поведение(пока что).
     */
    @Test
    public void testCreation120() {
        TemplateNotifiedClassAllMethods3 nexus = new TemplateNotifiedClassAllMethods3() {
        };
        ReflectionsCallsFactory.create(nexus);
    }

    /* --------------------------------------------------- testSpeed10  --------------------------------------------  */
    @Test
    public void testSpeed10() {
        TemplateNotifiedClassAllMethods nexus = new TemplateNotifiedClassAllMethods();
        long l = suppress(() -> measureTime(() -> ReflectionsCallsFactory.create(nexus), 10000));
        Assert.assertTrue(l < 10000);
    }

    @Test
    public void testSpeed20() {
        final Method testSuccess = findMethod(
                Level.PUBLIC, TemplateNotifiedClassAllMethods.class, "testSuccess", null);
        TemplateNotifiedClassAllMethods nexus = new TemplateNotifiedClassAllMethods();
        ReflectionsCall call = ReflectionsCallsFactory.create(nexus);


        long l = suppress(() -> measureTime(() -> call.testSuccess("testSuccess", nexus, testSuccess), 10000));
        Assert.assertTrue(l < 1000);
    }


    /* --------------------------------------------------- default sets check -----------------------------------  */


    public static final Set<InvolvedMethod> DEFAULT_METHODS_SET_TEMPLATE =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    success,
                    failure,
                    skipped,
                    aborted,
                    failed,
                    successful
            )));

    @Test
    public void defaultSetsCheck10() {
        Assert.assertEquals(DEFAULT_METHODS_SET_TEMPLATE, ReflectionsCallsFactory.DYNAMIC_METHODS);
    }


    public static final Set<InvolvedMethod> DEFAULT_STATIC_NOTIFIER_METHODS_SET_TEMPLATE =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    containerFailure,
                    containerSuccess)));

    @Test
    public void defaultSetsCheck20() {
        Assert.assertEquals(DEFAULT_STATIC_NOTIFIER_METHODS_SET_TEMPLATE,
                ReflectionsCallsFactory.STATIC_METHODS);
    }

    /* --------------------------------------------------- privates -----------------------------------------------  */

    @SafeVarargs
    private static <T> Set<T> set(T... t) {
        return new HashSet<T>(Arrays.asList(t));
    }


}

