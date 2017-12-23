package com.steammachine.org.junit5.extensions.expectedexceptions;

import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация тестовых методов Junit5 для пометки ожидаемых исключений.
 *
 * <p>
 * <p>
 * <p>
 * {@link Expected}
 *
 * @author Vladimir Bogodukhov
 *         Created 26/09/16 18:19
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ExpectedExceptionsExtension.class)
@Api(APILevel.stable)
public @interface Expected {


    /**
     * Список классов ожидаемых исключений.
     *
     *
     * @return Список классов ожидаемых исключений.
     */
    Class<? extends Throwable>[] expected() default {};

    /**
     * Признак точного совпадения типа исключения.
     * в случае {@code true} ожидается, что тип исключения, выбрасываемого из метода теста
     * точно совпадает с одним из элементов {@link #expected()}
     *
     * в случае {@code false} ожидается что тип исключения, выбрасываемого из метода теста может быть
     * как либо одним из одним из элементов {@link #expected()} либо одним из наследников
     *
     *
     * @return exact exception class match
     */
    boolean matchExactType() default false;


}
