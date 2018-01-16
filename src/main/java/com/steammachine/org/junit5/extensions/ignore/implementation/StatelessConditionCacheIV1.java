package com.steammachine.org.junit5.extensions.ignore.implementation;



import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;
import com.steammachine.org.junit5.extensions.ignore.IgnoreCondition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Vladimir Bogodukhov
 **/
@Api(value = State.EXPERIMENT)
public class StatelessConditionCacheIV1 implements StatelessConditionCache {

    private static final class Key<T> {
        private final List<String> params = new ArrayList<>();
        private final Class<T> clazz;
        private final int hashCode;

        private Key(Class<T> clazz, List<String> params) {
            this.clazz = Objects.requireNonNull(clazz);
            Objects.requireNonNull(params);
            this.params.addAll(params);

            this.hashCode = caleHashCode(this);
        }

        public Class<T> clazz() {
            return clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key<?> key = (Key<?>) o;

            if (params != null ? !params.equals(key.params) : key.params != null) return false;
            return clazz != null ? clazz.equals(key.clazz) : key.clazz == null;

        }

        public static int caleHashCode(Key key) {
            int result = key.params != null ? key.params.hashCode() : 0;
            result = 31 * result + (key.clazz != null ? key.clazz.hashCode() : 0);
            return result;
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return "Key{" +
                    "params=" + params +
                    ", clazz=" + clazz +
                    '}';
        }
    }

    private final ConcurrentHashMap<Key, IgnoreCondition> registry = new ConcurrentHashMap<>();

    public Map<Key, IgnoreCondition> registry() {
        return registry;
    }

    @Override
    public <T extends IgnoreCondition> T retrieve(
            Class<T> clazz,
            Function<Class<T>, T> function,
            String... params) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(function);

        Key<T> compositeKey = new Key<>(clazz, normalizeParams(params));
        //noinspection unchecked
        return (T) registry.computeIfAbsent(compositeKey, key -> function.apply(key.clazz()));
    }

    @Deprecated
    public <T extends IgnoreCondition> T retrieve(Class<T> clazz, Function<Class<T>, T> function) {
        return retrieve(clazz, function, (String[]) null);
    }

    private static List<String> normalizeParams(String... params) {
        if (params == null) {
            return Collections.emptyList();
        }

        ArrayList<String> result = new ArrayList<>(Arrays.stream(params).
                filter((s) -> s != null).
                filter((s) -> !s.trim().isEmpty()).collect(Collectors.toSet()));

        Collections.sort(result);
        return result;
    }


}
