//package com.steammachine.org.junit5.extensions.testresult.dynamictestfactory;
//
//
//import org.junit.jupiter.api.function.Executable;
//import com.steammachine.org.junit5.extensions.testresult.callbacks.Events;
//
//import java.util.Objects;
//
///**
// * Класс декоратор - вокруг Executable, обеспечивающий оповещение о результате Executable
// * Добавляет возможность оповещать о событиях завершения теста
// * <p>
// * <p>
// *
// * @author Vladimir Bogodukhov
// *         <p>
// *         UNDER_CONSTRUCTION!!!
// **/
//public class SignaledExecutable<T> implements Executable {
//
//    private final Executable wrappedNexus;
//    private final T info;
//    private final Object callKey;
//    private final Events events;
//
//    /**
//     * @param events  - объект отправки событий завершения выполнения
//     * @param nexus   - Executable, который декорируется
//     * @param callKey - ключ
//     * @param info    - дополнительная информация передаваемая о вызове
//     */
//    public SignaledExecutable(Events events, Executable nexus, Object callKey, T info) {
//        Objects.requireNonNull(nexus);
//        this.info = Objects.requireNonNull(info);
//        this.callKey = Objects.requireNonNull(callKey);
//        this.events = Objects.requireNonNull(events);
//
//        this.wrappedNexus = () -> {
//            try {
//                nexus.execute();
//                success();
//            } catch (Throwable throwable) {
//                failure(throwable);
//                throw throwable;
//            }
//        };
//    }
//
//    @Override
//    public void execute() throws Throwable {
//        wrappedNexus.execute();
//    }
//
//    private void success() {
//        events.fireSuccess(callKey, info);
//    }
//
//    private void failure(Throwable throwable) {
//        events.fireFailure(callKey, info, throwable);
//    }
//
//}
