package com.steammachine.org.junit5.extensions.dynamictests.dynamictestparam;

import com.steammachine.common.apilevel.Api;
import com.steammachine.common.apilevel.State;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Вспомогательный класс сбора параметров и генерации динамических тестов.
 * Позволяет в удобной и унифицированной форме оформить параметры динамического теста
 * <p>
 * <p>
 * <p>
 *
 * @author Vladimir Bogodukhov
 *         <p>
 *         {@link com.steammachine.org.junit5.extensions.dynamictests.dynamictestparam.DynamicTestParams}
 *         <p>
 *         <p>
 *         <a href="https://stackoverflow.com/questions/29216588/how-to-ensure-order-of-processing-in-java8-streams"> информация о порядке следования элементов </a>
 *         <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#Ordering"> информация о порядке следования элементов </a>
 *         <a href="https://ru.wikipedia.org/wiki/%D0%98%D0%B4%D0%B5%D0%BC%D0%BF%D0%BE%D1%82%D0%B5%D0%BD%D1%82%D0%BD%D0%BE%D1%81%D1%82%D1%8C> идемпотентная функция </a>
 **/
@Api(State.INCUBATING)
public class DynamicTestParams<T> {

    private static final class TestGroup<G> {
        private final String groupName;
        private final BiFunction<String, G, String> nameFactory;
        private final BiFunction<String, G, DynamicTest> executableFactory;
        private final Predicate<G> paramFilter;

        private TestGroup(
                String groupName,
                BiFunction<String, G, String> nameFactory,
                BiFunction<String, G, DynamicTest> executableFactory,
                Predicate<G> paramFilter) {
            this.groupName = Objects.requireNonNull(groupName);
            this.nameFactory = Objects.requireNonNull(nameFactory);
            this.executableFactory = Objects.requireNonNull(executableFactory);
            this.paramFilter = Objects.requireNonNull(paramFilter);
        }

        public String groupName() {
            return groupName;
        }

        public BiFunction<String, G, String> nameFactory() {
            return nameFactory;
        }

        public BiFunction<String, G, DynamicTest> executableFactory() {
            return executableFactory;
        }

        public Predicate<G> paramFilter() {
            return paramFilter;
        }
    }

    private static final boolean DEFAULT_CHECKNAME_UNIQUINESS = true;
    public static final Predicate ALWAYS_TRUE = t -> true;

    private List<TestGroup<T>> testGroups = new ArrayList<>();
    private List<Supplier<Stream<T>>> generators = new ArrayList<>();
    private boolean checkTestNamesUniquiness = DEFAULT_CHECKNAME_UNIQUINESS;
    private volatile boolean locked;

    public static <T> Predicate<T> alwaysTrue() {
        //noinspection unchecked
        return (Predicate<T>) ALWAYS_TRUE;
    }

    private DynamicTestParams() {
    }

    private DynamicTestParams(List<Supplier<Stream<T>>> generators) {
        this.generators.addAll(generators);
    }

    /**
     * Простой метод получения фабрики из метода преобразования параметра в исполняемый код.
     *
     * @param transform метод трансформации данных параметра в исполняемый код теста.
     * @param <T>       тип параметра
     * @return фабричный метод
     */
    public static <T> BiFunction<String, T, DynamicTest> test(Function<T, Executable> transform) {
        return (s, t) -> DynamicTest.dynamicTest(s, transform.apply(t));
    }


    /**
     * Создание нового экземпляра
     *
     * @param params параметры
     * @param <T>    тип параметра
     * @return новый экземпляр экземпляра
     */
    public static <T> DynamicTestParams<T> of(T... params) {
        return new DynamicTestParams<T>().params(params);
    }

    /**
     * Создание нового экземпляра пустого билдера с конкретным типом элементов
     *
     * @param type -
     * @param <T>  -
     * @return -
     */
    public static <T> DynamicTestParams<T> ofType(Class<T> type) {
        return new DynamicTestParams<T>().params();
    }

    /**
     * Создание нового экземпляра пустого билдера с конкретным типом элементов
     *
     * @param type -
     * @param <T>  -
     * @return -
     */
    public static <T> DynamicTestParams<T> create(Class<T> type) {
        return new DynamicTestParams<T>().params();
    }

    /**
     * Добавление параметра - типа <T>
     *
     * @param param объект параметра
     * @return этот объект
     */
    public DynamicTestParams<T> param(T param) {
        checkLocked(false);
        generators.add(() -> Stream.of(param));
        return this;
    }

    /*
     * Добавить функционал генерирования параметов.
     *
     * @param generator (всегда не null) -
     *
     * Функционал генерирования параметров - каждый вызов генерирует новый стрим (не null) который содержит
     * элементы одинаковые для каждого вызова - Идемпотентный результат.
     *
     * В том случае если используется хотя бы один генератор результирующий порядок следования (обработки)
     * элементов зависит от потока выдаваемого генератором.
     *
     * @return - этот объект
     */
    public DynamicTestParams<T> generator(Supplier<Stream<T>> generator) {
        checkLocked(false);
        Objects.requireNonNull(generator);
        generators.add(generator);
        return this;
    }

