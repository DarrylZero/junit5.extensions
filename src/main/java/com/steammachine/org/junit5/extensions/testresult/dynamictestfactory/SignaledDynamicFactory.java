//package com.steammachine.org.junit5.extensions.testresult.dynamictestfactory;
//
//import com.steammachine.org.junit5.extensions.testresult.callbacks.CommonCallBacks;
//import com.steammachine.org.junit5.extensions.testresult.implementation.MethodData;
//import org.junit.jupiter.api.DynamicTest;
//import org.junit.jupiter.api.function.Executable;
//import ru.socialquantum.common.utils.commonutils.CommonUtils;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.CallBacksSingleton;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.DynamicTestCallBackKey;
//
//import java.lang.reflect.Method;
//import java.util.Objects;
//import java.util.function.Function;
//
///**
// * Created 19/12/16 11:45
// *
// * @author Vladimir Bogodukhov
// *
// * UNDER_CONSTRUCTION!!!
// **/
//public class SignaledDynamicFactory {
//
//    private static final CommonCallBacks callBacks = CallBacksSingleton.instance();
//
//    private static class TypeParamsI implements TypeParams {
//        private final Executable executable;
//        private final String displayName;
//
//        private TypeParamsI(Executable executable, String displayName) {
//            this.executable = Objects.requireNonNull(executable);
//            this.displayName = Objects.requireNonNull(displayName);
//        }
//
//        @Override
//        public Executable executable() {
//            return executable;
//        }
//
//        @Override
//        public String displayName() {
//            return displayName;
//        }
//    }
//
//    /**
//     * @param displayName -
//     * @param executable  -
//     * @return -
//     * UNDER_CONSTRUCTION!!!
//     */
//    public static <T> DynamicTest dynamicTest(
//            String displayName,
//            Executable executable,
//            Function<TypeParams, TypeHelper<T>> function) {
//        Objects.requireNonNull(function);
//        Objects.requireNonNull(displayName);
//        TypeHelper<T> helper = function.apply(new TypeParamsI(executable, displayName));
//        if (helper != TypeHelper.NULL_HELPER) {
//            executable = new SignaledExecutable<>(callBacks, executable, helper.key(), helper.info());
//        }
//        return DynamicTest.dynamicTest(displayName, executable);
//    }
//
//    public static DynamicTest dynamicTest(
//            String displayName,
//            Executable exec) {
//
//        return SignaledDynamicFactory.dynamicTest(displayName, exec, typeParams -> {
//            MethodData data = MethodData.data(typeParams.executable().getClass(), typeParams.displayName(),
//                    getExecutedMethod(typeParams.executable().getClass()));
//
//            return new TypeHelper<MethodData>() {
//                @Override
//                public Object key() {
//                    return DynamicTestCallBackKey.KEY;
//                }
//
//                @Override
//                public MethodData info() {
//                    return data;
//                }
//            };
//        });
//    }
//
//    private static Method getExecutedMethod(Class<?> clazz) {
//        return CommonUtils.suppress(() -> clazz.getMethod("execute"));
//    }
//
//}
