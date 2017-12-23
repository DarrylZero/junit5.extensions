/*
package org.junit.platform.launcher.core;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import ru.socialquantum.common.utils.commonutils.CommonUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

*/
/**
 * Created 12/10/16 09:12
 *
 * @author Vladimir Bogodukhov
 **//*

public class HackUtilsV2 {

    public interface Link {
        void disconnect();
    }

    private static final class TestExecutionListenerNexus implements TestExecutionListener {
        private final List<TestExecutionListener> listeners = new ArrayList<>();
        public void registerListener(TestExecutionListener customListener) {
            listeners.add(0, customListener);
        }
        public void unRegisterListener(TestExecutionListener customListener) {
            listeners.remove(customListener);
        }


        */
/* -------------------------------------------------------------------------------------------------- *//*


        @Override
        public void testPlanExecutionStarted(TestPlan testPlan) {
            runListeners((l) -> l.testPlanExecutionStarted(testPlan));
        }

        @Override
        public void testPlanExecutionFinished(TestPlan testPlan) {
            runListeners((l) -> l.testPlanExecutionFinished(testPlan));
        }

        @Override
        public void dynamicTestRegistered(TestIdentifier testIdentifier) {
            runListeners((l) -> l.dynamicTestRegistered(testIdentifier));
        }

        @Override
        public void executionSkipped(TestIdentifier testIdentifier, String reason) {
            runListeners((l) -> l.executionSkipped(testIdentifier, reason));
        }

        @Override
        public void executionStarted(TestIdentifier testIdentifier) {
            runListeners((l) -> l.executionStarted(testIdentifier));
        }

        @Override
        public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
            runListeners((l) -> l.executionFinished(testIdentifier, testExecutionResult));
        }

        @Override
        public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
            runListeners((l) -> l.reportingEntryPublished(testIdentifier, entry));
        }

        */
/**
         * Алгортим изменен - предыдущая реализация расположены выше.
         * В данном случае берется текущий набор всех notifiers и после этого переносится в временный список,
         * затем отрабатывает код для каждого случая.
         *
         * @param action -
         *//*

        private void runListeners(Consumer<TestExecutionListener> action) {
            new ArrayList<>(listeners).forEach((i) -> suppressErrors(() -> action.accept(i)));
        }
    }

    @FunctionalInterface
    private interface Procedure {
        void execute() throws Throwable;
    }

    private static void suppressErrors(Procedure procedure) {
        Objects.requireNonNull(procedure);
        try {
            procedure.execute();
        } catch (Throwable ignored) {
        }
    }

    private static class AgentI implements Link {
        private final TestExecutionListener customListener;
        private final TestExecutionListenerNexus nexus;

        AgentI(TestExecutionListener customListener, TestExecutionListenerNexus nexus) {
            this.nexus = Objects.requireNonNull(nexus);
            this.customListener = Objects.requireNonNull(customListener);
        }

        public AgentI connect() {
            this.nexus.registerListener(this.customListener);
            return this;
        }

        @Override
        public void disconnect() {
            this.nexus.unRegisterListener(this.customListener);
        }

        @Override
        public String toString() {
            return "AgentI{" +
                    "customListener=" + customListener +
                    ", nexus=" + nexus +
                    '}';
        }
    }


    public static Link hackTestExtensionContext(
            TestExtensionContext context,
            TestExecutionListener customListener) {

        return hackContext(context, customListener);
    }

    public static Link hackContainerExtensionContext(
            ContainerExtensionContext context,
            TestExecutionListener customListener) {
        return hackContext(context, customListener);
    }


    */
/*  ------------------------------------------------ privates  ------------------------------------------------ *//*


    private static Link hackContext(Object context, TestExecutionListener customListener) {
        Objects.requireNonNull(customListener);
        Objects.requireNonNull(context);

        try {
            List<EngineExecutionListener> fields = getFieldValue(context, EngineExecutionListener.class);
            if (fields.size() != 1) {
                throw new IllegalStateException("fields.size() != 1");
            }
            EngineExecutionListener engineExecutionListener = fields.get(0);

            List<TestExecutionListener> list = getFieldValue(engineExecutionListener, TestExecutionListener.class);
            if (list.size() != 1) {
                throw new IllegalStateException("unmodifiedList.size() != 1");
            }
            TestExecutionListener testExecutionListener = list.get(0);
            Object registry = findField(testExecutionListener, "this$0", Object.class);

            List<List> listList = getFieldValue(registry, List.class);
            if (listList.size() != 1) {
                throw new IllegalStateException("listList.size() != 1");
            }

            @SuppressWarnings("unchecked")
            List<TestExecutionListener> listeners = (List<TestExecutionListener>) listList.get(0);
        */
/* here we have originalListener unmodifiedList *//*


            final TestExecutionListenerNexus nexus;
            if (listeners.stream().noneMatch((i) -> i.getClass() == TestExecutionListenerNexus.class)) {
            */
/* there is no listener we add it only once *//*

                nexus = new TestExecutionListenerNexus();
                listeners.add(nexus);
            } else {
            */
/* here we check that there is only one < then we add only one specific listener *//*

                nexus = listeners.stream().
                        filter((i) -> i.getClass() == TestExecutionListenerNexus.class).
                        map(i -> (TestExecutionListenerNexus) i).
                        reduce(null, CommonUtils::reduceUnique);
                Objects.requireNonNull(nexus);
            }

            return new AgentI(customListener, nexus).connect();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }

    private static <T> List<T> getFieldValue(Object object, Class<T> fieldType) throws IllegalAccessException {
        List<T> list = new ArrayList<T>();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                if (isPrivate) {
                    field.setAccessible(true);
                }
                Object value = field.get(object);
                if (isPrivate) {
                    field.setAccessible(false);
                }

                if (value != null && fieldType.isAssignableFrom(value.getClass())) {
                    list.add(fieldType.cast(value));
                }
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    private static <T> T findField(Object object, String fieldName, Class<T> fieldType) throws IllegalAccessException, NoSuchFieldException {
        Objects.requireNonNull(fieldName);
        Stream.Builder<Field> builder = Stream.builder();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Stream.of(clazz.getDeclaredFields()).forEach(builder);
            clazz = clazz.getSuperclass();
        }
        Stream<Field> stream = builder.build();
        return stream.filter((f) -> fieldName.equals(f.getName())).
                map((f) -> fieldValue(object, f)).
                map((o) -> o != null && fieldType.isAssignableFrom(o.getClass()) ? fieldType.cast(o) : null).
                filter(Objects::nonNull).
                findFirst().orElseThrow(IllegalStateException::new);
    }

    private static <T> T findFieldOld(Object object, String fieldName, Class<T> fieldType) throws IllegalAccessException, NoSuchFieldException {
        Objects.requireNonNull(fieldName);
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fieldName.equals(field.getName())) {
                    continue;
                }

                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                if (isPrivate) {
                    field.setAccessible(true);

                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.PRIVATE);
                }

                Object value = field.get(object);

                if (isPrivate) {
                    field.setAccessible(false);
                }


                if (value != null && fieldType.isAssignableFrom(value.getClass())) {
                    return fieldType.cast(value);
                }
            }
            clazz = clazz.getSuperclass();
        }

        throw new IllegalStateException();
    }

    private static Object fieldValue(Object object, Field f) {
        return CommonUtils.suppress(() -> {
            boolean isPrivate = Modifier.isPrivate(f.getModifiers());
            if (isPrivate) {
                f.setAccessible(true);

                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.PRIVATE);
            }

            Object value = f.get(object);
            if (isPrivate) {
                f.setAccessible(false);
            }

            return value;
        });
    }

}
*/
