package com.steammachine.org.junit5.extensions.dynamictests.methodcomparator;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * <p>
 *
 * @author Vladimir Bogodukhov
 */
@Api(APILevel.internal)
class AsInSourceCodeMetodComparatorV3 implements MethodComparatorFactory {

    private static class IntrenalMethodComparator implements Comparator<Method> {
        private final Class<?> clazz;
        private final Map<String, ClassMethodTable.SourceCodePosition> map;

        private IntrenalMethodComparator(Class<?> clazz) {
            if (clazz == null) {
                throw new NullPointerException("clazz is null");
            }
            this.clazz = clazz;
            this.map = methodsMap(clazz);
        }

        @Override
        public int compare(Method meth, Method meth2) {
            if (meth == meth2) {
            /* Один и тот же метод */
                return 0;
            } else if (isInheritor(meth.getDeclaringClass(), meth2.getDeclaringClass())) {
                return -1;
            } else if (isInheritor(meth2.getDeclaringClass(), meth.getDeclaringClass())) {
                return 1;
            } else {
                checkClassesEqual(meth.getDeclaringClass(), meth2.getDeclaringClass());
                checkClassesEqual(clazz, meth2.getDeclaringClass());
            /* Определяющие классы равны */


                ClassMethodTable.SourceCodePosition pos = map.get(ClassMethodTable.methodSignature(meth));
                ClassMethodTable.SourceCodePosition pos2 = map.get(ClassMethodTable.methodSignature(meth2));

                if (pos == null && pos2 == null) {
                /* Инфы нет в обоих случаях */
                    return 0;
                } else if (pos != null && pos2 == null) {
                    return 1;
                } else if (pos == null && pos2 != null) {
                    return -1;
                } else if (!pos.hasPosition() && !pos2.hasPosition()) {
                    return 0;
                } else if (pos.hasPosition() && !pos2.hasPosition()) {
                    return 1;
                } else if (!pos.hasPosition() && pos2.hasPosition()) {
                    return -1;
                } else if (pos.lineNumber() == pos2.lineNumber()) {
                /* Methods are situated at the samе line */
                    return pos.order() - pos2.order();
                } else {

                    return pos.lineNumber() - pos2.lineNumber();
                }
            }
        }
    }



    AsInSourceCodeMetodComparatorV3() {
    }


    @Override
    public Comparator<Method> methodComparator(Class<?> clazz) {
        return new IntrenalMethodComparator(clazz);
    }


/*
     Compares its two arguments for order.
     Returns a

     negative integer,
     zero,
     or
     a positive integer


     as the
     first argument is

     less than,
     equal to, or
     greater than the second.
*/

    @Override
    public String toString() {
        return "AsInSourceCodeMetodComparatorV3";
    }


    /**
     * Проверить что класс classA является наследником класса classB - но не является им самим
     *
     * @param classA
     * @param classB
     * @return
     */
    private static boolean isInheritor(Class classA, Class classB) {
        Objects.requireNonNull(classA, "classA is null");
        Objects.requireNonNull(classB, "classB is null");
        if (classA == classB) {
            return false;
        }
        return classB.isAssignableFrom(classA);
    }

    private static void checkClassesEqual(Class classA, Class classB) {
        if (Objects.requireNonNull(classA) != Objects.requireNonNull(classB)) {
            throw new IllegalStateException("classes  must be the same");
        }
    }

    public static Map<String, ClassMethodTable.SourceCodePosition> methodsMap(Class clazz) {
        InputStream classStream = clazz.getResourceAsStream(clazz.getSimpleName() + ".class");
        if (classStream == null) {
            /* for classes build dynamically - it is not possible to load class data */
            return Collections.emptyMap();
        }

        try {
            try {
                return ClassMethodTable.readClassFromInputStream(classStream);
            } finally {
                classStream.close();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}