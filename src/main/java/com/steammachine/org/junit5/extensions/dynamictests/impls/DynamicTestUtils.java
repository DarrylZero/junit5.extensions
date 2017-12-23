package com.steammachine.org.junit5.extensions.dynamictests.impls;

import com.steammachine.org.junit5.extensions.dynamictests.TestInstanceFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created 25/10/16 15:53
 *
 * @author Vladimir Bogodukhov
 **/
public class DynamicTestUtils {

    public static final List<Class> OBJECT_PRIMITIVES = Arrays.asList(
            Boolean.class, Byte.class, Short.class, Integer.class, Long.class,
            Float.class, Double.class, Character.class, String.class);

    public static final List<Class> OBJECT_PRIMITIVES_STRING = Arrays.asList(
            Boolean.TYPE,
            Boolean.class,

            Byte.TYPE,
            Byte.class,

            Short.TYPE,
            Short.class,

            Integer.TYPE,
            Integer.class,

            Long.TYPE,
            Long.class,

            Float.TYPE,
            Float.class,

            Double.TYPE,
            Double.class,

            Character.TYPE,
            Character.class,

            String.class);

    public static class PrimTypeNames {
        public static final String BooleanTYPE = "" + Boolean.TYPE.getName();
        public static final String BooleanСlass = "" + Boolean.class.getName();
        public static final String ByteTYPE = "" + Byte.TYPE.getName();
        public static final String ByteClass = "" + Byte.class.getName();
        public static final String ShortTYPE = "" + Short.TYPE.getName();
        public static final String ShortClass = "" + Short.class.getName();
        public static final String IntegerTYPE = "" + Integer.TYPE.getName();
        public static final String IntegerClass = "" + Integer.class.getName();
        public static final String LongTYPE = "" + Long.TYPE.getName();
        public static final String LongClass = "" + Long.class.getName();
        public static final String FloatTYPE = "" + Float.TYPE.getName();
        public static final String FloatClass = "" + Float.class.getName();
        public static final String DoubleTYPE = "" + Double.TYPE.getName();
        public static final String DoubleClass = "" + Double.class.getName();
        public static final String CharacterTYPE = "" + Character.TYPE.getName();
        public static final String CharacterClass = "" + Character.class.getName();
        public static final String StringClass = "" + String.class.getName();
    }

    public static List<DynamicTest> generateAsList(
            Object nexus,
            List<Object[]> paramsCandidates,
            List<Class> paramTypes,
            TestInstanceFactory testInstanceFactory) {


        class MethodHolder {
            final Method method;
            final List<List<Object>> callParams;

            private MethodHolder(Method method, List<List<Object>> callParams) {
                this.method = Objects.requireNonNull(method);
                this.callParams = Objects.requireNonNull(callParams);
            }

            public Method method() {
                return method;
            }

            public List<List<Object>> callParamsSet() {
                return callParams;
            }
        }

        Objects.requireNonNull(nexus, "nexus is null");
        Objects.requireNonNull(paramsCandidates, "params is null");
        Objects.requireNonNull(paramTypes, "paramTypes is null");
        Objects.requireNonNull(testInstanceFactory, "testInstanceFactory is null");


        Stream.Builder<Class<?>> builder = Stream.builder();
        Class<?> clazz = nexus.getClass();
        while (clazz != null) {
            builder.add(clazz);
            clazz = clazz.getSuperclass();
        }

        return builder.build().flatMap(cl -> Stream.of(cl.getDeclaredMethods())).
                filter(method -> !Modifier.isStatic(method.getModifiers())).
                filter(method -> !Modifier.isPrivate(method.getModifiers())).
                filter(method -> !Modifier.isNative(method.getModifiers())).
                filter(method -> method.getDeclaringClass() != Object.class).
                filter(method -> method.getReturnType() == Void.TYPE).
                filter(method -> method.getParameterCount() == paramTypes.size()).
                map(method -> {
                    List<List<Object>> params = getParamList(paramsCandidates, method);
                    return !params.isEmpty() ? new MethodHolder(method, params) : null;
                }).filter(Objects::nonNull).
                flatMap(mh -> dynamicTestStream(nexus, paramTypes, testInstanceFactory, mh.callParamsSet(), mh.method())).
                collect(Collectors.toList());
    }

    private static Stream<? extends DynamicTest> dynamicTestStream(
            Object nexus,
            List<Class> paramTypes,
            TestInstanceFactory testInstanceFactory,
            List<List<Object>> callParamsSet,
            Method method) {
        Stream.Builder<DynamicTest> sb = Stream.builder();
        int j = 0;
        for (List<Object> callParams : callParamsSet) {
            sb.add(createDynamicTest(testInstanceFactory, nexus, method, paramTypes, callParams, j++));
        }
        return sb.build();
    }

