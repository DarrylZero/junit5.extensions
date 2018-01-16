//package com.steammachine.org.junit5.extensions.guice.implementation.ver1;
//
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import com.google.inject.Module;
//import com.steammachine.org.junit5.extensions.guice.GuiceModules;
//import com.steammachine.org.junit5.extensions.guice.NullIterator;
//import com.steammachine.org.junit5.extensions.guice.NullModule;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// *
// * @author Vladimir Bogodukhov
// */
//public class GuiceJUnit5AssistantVersion1 {
//
//    private static class Bucket {
//        private final Injector injector;
//        private final List<Class<? extends Module>> modulesClasses;
//
//        private Bucket(Injector injector, List<Class<? extends Module>> modulesClasses) {
//            this.injector = Objects.requireNonNull(injector, "injector is null");
//            this.modulesClasses = modulesClasses;
//        }
//
//        public Injector injector() {
//            return injector;
//        }
//
//        public List<Class<? extends Module>> modulesClasses() {
//            return modulesClasses;
//        }
//    }
//
//    private static final Comparator<Class<? extends Module>> COMPARATOR =
//            new Comparator<Class<? extends Module>>() {
//                @Override
//                public int compare(Class<? extends Module> cls, Class<? extends Module> cls2) {
//                    return cls.getName().compareTo(cls2.getName());
//                }
//            };
//
//    /**
//     * Получение инжектора для совокупности объектов определенных
//     * в аннтации {@link GuiceModules}
//     *
//     * @param clazz - класс - в аннотации которого производится поиск.
//     * @return - инжектор или null.  null возвращается в случае если аннотация
//     * {@link GuiceModules} отсутствует в классе.
//     */
//    public static Injector getInjector(Class<?> clazz)  {
//        GuiceModules ann = clazz.getAnnotation(GuiceModules.class);
//        if (ann == null) {
//            return null;
//        }
//        final boolean iterators = !Arrays.asList(ann.iterators()).contains(NullIterator.class);
//        final boolean modules = !Arrays.asList(ann.value()).contains(NullModule.class);
//        if (iterators == modules) {
//            throw new IllegalStateException (
//                    "One of the fields - iterator() or (xor :) ) value() must be defined.");
//        }
//
//        final List<Class<? extends Module>> classesSet = listFromArray(
//                true, modules ? ann.value() : value(ann.iterators()));
//
//
////        Bucket bucket = spaces.computeIfAbsent(ann.space(), s -> {
////            Injector injector = modules ? createInjectorFor(ann.value()) : createInjectorFromIterators(ann.iterators());
////            return new Bucket(injector, classesSet);
////        });
//
//        Bucket bucket = spaces.get(ann.space());
//        if (bucket == null) {
//                /* Создаем инфу в первый раз */
//            Injector injector = modules ? createInjectorFor(ann.value()) : createInjectorFromIterators(ann.iterators());
//            bucket = new Bucket(injector, classesSet);
//            spaces.put(ann.space(), bucket);
//        } else if (!Objects.equals(bucket.modulesClasses(), classesSet)) {
//            /*  Данные инфы присутствуют, но состав классов модулей не идентичен */
//            throw new IllegalStateException("for space " + ann.space() + " modules are already defined " +
//                    bucket.modulesClasses());
//        }
//
//        //noinspection ConstantConditions
//        if (bucket == null) {
//            throw new IllegalStateException("bucket is null - somehow. ? ? ? ");
//        }
//
//        return bucket.injector();
//    }
//
//    /* -------------------------------------------- privates -------------------------------------------------- */
//
//    private static final Map<String, Bucket> spaces = new ConcurrentHashMap<>();
//
//
//    /**
//     * Метод зачистки всех конфигураций.
//     */
//    private static void forget() {
//        spaces.clear();
//    }
//
//    private static Injector createInjectorFor(Class<? extends Module>[] classes) {
////        Module[] modules = new Module[classes.length];
////        for (int i = 0; i < classes.length; i++) {
////            try {
////                modules[i] = classes[i].newInstance();
////            } catch (InstantiationException | IllegalAccessException e) {
////                throw new InitializationError(e);
////            }
////        }
////        return Guice.createInjector(modules);
//        List<Module> modules = Stream.of(classes).map(clazz -> CommonUtils.suppress(clazz::newInstance)).
//                collect(Collectors.toList());
//        return Guice.createInjector(modules.toArray(new Module[classes.length]));
//    }
//
//    private static Class<? extends Module>[] value(Class<?>[] iteratorClasses) {
//        List<Class<? extends Module>> resultModules = new ArrayList<>();
//        Objects.requireNonNull(iteratorClasses, "iteratorClasses is null");
//        for (Class<?> iteratorClass : iteratorClasses) {
//
//            boolean thereAreMethod = false;
//            for (Method method : iteratorClass.getDeclaredMethods()) {
//                if (!Modifier.isStatic(method.getModifiers())) {
//                    continue;
//                }
//                if (!Iterable.class.isAssignableFrom(method.getReturnType())) {
//                    continue;
//                }
//                if (method.getParameterTypes().length != 0) {
//                    continue;
//                }
//
//                Iterable iterable;
//                boolean accessible = method.isAccessible();
//                try {
//                    method.setAccessible(true);
//                    iterable = (Iterable) method.invoke(null);
//                } catch (InvocationTargetException e) {
//                    System.out.println(e);
//                    continue;
//                } catch (IllegalAccessException e) {
//                    System.out.println(e);
//                    continue;
//                } finally {
//                    method.setAccessible(accessible);
//                }
//
//                if (iterable == null) {
//                    throw new IllegalStateException("iterable == null somehow ? ? ? ");
//                }
//                /*  Данные получены - проверяем */
//
//                for (Object o : iterable) {
//                    if (o == null) {
//                        throw new IllegalStateException("class method " + iteratorClass.getName() +
//                                "#" + method.getName() + "()" + "returns null in one of elements");
//                    }
//
//                    if (!(o instanceof Class)) {
//                        throw new IllegalStateException("iterator gotten by class method " + iteratorClass.getName() +
//                                "#" + method.getName() + "()" + " returns not a Class object in one of its elements");
//                    }
//
//                    Class clz = (Class) o;
//                    //noinspection unchecked
//                    if (!Module.class.isAssignableFrom(clz)) {
//                        throw new IllegalStateException("iterator gotten by class method " + iteratorClass.getName() +
//                                "#" + method.getName() + "()" + " returns not a Class object in one of its elements");
//                    }
//                    //noinspection unchecked
//                    resultModules.add(clz);
//                    thereAreMethod = true;
//                }
//            }
//
//            if (!thereAreMethod) {
//                throw new IllegalStateException("there are no methods returning Iterable<Class<? extends Module>> " +
//                        "defined in class that ");
//            }
//        }
//
//        //noinspection unchecked
//        return resultModules.toArray(new Class[resultModules.size()]);
//    }
//
//    private static Injector createInjectorFromIterators(Class<?>[] iteratorClasses) {
//        return createInjectorFor(value(iteratorClasses));
//    }
//
//
//    @SafeVarargs
//    private static <T> List<T> listFromArray(boolean sort, Comparator<T> comparator, T... modulesClasses) {
//        Objects.requireNonNull(comparator);
//        List<T> result = new ArrayList<>(Arrays.asList(modulesClasses));
//
//        if (sort) {
//            result.sort(Objects.requireNonNull(comparator, "comparator is null"));
//        }
//
//        return Collections.unmodifiableList(result);
//    }
//
//    @SafeVarargs
//    private static List<Class<? extends Module>> listFromArray(
//            boolean sort,
//            Class<? extends Module>... modulesClss) {
//        return listFromArray(sort, COMPARATOR, modulesClss);
//    }
//
//
//}
