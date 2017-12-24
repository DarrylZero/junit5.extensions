//
//import org.junit.jupiter.api.extension.*;
//import org.junit.platform.engine.TestExecutionResult;
//import org.junit.platform.engine.TestSource;
//import org.junit.platform.engine.UniqueId;
//import org.junit.platform.engine.support.descriptor.MethodSource;
//import org.junit.platform.launcher.TestExecutionListener;
//import org.junit.platform.launcher.TestIdentifier;
//import ClassSourceWrapper;
//import ReflectionsCall;
//import ReflectionsCallsFactory;
//import TestStatus;
//import CallBack;
//import CallBacks;
//import CallBacksSingleton;
//import DynamicTestCallBackKey;
//import APILevel;
//import Api;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.platform.launcher.TestIdentifierHelper.updateIdentifierSource;
//import static org.junit.platform.launcher.core.HackUtils.*;
//
///**
// * Created 09/09/16 16:02
// *
// * @author Vladimir Bogodukhov
// *         UNDER_CONSTRUCTION!!!
// **/
//@Api(value = APILevel.internal)
//public class TestResultNotificationsExtensionV3 implements
//
//        TestExecutionCondition,
//        BeforeAllCallback {
//
//    public static final int VERSION = 3;
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
//    public ConditionEvaluationResult evaluate(TestExtensionContext c) {
//
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
//                Object testInstance = c.getTestInstance();
//                processExecutionFinished(testIdentifier.getUniqueId(), testIdentifier,
//                        calculateStatus(false, testExecutionResult.getStatus()), testInstance, method,
//                        throwable);
//            }
//        });
//
//        execute(new Exec() {
//            private Link agent;
//            private String testIdent;
//            private final Method testMethod = c.getTestMethod().orElseThrow(IllegalStateException::new);
//
//            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
//            @Override
//            public void execute() {
//                if (isDynamicTestFactory(c.getUniqueId())) {
//                    return;
//                }
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
//                        processExecutionFinished(testIdent, ti, status, c.getTestInstance(), testMethod, thr);
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
//                        processExecutionFinished(testIdent, ti, TestStatus.SKIPPED, c.getTestInstance(), testMethod, null);
//                    }
//                });
//            }
//        });
//
//        return ConditionEvaluationResult.enabled("");
//    }
//
//    @Override
//    public void beforeAll(ContainerExtensionContext context) throws Exception {
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
//    private static void processClassExecution(TestIdentifier identifier, TestExecutionResult result) {
//        Throwable throwable = result.getThrowable().orElse(null);
//        final TestStatus status = calculateStatus(false, result.getStatus());
//        TestSource testSource = identifier.getSource().orElseThrow(null);
//        ClassSourceWrapper.checkClassSource(testSource.getClass());
//                        /* testClass это класс того объекта теста, который выполнеяется. */
//        Class testClass = new ClassSourceWrapper(testSource).getTestClass();
//        List<ReflectionsCall> calls = collectClassRuleNotifiers(testClass);
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
//
//    /**
//     * @param testStringIdentifier - Строковый идентификатор
//     * @param identifier
//     * @param testStatus
//     * @param testInstance
//     * @param testMethod
//     * @param thr
//     */
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
//        collectRuleNotifiers(testInstance).
//                forEach(i -> notifyObjectFields(i, testStringIdentifier, testStatus, testInstance, testMethod, thr));
//    }
//
//    @SignatureSensitive
//    private static List<ReflectionsCall> collectRuleNotifiers(Object testInstance) {
//        Objects.requireNonNull(testInstance, "testInstance is null");
//
//        Class<?> clazz = testInstance.getClass();
//        Stream.Builder<Class> builder = Stream.builder();
//        while (clazz != null) {
//            builder.add(clazz);
//            clazz = clazz.getSuperclass();
//        }
//        Stream<Class> stream = builder.build();
//        return stream.
//                map(c -> Arrays.asList(c.getDeclaredFields())).
//                reduce(Collections.emptyList(), (fs1, fs2) -> {
//                    List<Field> fields = new ArrayList<>();
//                    fields.addAll(fs1);
//                    fields.addAll(fs2);
//                    return fields;
//                }).stream().
//                distinct().
//                filter((f) -> !Modifier.isStatic(f.getModifiers())).
//                map((f) -> getFieldValueSuppressed(testInstance, f)).
//                filter(Objects::nonNull).
//                filter((o) -> !intersectCollections(DYNAMIC_METHODS, getInvolvedMethods(o.getClass())).isEmpty()).
//                map(ReflectionsCallsFactory::create).
//                collect(Collectors.toList());
//    }
//
//    @SignatureSensitive
//    private static List<ReflectionsCall> collectClassRuleNotifiers(Class testClass) {
//        Objects.requireNonNull(testClass);
//
//        Class<?> clazz = testClass;
//        Stream.Builder<Class> builder = Stream.builder();
//        while (clazz != null) {
//            builder.add(clazz);
//            clazz = clazz.getSuperclass();
//        }
//        Stream<Class> stream = builder.build();
//        return stream.
//                map(c -> Arrays.asList(c.getDeclaredFields())).
//                reduce(Collections.emptyList(), (fs1, fs2) -> {
//                    List<Field> fields = new ArrayList<>();
//                    fields.addAll(fs1);
//                    fields.addAll(fs2);
//                    return fields;
//                }).stream().
//                distinct().
//                filter((f) -> Modifier.isStatic(f.getModifiers())).
//
//
//                map((f) -> getFieldValueSuppressed(null, f)).
//                filter(Objects::nonNull).
//                filter((o) -> !intersectCollections(STATIC_METHODS, getInvolvedMethods(o.getClass())).isEmpty()).
//                map(ReflectionsCallsFactory::create).
//                collect(Collectors.toList());
//    }
//
//    @SignatureSensitive
//    private static List<ReflectionsCall> __collectClassRuleNotifiers(Class testClass) {
//        Objects.requireNonNull(testClass);
//
//        Class<?> clazz = testClass;
//        Stream.Builder<Class> builder = Stream.builder();
//        while (clazz != null) {
//            builder.add(clazz);
//            clazz = clazz.getSuperclass();
//        }
//        Stream<Class> stream = builder.build();
//        return stream.
//                map(c -> Arrays.asList(c.getDeclaredFields())).
//                reduce(Collections.emptyList(), (fs1, fs2) -> {
//                    List<Field> fields = new ArrayList<>();
//                    fields.addAll(fs1);
//                    fields.addAll(fs2);
//                    return fields;
//                }).stream().
//                distinct().
//                filter((f) -> Modifier.isStatic(f.getModifiers())).
//                map((f) -> getFieldValueSuppressed(null, f)).
//                filter(Objects::nonNull).
//                filter((o) -> !intersectCollections(STATIC_METHODS, getInvolvedMethods(o.getClass())).isEmpty()).
//                map(ReflectionsCallsFactory::create).
//                collect(Collectors.toList());
//    }
//
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
//    private static Object getFieldValue(Object testInstance, Field field) throws IllegalAccessException, NoSuchFieldException {
//        Field modifiersField = Field.class.getDeclaredField("modifiers");
//        modifiersField.setAccessible(true);
//        int currentModifiers = modifiersField.getInt(field);
//        try {
//            int newModifiers = currentModifiers & ~Modifier.FINAL & ~Modifier.PRIVATE;
//            modifiersField.setInt(field, newModifiers);
//            field.setAccessible(true);
//
//            Object owner = Modifier.isStatic(field.getModifiers()) ? null : testInstance;
//            return field.get(owner);
//        } finally {
//            modifiersField.setInt(field, currentModifiers);
//        }
//    }
//
//    private static Object getClassFieldValue(Class clazz, Field field) throws IllegalAccessException, NoSuchFieldException {
//        Objects.requireNonNull(clazz);
//        Objects.requireNonNull(field);
//
//        Field modifiersField = Field.class.getDeclaredField("modifiers");
//        modifiersField.setAccessible(true);
//        int currentModifiers = modifiersField.getInt(field);
//        try {
//            int newModifiers = currentModifiers & ~Modifier.FINAL & ~Modifier.PRIVATE;
//            modifiersField.setInt(field, newModifiers);
//            field.setAccessible(true);
//            return field.get(clazz);
//        } finally {
//            modifiersField.setInt(field, currentModifiers);
//        }
//    }
//
//    private static Object getFieldValueSuppressed(Object testInstance, Field field) {
//        return suppress(() -> getFieldValue(testInstance, field));
//    }
//
//    private static Object getClassFieldValueSuppressed(Class clazz, Field field) {
//        return suppress(() -> getClassFieldValue(clazz, field));
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
//
//
//}