    /**
     * Добавление нескольких параметров
     *
     * @param params массив параметров (не null)  params[n] не null
     * @return этот объект
     */
    @SafeVarargs
    public final DynamicTestParams<T> params(T... params) {
        checkLocked(false);
        Objects.requireNonNull(params);
        Stream.of(params).forEachOrdered(this::param);
        return this;
    }


    /**
     * Включить или выключить режим проверки уникальности имен вызываемых тестов.
     * Если режим проверки включен - в момент вызова методов
     * {@link #dynamicTests(Function)}
     * и
     * {@link #dynamicTests(Function, Predicate)}
     * проводится проверка уникальности имен - в этом случае дубликаты имен тестов не допустимы.
     *
     * @param checkTestNamesUniquiness - передаваемое значение.
     * @return этот объект.
     */
    public DynamicTestParams<T> checkTestNamesUniquiness(boolean checkTestNamesUniquiness) {
        checkLocked(false);
        this.checkTestNamesUniquiness = checkTestNamesUniquiness;
        return this;
    }


    /**
     * Вызов метода блокирует дальнейшие изменения объекта.
     * <p>
     * Метод должен вызываться хотя бы один раз перед формированием тестов осуществляемых посредством.
     * {@link DynamicTestParams#dynamicTests(Function, Predicate)}
     * {@link DynamicTestParams#dynamicTests(Function)}
     * {@link DynamicTestParams#dynamicTests(Function, Function, BiFunction, Predicate)}
     * {@link DynamicTestParams#form()}
     *
     * @return этот объект
     */
    public DynamicTestParams<T> lock() {
        locked = true;
        return this;
    }

    /**
     * @return Набор параметров в виде нового открытого стрима (Всегда не null)
     * параметры представлены как они объявлены в объекте.
     */
    public Stream<T> paramsData() {
        return generators.stream().flatMap(Supplier::get);
    }

    /**
     * Получить стрим с динамическихми тестами построеными на основе переданных параметров с использованием
     * метода преобразования.
     * <p>
     * В методе может проводиться проверки уникальности имен.
     * <p>
     * если вызывается {@link #checkTestNamesUniquiness(boolean)} (с параметром {@code true})
     *
     * @param name              метод преобразования параметра в наименование теста (Всегда не null)
     * @param executableFactory метод преобразования параметра в набор инструкций (Всегда не null)
     * @param testFactory       фабрика тестов (Всегда не null)
     * @param paramFilter       Фильтр для параметров (Всегда не null)
     * @return стрим с тестами, построенными на переданных параметрах. (Всегда не null)
     * @deprecated  Использовать {@link #testGroup} и {@link #form()}
     */
    @Deprecated
    public Stream<DynamicTest> dynamicTests(
            Function<T, String> name,
            Function<T, Executable> executableFactory,
            BiFunction<String, Executable, DynamicTest> testFactory,
            Predicate<? super T> paramFilter) {

        checkLocked(true);
        Objects.requireNonNull(name);
        Objects.requireNonNull(executableFactory);
        Objects.requireNonNull(paramFilter);
        Objects.requireNonNull(testFactory);
        return dynamicTests(t -> testFactory.apply(name.apply(t), executableFactory.apply(t)), paramFilter);
    }

    /**
     * Получить стрим с динамическихми тестами построеными на основе переданных параметров с использованием
     * метода преобразования.
     * <p>
     * В методе может проводиться проверки уникальности имен
     *
     * @param function  - метод преобразования из параметра в динамический тест (Всегда не null)
     * @param predicate - Фильтр для параметров
     * @return стрим с тестами, построенными на переданных параметрах. (Всегда не null)
     * @deprecated  Использовать {@link #testGroup} и {@link #form()}
     */
    @Deprecated
    public Stream<DynamicTest> dynamicTests(Function<T, DynamicTest> function, Predicate<? super T> predicate) {
        checkLocked(true);
        Objects.requireNonNull(function);
        Objects.requireNonNull(predicate);
        if (checkTestNamesUniquiness) {
            /* проверить уникальность названий имен получаемых тестом */
            //noinspection ResultOfMethodCallIgnored
            paramsData().filter(predicate).map(function).
                    collect(Collectors.toMap(DynamicTest::getDisplayName, dt -> dt, DynamicTestParams::checkNameUniquiness));
        }
        return paramsData().filter(predicate).map(function);
    }

    /**
     * Получить стрим с динамическихми тестами построеными на основе переданных параметров с использованием
     * метода преобразования.
     * <p>
     * В методе может проводиться проверки уникальности имен
     *
     * @param function - метод преобразования из параметра в динамический тест. (Всегда не null)
     * @return стрим с тестами, построенными на переданных параметрах. (Всегда не null)
     * <p>
     * @deprecated  Использовать {@link #testGroup} и {@link #form()}
     */
    @Deprecated
    public Stream<DynamicTest> dynamicTests(Function<T, DynamicTest> function) {
        return dynamicTests(function, alwaysTrue());
    }

