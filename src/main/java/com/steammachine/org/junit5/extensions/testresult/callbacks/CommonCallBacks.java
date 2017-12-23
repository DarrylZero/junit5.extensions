package com.steammachine.org.junit5.extensions.testresult.callbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created 19/12/16 11:58
 *
 * @author Vladimir Bogodukhov
 *         {@link CommonCallBacks}
 *
 *         UNDER_CONSTRUCTION
 **/
public class CommonCallBacks implements CallBacks, Events {

    private final Map<Object, List<CallBack<?>>> callBacks = new ConcurrentHashMap<>();

    @Override
    public <T> void register(Object key, CallBack<? extends T> callBack) {
        callBacks.putIfAbsent(key, new CopyOnWriteArrayList<>());
        callBacks.get(key).add(callBack);
    }

    @Override
    public <T> void unregister(Object key, CallBack<? extends T> callBack) {
        List<CallBack<?>> callBackSet = this.callBacks.get(key);
        if (callBackSet != null) {
            callBackSet.remove(callBack);
        }
    }

    @Override
    public <T> void unregisterForKey(Object key) {
        callBacks.remove(key);
    }

    @Override
    public <T> void fireSuccess(Object key, T info) {
        List<CallBack<?>> callBackSet = callBacks.get(key);
        if (callBackSet != null) {
            List<CallBack<?>> callBacks = new ArrayList<>(callBackSet);
            callBacks.stream().
                    filter((i) -> true).
                    forEach((c) -> safeCallSuccess(c, info));
        }
    }

    @Override
    public <T> void fireFailure(Object key, T info, Throwable throwable) {
        List<CallBack<?>> callBackSet = callBacks.get(key);
        if (callBackSet != null) {
            List<CallBack<?>> callBacks = new ArrayList<>(callBackSet);
            callBacks.stream().
                    filter((i) -> true).
                    forEach((c) -> safeCallFailure(c, info, throwable));
        }
    }

    /*  -------------------------------------------------------- privates ------------------------------------------ */

    private <T> void safeCallSuccess(CallBack callBack, T t) {
        Objects.requireNonNull(callBack);
        try {
            //noinspection unchecked
            callBack.railTestSuccess(t);
        } catch (Throwable ignored) {
            /* ignored */
        }
    }

    private <T> void safeCallFailure(CallBack callBack, T t, Throwable throwable) {
        Objects.requireNonNull(callBack);
        try {
            //noinspection unchecked
            callBack.railTestFailure(t, throwable);
        } catch (Throwable ignored) {
            /* ignored */
        }
    }

}
