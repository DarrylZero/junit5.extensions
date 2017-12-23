package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.types.Api;
import com.steammachine.org.junit5.extensions.types.APILevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Aннотация - маркер для пометки классов условий как
 * сохраняемых в внутреннем кэше приложения.
 * <p>
 * Экземпляр класса с такой аннотацией и значением {@link Cached#value()} = {@code true}
 * создается и инициализируется один раз, для каждого набора параметров.
 * Если экземпляр для набора параметров уже был создан он берется из внутреннего кэша.
 * <p>
 * Аннотация с установленным значением наследуется от предка, наиболее близкого к данному классу.
 * <p>
 * Если класс и ни один из его предков не аннотирован {@link Cached} ({@code true})
 *
 * Экземпляр такого класса не хранится в кэше и создается каждый раз.
 * <p>
 * <p>
 * Created 30/09/16 08:16
 *
 * @author Vladimir Bogodukhov
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Api(value = APILevel.experimental)
public @interface Cached {

    /**
     * @return значение
     * {@code true} экземрляр класса кэшируется
     * {@code false} экземрляр класса НЕ  кэшируется
     */
    boolean value() default true;
}
