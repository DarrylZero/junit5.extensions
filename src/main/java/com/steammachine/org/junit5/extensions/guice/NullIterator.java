//package com.steammachine.org.junit5.extensions.guice;
//
//import com.google.inject.Module;
//
//import java.util.Collections;
//
///**
// * Класс пример, определяющий перечислители.
// * @author Vladimir Bogodukhov
// */
//public final class NullIterator {
//
//    /**
//     * Метод предоставляющий перечислитель  -
//     * Статический, возвращающий перечислитель Iterable<Module> и без параметров
//     *
//     * @return перечислитель
//     */
//    private static Iterable<Class<? extends Module>> iterator() {
//        return Collections.emptyList();
//    }
//
//    /**
//     * Метод предоставляющий перечислитель  -
//     * Статический, возвращающий перечислитель Iterable<Module> и без параметров
//     *
//     * @return перечислитель
//     */
//    private static Iterable<Class<? extends Module>> iterator2() {
//        return Collections.emptyList();
//    }
//
//    /**
//     * Метод НЕ предоставляющий перечислитель  -
//     * Статический, возвращающий перечислитель Iterable<Module>  с параметрами
//     *
//     * @return перечислитель
//     */
//    private static Iterable<Class<? extends Module>> iterator2(String param) {
//        return Collections.emptyList();
//    }
//
//    /**
//     * Метод НЕ предоставляющий перечислитель  -
//     * НЕ Статический, возвращающий перечислитель Iterable<Module> без параметров
//     *
//     * @return перечислитель
//     */
//    private Iterable<Class<? extends Module>> iterator3() {
//        return Collections.emptyList();
//    }
//}
