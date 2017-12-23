//package com.steammachine.org.junit5.extensions.testresult.dynamictestfactory;
//
///**
// * Created 20/12/16 14:28
// *
// * @author Vladimir Bogodukhov
// **/
//public interface TypeHelper<T> {
//
//    TypeHelper NULL_HELPER = new TypeHelper() {
//        @Override
//        public Object key() {
//            return null;
//        }
//
//        @Override
//        public Object info() {
//            return null;
//        }
//    };
//
//    static <T> TypeHelper<T> NullHelper() {
//        //noinspection unchecked
//        return (TypeHelper<T>) NULL_HELPER;
//    }
//
//    /**
//     *
//     * Ключ
//     */
//    Object key();
//
//
//    /**
//     * Данные дополнительной информации
//     */
//    T info();
//}
//