    private static List<List<Object>> getParamList(List<Object[]> paramsCandidates, Method method) {
        List<List<Object>> params = new ArrayList<>();

        for (Object[] tp : paramsCandidates) {
            List<Object> paramCandidates = Arrays.asList(tp);
            if (isMethodCompatible(method, paramCandidates)) {
                params.add(paramCandidates);
            }
        }
        return params;
    }


    private static DynamicTest createDynamicTest(
            TestInstanceFactory testInstanceFactory,
            Object nexus,
            Method method,
            List<Class> paramTypes,
            List<Object> params,
            int position) {

        String displayName = methodWithParams(method, params, position);
        return testInstanceFactory.newDynamicTest(displayName, formExec(nexus, method, params));
    }

    /**
     * Метод не делает проверок на соответстия параметров методу
     */
    private static String methodWithParams(Method method, List<Object> params, int position) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(params);

        StringBuilder builder = new StringBuilder();
        builder.append(method.getName()).append("(");
        builder.append(paramValues(params));
        builder.append(")");
        return "" + builder;
    }


    private static Executable formExec(Object nexus, Method method, List<Object> params) {
        Objects.requireNonNull(nexus);
        Objects.requireNonNull(method);
        Objects.requireNonNull(params);
        Object[] arguments = params.toArray(new Object[params.size()]);


        return () -> {
            if (!method.isAccessible()) {
                method.setAccessible(true);
                try {
                    try {
                        method.invoke(nexus, arguments);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    }
                } finally {
                    method.setAccessible(false);
                }
            }
        };
    }

    public static boolean isMethodCompatible(Method method, List<Object> paramCandidates) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(paramCandidates);

        if (method.getParameterCount() != paramCandidates.size()) {
            return false;
        }

        boolean result = true;
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            result = result && isTypeAssignableFromValue(parameterTypes[i], paramCandidates.get(i));
        }
        return result;
    }


    /**
     * Метод проверяет может ли быть установлено значение типа
     *
     * @param typeToAssignTo тип
     * @param value          значение
     * @return {@code true} если значение может быть установлено
     */
    @SuppressWarnings({"SimplifiableIfStatement", "unchecked"})
    public static boolean isTypeAssignableFromValue(Class typeToAssignTo, Object value) {
        Objects.requireNonNull(typeToAssignTo);

        if (value == null) {
/*
            Если значение null и тип примитивный и такое значение установить НЕЛЬЗЯ.
            Если значение null тип НЕ примитивный такое значение установить МОЖНО.
*/
            return !typeToAssignTo.isPrimitive();
        }

/*   Далее значение (value) не null.  */

        Class<?> typeToAssignFrom = value.getClass();

        if (typeToAssignTo == Object.class) {
            return true;
        }

        if (inGroup(typeToAssignTo, Boolean.TYPE, Boolean.class)) {
            return inGroup(typeToAssignFrom, Boolean.TYPE, Boolean.class);
        }

        if (inGroup(typeToAssignTo, Byte.TYPE, Byte.class)) {
            return inGroup(typeToAssignFrom, Byte.TYPE, Byte.class);
        }

        if (inGroup(typeToAssignTo, Short.TYPE, Short.class)) {
            return inGroup(typeToAssignFrom, Short.TYPE, Short.class);
        }

        if (inGroup(typeToAssignTo, Integer.TYPE, Integer.class)) {
            return inGroup(typeToAssignFrom, Integer.TYPE, Integer.class);
        }

        if (inGroup(typeToAssignTo, Long.TYPE, Long.class)) {
            return inGroup(typeToAssignFrom, Long.TYPE, Long.class);
        }

        if (inGroup(typeToAssignTo, Float.TYPE, Float.class)) {
            return inGroup(typeToAssignFrom, Float.TYPE, Float.class);
        }

        if (inGroup(typeToAssignTo, Double.TYPE, Double.class)) {
            return inGroup(typeToAssignFrom, Double.TYPE, Double.class);
        }

        if (inGroup(typeToAssignTo, Character.TYPE, Character.class)) {
            return inGroup(typeToAssignFrom, Character.TYPE, Character.class);
        }

        return typeToAssignTo.isAssignableFrom(typeToAssignFrom);
    }

    static boolean inGroup(Class<?> type, Class<?>... group) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(group);
        for (Class<?> item : group) {
            if (type == item) {
                return true;
            }
        }
        return false;
    }

    public static String typeName(Class<?> type) {
        Objects.requireNonNull(type);
        if (OBJECT_PRIMITIVES.contains(type)) {
            return type.getTypeName().substring(type.getTypeName().lastIndexOf(".") + 1);
        }
        return type.getTypeName();
    }


    public static String paramValues(
            List<Object> paramSet,
            List<Class> paramTypes) {
        Objects.requireNonNull(paramSet);
        Objects.requireNonNull(paramTypes);

        if (paramSet.size() != paramTypes.size()) {
            throw new IllegalStateException("paramSet.size() != paramTypes.size()");
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < paramSet.size(); i++) {
            b.append(b.length() > 0 ? ", " : "");

            Object value = paramSet.get(i);
            b.append(value).append(":").append(typeName(paramTypes.get(i))).
                    append(value == null ? "" : ":" + typeName(value.getClass()));
        }
        return b.toString();
    }

    private static String paramValues(
            List<Object> paramSet) {

        Objects.requireNonNull(paramSet);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < paramSet.size(); i++) {
            builder.append(builder.length() > 0 ? ", " : "");

            final String value;
            if (paramSet.get(i) == null) {
                value = null;
            } else if (paramSet.get(i) instanceof Enum) {
                value = "" + className(paramSet.get(i).getClass()) + "." + paramSet.get(i);
            } else if (!OBJECT_PRIMITIVES_STRING.contains(paramSet.get(i).getClass())) {
                value = "" + paramSet.get(i);
            } else {
                switch (paramSet.get(i).getClass().getName()) {
                    case "boolean":
                    case "java.lang.Boolean": {
                        value = "" + paramSet.get(i);
                        break;
                    }

                    case "byte":
                    case "java.lang.Byte": {
                        value = "" + paramSet.get(i);
                        break;
                    }

                    case "short":
                    case "java.lang.Short": {
                        value = "" + paramSet.get(i);
                        break;
                    }

                    case "int":
                    case "java.lang.Integer": {
                        value = "" + paramSet.get(i);
                        break;
                    }

                    case "long":
                    case "java.lang.Long": {
                        value = "" + paramSet.get(i) + "L";
                        break;
                    }

                    case "float":
                    case "java.lang.Float": {
                        value = "" + paramSet.get(i);
                        break;
                    }

                    case "double":
                    case "java.lang.Double": {
                        value = "" + paramSet.get(i);
                        break;
                    }

                    case "char":
                    case "java.lang.Character": {
                        value = "'" + paramSet.get(i) + "'";
                        break;
                    }

                    case "java.lang.String": {
                        value = "\"" + paramSet.get(i) + "\"";
                        break;
                    }

                    default: {
                        throw new IllegalStateException("unknown type " + paramSet.get(i).getClass().getName());
                    }
                }
            }
            builder.append(value);

        }


        return "" + builder;
    }

    private static String className(Class<?> aClass) {
        Objects.requireNonNull(aClass);

        List<String> classNames = new ArrayList<>();
        Class<?> tmp = aClass;
        while (tmp != null) {
            classNames.add(0, tmp.getSimpleName());
            tmp = tmp.getDeclaringClass();
        }

        StringBuilder builder = new StringBuilder();
        for (String className : classNames) {
            builder.append(builder.length() > 0 ? "." : "");
            builder.append(className);
        }
        return "" + builder;
    }

    @SuppressWarnings("unchecked")
    public static List<Class> checkTypes(List<Object[]> params) {
        Objects.requireNonNull(params);

        List<Class> types = null;
        for (Object[] group : params) {
            if (types == null) {
                types = new ArrayList<>();
                for (int i = 0; i < group.length; i++) {
                    types.add(group[i] != null ? group[i].getClass() : null);
                }
            } else {
                if (group.length != types.size()) {
                    throw new IllegalArgumentException("param length must be equal for all elements");
                }

                for (int i = 0; i < group.length; i++) {
                    if (types.get(i) == null) {
                        types.set(i, group[i].getClass());
                        continue;
                    }

                    if (group[i] != null && types.get(i) != null) {
                        if (!group[i].getClass().isAssignableFrom(types.get(i)) &&
                                !types.get(i).isAssignableFrom(group[i].getClass())) {
                            throw new IllegalArgumentException("params contain incompatible types " +
                                    group[i].getClass() + " and " + types.get(i) + "  at index " + i);
                        }

                        if (!types.get(i).isAssignableFrom(group[i].getClass()) &&
                                group[i].getClass().isAssignableFrom(types.get(i))) {
                            types.set(i, group[i].getClass());
                        }
                    }
                }
            }
        }
        return types == null ? Collections.emptyList() : types;
    }


}
