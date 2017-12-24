//package com.steammachine.org.junit5.extensions.ignore.implementation;
//
//import com.steammachine.org.junit5.extensions.ignore.Cached;
//import com.steammachine.org.junit5.extensions.ignore.IgnoreJ5;
//import com.steammachine.org.junit5.extensions.types.APILevel;
//import com.steammachine.org.junit5.extensions.types.Api;
//import org.junit.jupiter.api.extension.ConditionEvaluationResult;
//import org.junit.jupiter.api.extension.ExecutionCondition;
//import org.junit.jupiter.api.extension.ExtensionContext;
////import org.junit.jupiter.engine.descriptor.MethodBasedTestExtensionContext;
//import org.junit.platform.commons.util.AnnotationUtils;
//import com.steammachine.org.junit5.extensions.ignore.IgnoreCondition;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//
///**
// * Created 09/09/16 16:02
// *
// * @author Vladimir Bogodukhov
// **/
//@Api(value = APILevel.internal)
////public class IgnoreExtensionV3 implements ContainerExecutionCondition, TestExecutionCondition {
//public class IgnoreExtensionV6 implements ExecutionCondition {
//
//    /**
//     * Текущая версия класса
//     */
//    public static final int VERSION = 3;
//    public static final int SUB_VERSION = 2;
//    private static final ConditionEvaluationResult ENABLED = ConditionEvaluationResult.enabled("");
//    private static final StatelessConditionCache cache = new StatelessConditionCacheImpl();
//    private static final String NULL_SIGN = "->null";
//    private static final String EQUAL_SIGN = "=";
//
//    @Override
//    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
//        return evaluateIfAnnotated(context);
//    }
//
////    @Override
////    public ConditionEvaluationResult evaluate(ContainerExtensionContext context) {
////        return evaluateIfAnnotated(context);
////    }
//
////    @Override
////    public ConditionEvaluationResult evaluate(TestExtensionContext context) {
////        return evaluateIfAnnotated(context);
////    }
//
//    private ConditionEvaluationResult evaluateIfAnnotated(ExtensionContext context) {
//        Optional<IgnoreJ5> disabledOptional = AnnotationUtils.findAnnotation(context.getElement(), IgnoreJ5.class);
//
//        if (!disabledOptional.isPresent()) {
//            return ENABLED;
//        }
//
//
////        context.getRequiredTestInstance();
//        Object testInstance = context.getTestInstance().orElse(null);
//
////        Object testInstance = context instanceof MethodBasedTestExtensionContext ?
////                ((MethodBasedTestExtensionContext) context).getTestInstance() :
////                null;
//
//        IgnoreJ5 i = disabledOptional.get();
//        boolean isDisabled = suppress(() -> getCondition(i.condition(), testInstance, cache, i.params()).evaluate(), Boolean.TYPE);
//        String reason = i.value().isEmpty() ? null : "@Ignore with reason --> " + i.value();
//        return isDisabled ? ConditionEvaluationResult.disabled(reason) : ENABLED;
//    }
//
//    private static <T extends IgnoreCondition> T getCondition(
//            Class<T> value,
//            Object testInstance,
//            StatelessConditionCache cache,
//            String[] settings) {
//        Objects.requireNonNull(value);
//        Objects.requireNonNull(cache);
//        Objects.requireNonNull(settings);
//
//        if (isStateless(value)) {
//            return cache.retrieve(value, c -> createClassInstance(c, testInstance, settings), settings);
//        } else {
//            return suppress(() -> createClassInstance(value, testInstance, settings));
//        }
//    }
//
//    private static <T extends IgnoreCondition> T createClassInstance(
//            Class<T> clazz,
//            Object testInstance,
//            String... settings) {
//
//        final Constructor factory;
//        final boolean useTestInstance;
//        if (clazz.getDeclaringClass() == null) {
//            Constructor<?> con = Arrays.stream(clazz.getDeclaredConstructors()).filter(c -> c.getParameterCount() == 0).
//                    map(c -> {
//                        c.setAccessible(true);
//                        return c;
//                    }).findFirst().orElse(null);
//
//            if (con != null) {
//                useTestInstance = false;
//            } else {
//                con = Arrays.stream(clazz.getDeclaredConstructors()).
//                        filter(c -> c.getParameterCount() == 1 && c.getParameterTypes()[0] == testInstance.getClass()).
//                        map(c -> {
//                            c.setAccessible(true);
//                            return c;
//                        }).findFirst().orElse(null);
//                useTestInstance = true;
//            }
//            if (con == null) {
//                throw new IllegalStateException("Class " + clazz + " does not have no-args constructor");
//            }
//            factory = con;
//
//        } else if (Modifier.isStatic(clazz.getModifiers())) {
//            factory = Arrays.stream(clazz.getDeclaredConstructors()).filter(c -> c.getParameterCount() == 0).
//                    map(c -> {
//                        c.setAccessible(true);
//                        return c;
//                    }).findFirst().orElseThrow(
//                    () -> new IllegalStateException("Class " + clazz + " does not have no-args constructor"));
//            useTestInstance = false;
//        } else {
//            Objects.requireNonNull(testInstance);
//            factory = Arrays.stream(clazz.getDeclaredConstructors()).
//                    filter(c -> c.getParameterCount() == 1 && c.getParameterTypes()[0] == testInstance.getClass()).
//                    map(c -> {
//                        c.setAccessible(true);
//                        return c;
//                    }).findFirst().orElseThrow(
//                    () -> new IllegalStateException("Class " + clazz + " does not have no-args constructor"));
//            useTestInstance = true;
//        }
//
//        //noinspection unchecked,UnnecessaryLocalVariable
//        T t = suppress(() -> (T) (useTestInstance ? factory.newInstance(testInstance) : (T) factory.newInstance()));
//
//        if (settings.length > 0) {
//            fillProperties(t, settings);
//        }
//        return t;
//    }
//
//    private static void fillProperties(Object o, String[] settings) {
//
//        class Parsed {
//            private final String name;
//            private final String rawValue;
//            private final boolean nullValue;
//
//
//            Parsed(String name, String rawValue, boolean nullValue) {
//                this.name = Objects.requireNonNull(name);
//                this.rawValue = nullValue ? rawValue : Objects.requireNonNull(rawValue);
//                this.nullValue = nullValue;
//            }
//
//            public String name() {
//                return name;
//            }
//
//            private String rawValue() {
//                return rawValue;
//            }
//
//            private boolean nullValue() {
//                return nullValue;
//            }
//        }
//
//        class Setter {
//            private final MethodCaller caller;
//            private final Object object;
//            private final Object value;
//
//            private Setter(MethodCaller caller, Object object, Object value) {
//                this.caller = Objects.requireNonNull(caller);
//                this.object = Objects.requireNonNull(object);
//                this.value = value;
//            }
//
//            private void setVal() {
//                caller.invoke(object, value);
//            }
//
//        }
//
//        class Accumulator {
//            private final Map<String, List<Method>> methods = new HashMap<>();
//
//            private Accumulator() {
//            }
//
//            private Accumulator(Method method) {
//                Objects.requireNonNull(method);
//                this.methods.put(method.getName(), Arrays.asList(method));
//            }
//
//            public Accumulator merge(Accumulator accumulator) {
//                Accumulator result = new Accumulator();
//
//                for (Map.Entry<String, List<Method>> entry : this.methods.entrySet()) {
//                    result.methods.putIfAbsent(entry.getKey(), new ArrayList<>());
//                    result.methods.get(entry.getKey()).addAll(entry.getValue());
//                }
//
//                for (Map.Entry<String, List<Method>> entry : accumulator.methods.entrySet()) {
//                    result.methods.putIfAbsent(entry.getKey(), new ArrayList<>());
//                    result.methods.get(entry.getKey()).addAll(entry.getValue());
//                }
//
//                return result;
//            }
//
//        }
//
//        Objects.requireNonNull(o);
//        Objects.requireNonNull(settings);
//
//        LazyEval<Map<String, List<Method>>> setters = LazyEval.eval(
//                () -> Stream.of(o.getClass().getMethods())
//                        .filter((m) -> !Modifier.isStatic(m.getModifiers()))
//                        .filter((m) -> Modifier.isPublic(m.getModifiers()))
//                        .filter((m) -> m.getParameterTypes().length == 1)
//                        .filter((m) -> m.getReturnType() == Void.TYPE)
//                        .filter((m) -> m.getName().startsWith("set"))
//                        .map(Accumulator::new)
//                        .reduce(Accumulator::merge)
//                        .orElse(new Accumulator())
//                        .methods);
//
//        LazyEval<List<String>> allProps = LazyEval.eval(() ->
//                setters.value().values().
//                        stream()
//                        .reduce(Collections.emptyList(), IgnoreExtensionV6::mergeLists)
//                        .stream()
//                        .map((m) -> {
//                            String name = m.getName().substring(3);
//                            name = name.substring(0, 1).toLowerCase() + name.substring(1);
//                            return m.getParameterTypes()[0].getTypeName() + " " + name;
//                        })
//                        .collect(Collectors.toList()));
//
//
//        Arrays.stream(settings).
//                filter(Objects::nonNull).
//                filter(s -> !s.trim().isEmpty()).
//                filter(s -> s.contains(EQUAL_SIGN) || s.contains(NULL_SIGN)).
//                map((s) -> {
//                    if (s.contains(NULL_SIGN)) {
//                        int indexOf = s.indexOf(NULL_SIGN);
//                        return new Parsed(s.substring(0, indexOf), null, true);
//                    } else if (s.contains(EQUAL_SIGN)) {
//                        int indexOf = s.indexOf(EQUAL_SIGN);
//                        return new Parsed(s.substring(0, indexOf), s.substring(indexOf + 1, s.length()), false);
//                    } else {
//                        throw new IllegalStateException("illegal property record " + s);
//                    }
//                }).
//                map((p) -> {
//                    String methodName = "set" + p.name().substring(0, 1).toUpperCase() +
//                            p.name().substring(1, p.name().length());
//                    List<Method> methods = setters.value().get(methodName);
//                    if (methods == null) {
//                        throw new IllegalStateException("property " + p.name() + " not found." +
//                                " Possible properties are " + allProps.value());
//                    }
//
//                    List<Setter> list = new ArrayList<>();
//                    for (Method method : methods) {
//                        if (method.getParameterTypes()[0].isPrimitive() && p.nullValue()) {
//                            throw new IllegalStateException("cannot assign null to property " + p.name() + " " +
//                                    " Possible properties are " + allProps.value());
//                        }
//
//                        Object value = p.nullValue() ? null : convert(method.getParameterTypes()[0], p.rawValue());
//                        list.add(new Setter(new MethodUtils.BaseMethodCaller(method), o, value));
//
//                    }
//                    return list;
//
//                }).reduce(Collections.emptyList(), IgnoreExtensionV6::mergeLists).
//                forEach(Setter::setVal);
//
//    }
//
//
//    private static boolean isStateless(Class<?> clazz) {
//        Objects.requireNonNull(clazz);
//        Class<?> currentClass = clazz;
//        while (currentClass != null) {
//            Cached annotation = currentClass.getAnnotation(Cached.class);
//            if (annotation != null) {
//                return annotation.value();
//            }
//            currentClass = currentClass.getSuperclass();
//        }
//        return false;
//    }
//
//    private static Object convert(Class typeParam, String value) {
//        Objects.requireNonNull(typeParam);
//        Objects.requireNonNull(value);
//
//        if (typeParam == String.class) {
//            return value;
//        } else if (typeParam == Boolean.TYPE) {
//            return parseBoolean(value);
//        } else if (typeParam == Boolean.class) {
//            return parseBoolean(value);
//        } else if (typeParam == Byte.TYPE) {
//            return Byte.parseByte(value);
//        } else if (typeParam == Byte.class) {
//            return Byte.parseByte(value);
//        } else if (typeParam == Short.TYPE) {
//            return Short.parseShort(value);
//        } else if (typeParam == Short.class) {
//            return Short.parseShort(value);
//        } else if (typeParam == Integer.TYPE) {
//            return Integer.parseInt(value);
//        } else if (typeParam == Integer.class) {
//            return Integer.parseInt(value);
//        } else if (typeParam == Long.TYPE) {
//            return Long.parseLong(value);
//        } else if (typeParam == Long.class) {
//            return Long.parseLong(value);
//        } else if (typeParam == Float.TYPE) {
//            return Float.parseFloat(value);
//        } else if (typeParam == Float.class) {
//            return Float.parseFloat(value);
//        } else if (typeParam == Double.TYPE) {
//            return Double.parseDouble(value);
//        } else if (typeParam == Double.class) {
//            return Double.parseDouble(value);
//        } else if (typeParam == BigDecimal.class) {
//            return new BigDecimal(value);
//        } else if (typeParam == BigInteger.class) {
//            return new BigInteger(value);
//        }
//
//        throw new IllegalStateException("type " + typeParam + " is not supported");
//    }
//
//    private static boolean parseBoolean(String value) {
//        if ("false".equalsIgnoreCase(value)) {
//            return false;
//        } else if ("true".equalsIgnoreCase(value)) {
//            return true;
//        }
//        throw new IllegalStateException(" " + value + " is not valid boolean value");
//    }
//
//    private static <T> List<T> mergeLists(List<T> l1, List<T> l2) {
//        List<T> list = new ArrayList<>();
//        list.addAll(l1);
//        list.addAll(l2);
//        return list;
//    }
//
//
//}
