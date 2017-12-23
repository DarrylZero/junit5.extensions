//package com.steammachine.org.junit5.extensions.testresult.implementation;
//
//import com.steammachine.org.junit5.extensions.common.ClassSourceWrapper;
//import com.steammachine.org.junit5.extensions.testresult.NotifierCollector;
//import com.steammachine.org.junit5.extensions.testresult.ReflectionsCall;
//import com.steammachine.org.junit5.extensions.testresult.TestStatus;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.CallBack;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.CallBacks;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.CallBacksSingleton;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.DynamicTestCallBackKey;
//import com.steammachine.org.junit5.extensions.types.APILevel;
//import com.steammachine.org.junit5.extensions.types.Api;
//import org.junit.jupiter.api.extension.BeforeAllCallback;
//import org.junit.jupiter.api.extension.ConditionEvaluationResult;
//import org.junit.jupiter.api.extension.ExecutionCondition;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.platform.engine.TestExecutionResult;
//import org.junit.platform.engine.TestSource;
//import org.junit.platform.engine.UniqueId;
//import org.junit.platform.engine.support.descriptor.MethodSource;
//import org.junit.platform.launcher.TestExecutionListener;
//import org.junit.platform.launcher.TestIdentifier;
//import org.junit.platform.launcher.core.HackUtilsV6.Link;
//
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static org.junit.platform.launcher.TestIdentifierHelper.updateIdentifierSource;
//import static org.junit.platform.launcher.core.HackUtils.hackContainerExtensionContext;
//import static org.junit.platform.launcher.core.HackUtils.hackTestExtensionContext;
//
///**
// * Created 14/07/17 16:02
// *
// * @author Vladimir Bogodukhov
// **/
//@Api(value = APILevel.internal)
//public class TestResultNotificationsExtensionV5 implements ExecutionCondition, BeforeAllCallback {
//
//    public static final int VERSION = 4;
//
//    private final CallBacks callBacksStore = CallBacksSingleton.instance();
//
//
//    @FunctionalInterface
//    private interface Exec {
//        void execute();
//    }
//
//
//    @FunctionalInterface
//    private interface SQTestExecutionListener extends TestExecutionListener {
//        @Override
//        void executionFinished(TestIdentifier identifier, TestExecutionResult result);
//    }
//
//    @Override
//    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext c) {
//        execute(new Exec() {
//            private Link agent;
//            private final String testFactoryId = c.getUniqueId();
//            private Map<String, MethodData> callBacksInfo = new ConcurrentHashMap<>();
//
//            @Override
//            public void execute() {
//                if (!isDynamicTestFactory(c.getUniqueId())) {
//                    return;
//                }
//
//                agent = hackTestExtensionContext(c, new TestExecutionListener() {
//                    @Override
//                    public void executionStarted(TestIdentifier testIdentifier) {
//                        if (isDynamicTest(testIdentifier)) {
//                            CallBack<MethodData> callBack = new CallBack<MethodData>() {
//                                private final String currentTestIdentifier = testIdentifier.getUniqueId();
//
//                                @Override
//                                public void railTestSuccess(MethodData methodData) {
//                                    setTestData(currentTestIdentifier, MethodData.updateData(methodData, this));
//                                }
//
//                                @Override
//                                public void railTestFailure(MethodData methodData, Throwable throwable) {
//                                    setTestData(currentTestIdentifier, MethodData.updateData(methodData, this));
//                                }
//                            };
//                            registerMethodCallBack(callBack);
//                        }
//                    }
//
//                    @Override
//                    public void executionFinished(TestIdentifier testIdentifier,
//                                                  TestExecutionResult testExecutionResult) {
//                        if (agent == null) {
//                            return;
//                        }
//
//                        if (isDynamicTest(testIdentifier)) {
//                            CallBack<MethodData> callBack = getCallBack(testIdentifier.getUniqueId());
//                            if (callBack != null) {
//                                unRegisterMethodCallBack(callBack);
//                                MethodData testData = getTestData(testIdentifier.getUniqueId());
//                                removeTestData(testIdentifier.getUniqueId());
//
//                                TestIdentifier newIdentifier = updateIdentifierSource(testIdentifier,
//                                        new MethodSource(testData.method()));
//
//                                dynamicTestFinished(newIdentifier, testExecutionResult, testData.method());
//                            }
//                        }
//
//                        if (isDynamicTestFactory(testIdentifier) &&
//                                Objects.equals(testFactoryId, testIdentifier.getUniqueId())) {
//                            agent.disconnect();
//                            agent = null;
//                        }
//                    }
//                });
//            }
//
//            private void setTestData(String uniqueId, MethodData methodData) {
//                callBacksInfo.put(uniqueId, methodData);
//            }
//
//            private void registerMethodCallBack(CallBack<MethodData> callBack) {
//                callBacksStore.register(DynamicTestCallBackKey.KEY, callBack);
//            }
//
//            private void unRegisterMethodCallBack(CallBack<MethodData> callBack) {
//                callBacksStore.unregister(DynamicTestCallBackKey.KEY, callBack);
//            }
//
//            private void removeTestData(String uniqueId) {
//                callBacksInfo.remove(uniqueId);
//            }
//
//            private MethodData getTestData(String uniqueId) {
//                return callBacksInfo.get(uniqueId);
//            }
//
//            private CallBack<MethodData> getCallBack(String uniqueId) {
//                MethodData testData = getTestData(uniqueId);
//                if (testData == null) {
//                    return null;
//                }
//                //noinspection unchecked
//                return (CallBack<MethodData>) testData.addition();
//            }
//
//
//            private void dynamicTestStarted(TestIdentifier testIdentifier) {
////                System.out.println("executionStarted " + testIdentifier);
//            }
//
//            private void dynamicTestFinished(TestIdentifier testIdentifier,
//                                             TestExecutionResult testExecutionResult, Method method) {
//                Throwable throwable = testExecutionResult.getThrowable().orElse(null);
//                Object testInstance = c.getTestInstance().orElse(null);
//                processExecutionFinished(testIdentifier.getUniqueId(), testIdentifier,
//                        calculateStatus(false, testExecutionResult.getStatus()), testInstance, method,
//                        throwable);
//            }
//        });
//
//        execute(new Exec() {
//            private Link agent;
//            private String testIdent;
//            //            private final Method testMethod = c.getTestMethod().orElseThrow(IllegalStateException::new);
//            private final Method testMethod = c.getTestMethod().orElse(null);
//
//            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
//            @Override
//            public void execute() {
//                if (testMethod == null) return;
//                if (isDynamicTestFactory(c.getUniqueId())) return;
//
//
//                agent = hackTestExtensionContext(c, new TestExecutionListener() {
//                    @Override
//                    public void executionFinished(TestIdentifier ti, TestExecutionResult testExecutionResult) {
//                        if (!ti.isTest() || agent == null || isDynamicTest(ti)) {
//                            return;
//                        }
//                        /* Если есть агент и это не контейнер */
//                        agent.disconnect(); /* first disconnect - then fire event */
//                        agent = null;
//
//                        testIdent = testIdent != null ? testIdent : ti.getDisplayName(); /* Похоже на баг фреймворка !*/
//                        final TestStatus status = calculateStatus(false, testExecutionResult.getStatus());
//                        Throwable thr = testExecutionResult.getThrowable().orElse(null);
//                        processExecutionFinished(testIdent, ti, status, c.getTestInstance().orElse(null), testMethod, thr);
//                    }
//
//                    @Override
//                    public void executionSkipped(TestIdentifier ti, String reason) {
//                        if (!ti.isTest() || agent == null || isDynamicTest(ti)) {
//                            return;
//                        }
//                        /* Если есть агент и это не контейнер */
//                        agent.disconnect(); /* first disconnect - then fire event */
//                        agent = null;
//
//                        testIdent = testIdent != null ? testIdent : ti.getDisplayName(); /* Похоже на баг фреймворка !*/
//                        processExecutionFinished(testIdent, ti, TestStatus.SKIPPED, c.getTestInstance().orElse(null), testMethod, null);
//                    }
//                });
//            }
//        });
//
//        return ConditionEvaluationResult.enabled("");
//    }
//
//    @Override
//    public void beforeAll(ExtensionContext context) throws Exception {
//        execute(new Exec() {
//            private Link link;
//
//            @Override
//            public void execute() {
//                link = hackContainerExtensionContext(context, (SQTestExecutionListener) (identifier, result) -> {
//                    if (identifier.isContainer()) {
//                        link.disconnect();
//                        processClassExecution(identifier, result);
//                    }
//                });
//            }
//        });
//    }
//
//    /* ------------------------------------------------------ privates  --------------------------------------------- */
//
//    private void processExecutionFinished(
//            String testStringIdentifier,
//            TestIdentifier identifier,
//            TestStatus testStatus,
//            Object testInstance,
//            Method testMethod,
//            Throwable thr) {
//        /* test result notifications are triggered */
//        if (!identifier.isTest()) {
//            throw new IllegalStateException();
//        }
//        NotifierCollector.collectNotifiers(testInstance).
//                forEach(i -> notifyObjectFields(i, testStringIdentifier, testStatus, testInstance, testMethod, thr));
//    }
//
//    /* ---------------------------------------------- statics ------------------------------------------------------- */
//
//    private static void processClassExecution(TestIdentifier identifier, TestExecutionResult result) {
//        Throwable throwable = result.getThrowable().orElse(null);
//        final TestStatus status = calculateStatus(false, result.getStatus());
//        TestSource testSource = identifier.getSource().orElseThrow(null);
//        ClassSourceWrapper.checkClassSource(testSource.getClass());
//                        /* testClass это класс того объекта теста, который выполнеяется. */
//        Class testClass = new ClassSourceWrapper(testSource).getTestClass();
//        List<ReflectionsCall> calls = NotifierCollector.collectClassNotifiers(testClass);
//        calls.forEach((i) -> notifyClassFields(i, status, testClass, throwable));
//    }
//
//
//    private static TestStatus calculateStatus(boolean isSkipped, TestExecutionResult.Status testStatus) {
//        final TestStatus status;
//        if (isSkipped) {
//            status = TestStatus.SKIPPED;
//        } else {
//            switch (testStatus) {
//                case ABORTED: {
//                    status = TestStatus.ABORTED;
//                    break;
//                }
//                case SUCCESSFUL: {
//                    status = TestStatus.SUCCESSFUL;
//                    break;
//                }
//
//                case FAILED: {
//                    status = TestStatus.FAILED;
//                    break;
//                }
//
//                default: {
//                    throw new IllegalStateException();
//                }
//            }
//        }
//        return status;
//    }
//
//    private static void notifyObjectFields(
//            ReflectionsCall call,
//            String testIdentifier,
//            TestStatus status,
//            Object testInstance,
//            Method testMethod,
//            Throwable thr) {
//        Objects.requireNonNull(call);
//        Objects.requireNonNull(status);
//        Objects.requireNonNull(testIdentifier);
//        Objects.requireNonNull(testInstance);
//        Objects.requireNonNull(testMethod);
//
//        switch (status) {
//            case SUCCESSFUL: {
//                call.testSuccess(testIdentifier, testInstance, testMethod);
//                break;
//            }
//
//            case FAILED: {
//                call.testFailure(testIdentifier, testInstance, testMethod, thr);
//                break;
//            }
//
//            case SKIPPED: {
//                call.testSkipped(testIdentifier, testInstance, testMethod);
//                break;
//            }
//
//            case ABORTED: {
//                call.testAborted(testIdentifier, testInstance, testMethod);
//                break;
//            }
//
//            default: {
//                throw new IllegalStateException("unknown status " + status);
//            }
//        }
//    }
//
//
//    private static void notifyClassFields(
//            ReflectionsCall call,
//            TestStatus testStatus,
//            Class testClass,
//            Throwable throwable) {
//        Objects.requireNonNull(call);
//        Objects.requireNonNull(testStatus);
//        Objects.requireNonNull(testClass);
//
//        switch (testStatus) {
//            case SUCCESSFUL: {
//                call.testClassSuccess(testClass);
//                break;
//            }
//
//            case FAILED: {
//                call.testClassFailure(testClass, testClass.getName(), throwable);
//                break;
//            }
//
//            case ABORTED: {
//                throw new IllegalStateException("unprocessed status " + testStatus);
//            }
//
//            default: {
//                throw new IllegalStateException("unknown status " + testStatus);
//            }
//        }
//    }
//
//    public static void execute(Exec exec) {
//        Objects.requireNonNull(exec).execute();
//    }
//
//    private static boolean isDynamicTest(String testIdentifier) {
//        return "dynamic-test".equals(topIdType(testIdentifier));
//    }
//
//    private static boolean isDynamicTest(TestIdentifier testIdentifier) {
//        return isDynamicTest(testIdentifier.getUniqueId());
//    }
//
//    private static boolean isDynamicTestFactory(String testIdentifier) {
//        return "test-factory".equals(topIdType(testIdentifier));
//    }
//
//    private static boolean isDynamicTestFactory(TestIdentifier testIdentifier) {
//        return isDynamicTestFactory(testIdentifier.getUniqueId());
//    }
//
//    private static String topIdValue(String testIdentifier) {
//        UniqueId uniqueId = UniqueId.parse(testIdentifier);
//        if (uniqueId.getSegments().isEmpty()) {
//            return null;
//        }
//
//        return uniqueId.getSegments().get(uniqueId.getSegments().size() - 1).getValue();
//    }
//
//    private static String topIdType(String testIdentifier) {
//        UniqueId uniqueId = UniqueId.parse(testIdentifier);
//        if (uniqueId.getSegments().isEmpty()) {
//            return null;
//        }
//
//        return uniqueId.getSegments().get(uniqueId.getSegments().size() - 1).getType();
//    }
//}
