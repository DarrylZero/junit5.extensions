package com.steammachine.org.junit5.extensions.expectedexceptions;//package ru.socialquantum.junit5.addons.expectedexceptions;
//
//import org.junit.jupiter.api.extension.*;
//import org.junit.platform.commons.util.AnnotationUtils;
//import org.opentest4j.TestAbortedException;
//import APILevel;
//import Api;
//
//import java.lang.reflect.AnnotatedElement;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * Created 26/09/16 18:17
// *
// * @author Vladimir Bogodukhov
// **/
//@Api(APILevel.internal) class ExpectedExceptionsExtension
//        implements TestExecutionExceptionHandler, AfterTestExecutionCallback {
//
//    private final ExtensionContext.Namespace extensionNameSpace =
//            NamespaceFactory.createNameSpace("expected exceptions extension namespace", new Object());
//
//
//
//    @Override
//    public void handleTestExecutionException(TestExtensionContext context, Throwable throwable) throws Throwable {
//        if (getExpected(context) == null) {
//            return;
//        }
//        /* Тут подавляются все исключения. которые возникают в процесе вызоыва тестируемого метода */
//        context.getStore(extensionNameSpace).put(context.getTestMethod().get(), throwable);
//    }
//
//    @Override
//    public void afterTestExecution(TestExtensionContext context) throws Exception {
//        Expected expected = getExpected(context);
//        if (expected == null) {
//            return;
//        }
//
//        Throwable throwable = (Throwable) context.getStore(extensionNameSpace).remove(context.getTestMethod().get());
//        Class<? extends Throwable> throwableClass = throwable == null ? null : throwable.getClass();
//        if (matches(throwableClass, Arrays.asList(expected.expected()), expected.matchExactType())) {
//            /* тест выплнен с ошибкой - исключение брошено внутри теста, но оно ожидается  */
//
//            return;
//        }
//
//        final String throwableClassName = throwableClass != null ? throwableClass.getName() : null;
//        /* Исключение брошено в момент выполнения теста - но это исключение не ожидается */
//        throw new AssertionError("thrown exception [" + throwableClassName + "] must match one of the following:  " +
//                Stream.of(expected.expected()).filter(Objects::nonNull).
//                        map(Class::getName).collect(Collectors.toList()));
//    }
//
//
///* ---------------------------------------------------- privates -------------------------------------------- */
//
//    @Api(APILevel.internal)
//    private static Expected getExpected(TestExtensionContext context) {
//        Optional<AnnotatedElement> element = context.getElement();
//        if (!element.isPresent()) {
//            return null;
//        }
//        Optional<Expected> opAnnotation = AnnotationUtils.findAnnotation(element, Expected.class);
//        if (!opAnnotation.isPresent()) {
//            return null;
//        }
//        /*  ------------- до этой точки аннотации нет ------------- */
//        /* исключение БРОШЕНО */
//        return opAnnotation.get();
//    }
//
//
//    /**
//     * Проверить что исключение находится в списке ожидаемых.
//     *
//     * @param thr            - проверяемый тип ошибки
//     * @param exp            - список соответствий
//     * @param matchExactType - признак точного соответсвия -
//     *                       {@code true} - проверяемый тип ошибки должен точно соответствовать
//     *                       {@code false} -  проверяемый тип ошибки должен быть одним из предков
//     * @return
//     */
//    @Api(APILevel.internal)
//    static boolean matches(
//            Class<? extends Throwable> thr,
//            List<Class<? extends Throwable>> exp,
//            boolean matchExactType) {
//        Objects.requireNonNull(exp, "exp is null");
//        exp.forEach(Objects::requireNonNull);
//
//        if (thr == null) {
//            return exp.isEmpty();
//        } else {
//            return exp.stream().map(Objects::requireNonNull).collect(Collectors.toSet()).
//                    stream().anyMatch((i) -> isRightClass(thr, i, matchExactType));
//        }
//
//
//    }
//
//    @Api(APILevel.internal)
//    private static boolean isRightClass(Class<? extends Throwable> thr,
//                                        Class<? extends Throwable> exp,
//                                        boolean matchExactType) {
//        if (matchExactType) {
//            return exp == thr;
//        } else {
//            return exp.isAssignableFrom(thr);
//        }
//    }
//
//}