//package com.steammachine.org.junit5.extensions.guice;
//
//import com.google.inject.Module;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.lang.annotation.*;
//
///**
// *
// *
// * Аннотация для Junit5
// *
// * @author Vladimir Bogodukhov
// * {@link GuiceModules}
// **/
//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@ExtendWith(GuiceExtension.class)
//@Inherited
//public @interface GuiceModules {
//
//
//    /**
//     * Массив классов модулей инициализации объектов.
//     * Массив содержит ссылки на классы конфигурационных модулей.
//     * Класс модуля - любой класс поддерживающий интерфейс модуля конфигурации {@link Module}
//     *
//     * @return Массив модулей инициализации объектов. (всегда не null)
//     */
//    Class<? extends Module>[] value() default {NullModule.class};
//
//    /**
//     * Массив классов перечислителей.
//     * Каждый класс перечислитель, определенный в массиве, должен содержать, как минимум, один статический методов без параметров,
//     * каждый возвращает перечислитель классов модулей.  {@link Iterable}
//     * <p>
//     * <p>
//     * Пример класса и метода {@link NullIterator#iterator()}
//     *
//     * @return Класс перечислитель модулей.(всегда не null)
//     */
//    Class<?>[] iterators() default {NullIterator.class};
//
//    /**
//     * Строковый идентификатор конфигурации пространства объектов.
//     * Идентификатор пространства объектов однозначно определяет сконфигурированные объекты.
//     * Идентификатору должен соответсвовать фиксированный набор модулей конфигурации,
//     * определенный либо методом {@link #value()} либо методом {@link #iterators()}
//     * <p>
//     * Запись
//     *
//     * @return наименование пространства - любая не null строка
//     *
//     *
//     * @Junit5Guice(value = {Mod.class, Mod2.class,Mod3.class}, space = "SPACE")
//     * class SomeTest {
//     *     @Inject
//     *     private SomeField someField;
//     *     @Test
//     *     public void test() {
//     *    }
//     * }
//     * <p>
//     * Означает:
//     * Для класса SomeTest, при его создании и внедрении объектов (someField) будет использована конфигурация,
//     * определяемая классами модулей Mod, Mod2 и Mod3.
//     * Конфигурация имеет строковый идентификатор "SPACE"
//     * <p>
//     * Второй тест - определенный такой же конфигурацией
//     * {Mod.class, Mod2.class,Mod3.class}, space = "SPACE"
//     * Может раздалять все общие объекты конфигурации
//     * @Junit5Guice(value = {Mod.class, Mod2.class,Mod3.class}, space = "SPACE")
//     * class AnotherTest {
//     *    @Inject
//     *    private SomeField someField;
//     * }
//     */
//    String space();
//
//
//}