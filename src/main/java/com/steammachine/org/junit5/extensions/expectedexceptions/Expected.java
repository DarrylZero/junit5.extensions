package com.steammachine.org.junit5.extensions.expectedexceptions;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация тестовых методов Junit5 для пометки ожидаемых исключений.
 * <p>
 * <p>
 * <p>
 * <p>
 * {@link com.steammachine.org.junit5.extensions.expectedexceptions.Expected}
 * com.steammachine.org.junit5.extensions.expectedexceptions.Expected
 *
 * @author Vladimir Bogodukhov
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ExpectedExceptionsExtension.class)
@Api(State.MAINTAINED)
public @interface Expected {


    /**
     * Список классов ожидаемых исключений.
     *
     * @return Список классов ожидаемых исключений.
     */
    Class<? extends Throwable>[] expected() default {};

    /**
     * Признак точного совпадения типа исключения.
     * в случае {@code true} ожидается, что тип исключения, выбрасываемого из метода теста
     * точно совпадает с одним из элементов {@link #expected()}
     * <p>
     * в случае {@code false} ожидается что тип исключения, выбрасываемого из метода теста может быть
     * как либо одним из одним из элементов {@link #expected()} либо одним из наследников
     *
     * @return exact exception class match
     */
    boolean matchExactType() default false;


}