    /**
     * Добавить группу с динамическими тестами.
     * <p>
     * В методе проводится проверка уникальности имен добавляемых групп. Группы не могут иметь одинаковые имена.
     *
     * @param groupName         Имя группы - не null - уникальное в рамках проекта.
     * @param nameFactory       - фабрика имен теста (не null) получение имени в зависимости от параметра и имени группы.
     *                          Индемпотентная функция.
     * @param executableFactory - фабрика получения кода в соответствии с параметром (не null).
     *                          Индемпотентная функция получения экземпляра .
     * @param paramFilter       - фильтр параметров (не null). Индемпотентная функция.
     * @return ссылка на этот объект
     */
    public DynamicTestParams<T> testGroup(
            String groupName,
            BiFunction<String, T, String> nameFactory,
            BiFunction<String, T, DynamicTest> executableFactory,
            Predicate<T> paramFilter) {
        checkLocked(false);

        /* Если существует группа с таким именем - это ошибка */
        if (testGroups.stream().map(TestGroup::groupName).anyMatch(s -> Objects.equals(groupName, s))) {
            throw new IllegalStateException();
        }
        testGroups.add(new TestGroup<T>(groupName, nameFactory, executableFactory, paramFilter));
        return this;
    }

    /**
     * Операция формирования тестов.
     * Метод осуществляет конечную операцию по формированию набора тестов.
     * При этом производится декартово произведение методов с передаваемыми параметрами.
     *
     * @return поток с динамическими тестами.
     */
    public Stream<DynamicTest> form() {
        checkLocked(true);
        if (checkTestNamesUniquiness) {
            //noinspection ResultOfMethodCallIgnored
            createTestStream().map(DynamicTest::getDisplayName).
                    collect(Collectors.toMap(s -> s, s -> s, DynamicTestParams::checkNameUniquiness));
        }
        return createTestStream();
    }


    /**
     * !!!!! Этот метод пока что достаточно не проверен - его можно использовать с осторожностью.
     * Метод создает новый объект с копией существующих параметров.
     *
     * @return новый набор параметров сделанный на основе прошлых параметров.
     */
    public DynamicTestParams<T> copyParams() {
        checkLocked(true);
        return new DynamicTestParams<T>(this.generators);
    }

    /* ----------------------------------------- privates ----------------------------------------------------   */

    private Stream<DynamicTest> createTestStream() {
        return Stream.of(getDynamicsTestStream()).flatMap(Supplier::get);
    }

    private Supplier<Stream<DynamicTest>> getDynamicsTestStream() {
        return () -> testGroups.stream().
                flatMap(group -> generators.stream().
                        flatMap(Supplier::get).
                        filter(group.paramFilter()).
                        map(t -> group.executableFactory().apply(group.nameFactory().apply(group.groupName(), t), t)));
    }

    private static DynamicTest checkNameUniquiness(DynamicTest dt, DynamicTest dt2) {
        if (dt != dt2 && Objects.equals(dt.getDisplayName(), dt2.getDisplayName())) {
            throw new IllegalStateException("test nameFactory " + dt.getDisplayName() + " is already in use");
        }
        return dt;
    }

    private static String checkNameUniquiness(String dt, String dt2) {
        if (Objects.equals(dt, dt2)) {
            throw new IllegalStateException("name " + dt + " is already in use");
        }
        return dt;
    }

    private void checkLocked(boolean value) {
        if (locked != value) {
            throw new IllegalStateException("locked value " + locked +
                    " does not match to expected " + value);
        }
    }

    private static <T> Stream<T> streamFromIterable(Supplier<Iterable<T>> iterableSupplier) {
        Iterator<T> iterator = iterableSupplier.get().iterator();
        Stream.Builder<T> builder = Stream.builder();
        while (iterator.hasNext()) {
            builder.accept(iterator.next());
        }
        return builder.build();
    }

    private static <T> Supplier<Iterable<T>> fromFunctionGenerator(Function<Integer, T> generator) {
        Objects.requireNonNull(generator);
        return () -> () -> functionGeneratorIterator(generator);
    }

    private static class FunctionGeneratorIterator<T> implements Iterator<T> {
        private final Function<Integer, T> generator;
        private volatile T next;
        private volatile int index = -1;

        private FunctionGeneratorIterator(Function<Integer, T> generator) {
            this.generator = Objects.requireNonNull(generator);
        }

        @Override
        public boolean hasNext() {
            if (index == -1) {
                index = 0;
                next = generator.apply(index);
            }
            return next != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T curNext = this.next;
            this.next = generator.apply(++index);
            return curNext;
        }
    }

    private static <T> Iterator<T> functionGeneratorIterator(Function<Integer, T> generator) {
        return new FunctionGeneratorIterator<>(generator);
    }
}
