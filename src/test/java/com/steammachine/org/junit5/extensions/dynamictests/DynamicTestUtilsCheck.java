package com.steammachine.org.junit5.extensions.dynamictests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;
import com.steammachine.org.junit5.extensions.dynamictests.impls.DynamicTestUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vladimir Bogoduhov
 */
public class DynamicTestUtilsCheck {
    private static final Comparator<Class> CLASS_COMPARATOR = Comparator.comparing(Class::getName);

    /* --------------------------------------------- OBJECT_PRIMITIVES --------------------------------------------  */

    @Test
    public void objectsPrimitives10() {
        List<Class> template = new ArrayList<>(Arrays.asList(
                Boolean.class, Byte.class, Short.class, Integer.class, Long.class,
                Float.class, Double.class, Character.class, String.class));
        Collections.sort(template, CLASS_COMPARATOR);

        List<Class> classes = new ArrayList<>(DynamicTestUtils.OBJECT_PRIMITIVES);
        Collections.sort(classes, CLASS_COMPARATOR);
        Assert.assertEquals(template, classes);
    }

    @Test
    public void objectsPrimitives20() {
        List<Class> template = new ArrayList<>(Arrays.asList(
                Boolean.TYPE,
                Boolean.class,

                Byte.TYPE,
                Byte.class,

                Short.TYPE,
                Short.class,

                Integer.TYPE,
                Integer.class,

                Long.TYPE,
                Long.class,

                Float.TYPE,
                Float.class,

                Double.TYPE,
                Double.class,

                Character.TYPE,
                Character.class,

                String.class));
        Collections.sort(template, CLASS_COMPARATOR);

        List<Class> classes = new ArrayList<>(DynamicTestUtils.OBJECT_PRIMITIVES_STRING);
        Collections.sort(classes, CLASS_COMPARATOR);
        Assert.assertEquals(template, classes);
    }


    /* ------------------------------------------------- type consts --------------------------------------------  */


    @Test
    public void typeConsts10() {
        Assert.assertEquals("boolean", DynamicTestUtils.PrimTypeNames.BooleanTYPE);
        Assert.assertEquals("java.lang.Boolean", DynamicTestUtils.PrimTypeNames.BooleanСlass);
        Assert.assertEquals("byte", DynamicTestUtils.PrimTypeNames.ByteTYPE);
        Assert.assertEquals("java.lang.Byte", DynamicTestUtils.PrimTypeNames.ByteClass);
        Assert.assertEquals("short", DynamicTestUtils.PrimTypeNames.ShortTYPE);
        Assert.assertEquals("java.lang.Short", DynamicTestUtils.PrimTypeNames.ShortClass);
        Assert.assertEquals("int", DynamicTestUtils.PrimTypeNames.IntegerTYPE);
        Assert.assertEquals("java.lang.Integer", DynamicTestUtils.PrimTypeNames.IntegerClass);
        Assert.assertEquals("long", DynamicTestUtils.PrimTypeNames.LongTYPE);
        Assert.assertEquals("java.lang.Long", DynamicTestUtils.PrimTypeNames.LongClass);
        Assert.assertEquals("float", DynamicTestUtils.PrimTypeNames.FloatTYPE);
        Assert.assertEquals("java.lang.Float", DynamicTestUtils.PrimTypeNames.FloatClass);
        Assert.assertEquals("double", DynamicTestUtils.PrimTypeNames.DoubleTYPE);
        Assert.assertEquals("java.lang.Double", DynamicTestUtils.PrimTypeNames.DoubleClass);
        Assert.assertEquals("char", DynamicTestUtils.PrimTypeNames.CharacterTYPE);
        Assert.assertEquals("java.lang.Character", DynamicTestUtils.PrimTypeNames.CharacterClass);
        Assert.assertEquals("java.lang.String", DynamicTestUtils.PrimTypeNames.StringClass);
    }



    /* ------------------------------------------------- checkTypes -----------------------------------------------  */

    @Test
    public void checkTypes10() {
        Assert.assertEquals(
                Collections.emptyList(),
                DynamicTestUtils.checkTypes(Collections.emptyList())
        );
    }

    @Test
    public void checkTypes20() {
        Assert.assertEquals(
                Arrays.asList(Boolean.class, Boolean.class, String.class, Long.class),
                DynamicTestUtils.checkTypes(
                        Arrays.asList(
                                new Object[]{null, true, "sdasd", 1L},
                                new Object[]{false, null, "sdasd", 1L},
                                new Object[]{false, true, null, 1L},
                                new Object[]{true, false, "sdasd", null}
                        )
                )
        );
    }

    @Test
    public void checkTypes30() {
        Assert.assertEquals(
                Arrays.asList(Object.class, Object.class, Object.class, Object.class),
                DynamicTestUtils.checkTypes(
                        Arrays.asList(
                                new Object[]{new Object(), true, "sdasd", 1L},
                                new Object[]{false, new Object(), "sdasd", 1L},
                                new Object[]{false, true, new Object(), 1L},
                                new Object[]{true, false, "sdasd", new Object()}
                        )
                )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTypes40() {
        DynamicTestUtils.checkTypes(
                Arrays.asList(
                        new Object[]{"sdasd", false},
                        new Object[]{false, "sdasd"}
                )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTypes50() {
        DynamicTestUtils.checkTypes(
                Arrays.asList(
                        new Object[]{false, "sdasd", 1L, false},
                        new Object[]{false, "sdasd", 1L, ""})
        );
    }

    @Test()
    public void checkTypes60() {
        Assert.assertEquals(
                Arrays.asList(Boolean.class, String.class, Long.class, String.class),
                DynamicTestUtils.checkTypes(
                        Arrays.asList(
                                new Object[]{false, "sdasd", 1L, null},
                                new Object[]{false, "sdasd", 1L, ""}
                        )

                )
        );
    }

    @Test
    public void checkTypes100() {
        Assert.assertEquals(
                Arrays.asList(Boolean.class, Boolean.class, String.class),

                DynamicTestUtils.checkTypes(
                        Arrays.asList(
                                new Object[]{false, true, "sdasd"},
                                new Object[]{true, false, "sdasd"}
                        )
                )
        );
    }

    /* ----------------------------------------- paramValues ----------------------------------------------- */

    @Test
    public void paramNames10() {
        Assert.assertEquals("1:int:Integer, 1:long:Long, value:String:String", DynamicTestUtils.paramValues(
                Arrays.asList(1, 1L, "value"),
                Arrays.asList(Integer.TYPE, Long.TYPE, String.class))
        );
    }

    @Test
    public void paramNames20() {
        Assert.assertEquals("1:int:Integer, 1:long:Long, null:String",
                DynamicTestUtils.paramValues(
                        Arrays.asList(1, 1L, null),
                        Arrays.asList(Integer.TYPE, Long.TYPE, String.class))
        );
    }

    @Test
    public void paramNames30() {
        Assert.assertEquals("null:String, null:String, null:String, null:String, null:String, null:String",
                DynamicTestUtils.paramValues(
                        Arrays.asList(null, null, null, null, null, null),
                        Arrays.asList(String.class, String.class, String.class, String.class, String.class, String.class)));
    }

    @Test
    public void paramNames40() {
        Assert.assertEquals("1:int:Integer, 1:long:Long, null:String",
                DynamicTestUtils.paramValues(
                        Arrays.asList(1, 1L, null),
                        Arrays.asList(Integer.TYPE, Long.TYPE, String.class)));
    }


    /* ----------------------------------------- generateAsList -------------------------------------------- */


    private static class AClazz {

        public void test(String s, long l) {
        }

    }

    @Test
    public void generateAsList10() {
        List<DynamicTest> dynamicTests = DynamicTestUtils.generateAsList(
                new AClazz(),
                Arrays.asList(new Object[]{"1", 1L}, new Object[]{"2", 2L}),
                Arrays.asList(String.class, Long.TYPE),
                DynamicTest::dynamicTest);
        Assert.assertEquals(2, dynamicTests.size());

        List<String> list = dynamicTests.stream().map(DynamicTest::getDisplayName).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("test(\"1\", 1L)", "test(\"2\", 2L)"), list);

    }

    /* ----------------------------------------- isTypeAssignableFromValue -------------------------------------------- */

    @Test(expected = NullPointerException.class)
    public void isTypeAssignableFrom12() {
        DynamicTestUtils.isTypeAssignableFromValue(null, null);
    }

    @Test
    public void isTypeAssignableFrom14() {
        DynamicTestUtils.isTypeAssignableFromValue(Byte.TYPE, null);
    }


    @Test
    public void isTypeAssignableFrom10() {
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Object.class, ""));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(String.class, ""));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Byte.TYPE, (byte) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Byte.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Byte.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Byte.class, (byte) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Byte.TYPE, (byte) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Byte.class, (byte) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Byte.class, null));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Byte.class, 1L));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Short.TYPE, (short) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Short.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Short.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Short.class, (short) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Short.TYPE, (short) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Short.class, (short) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Short.class, null));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Short.class, 1L));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.TYPE, (float) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Float.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.TYPE, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, null));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Float.class, 1L));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Long.TYPE, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.TYPE, (long) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Long.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.class, (long) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.TYPE, (long) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.class, (long) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Long.class, 1L));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Integer.TYPE, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.TYPE, (int) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Integer.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.class, (int) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.TYPE, (int) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.class, (int) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Integer.class, 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.TYPE, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, (float) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Float.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.TYPE, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, (float) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Float.class, null));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Float.class, 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.TYPE, (double) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.class, (double) 1));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Double.TYPE, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.class, (double) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.TYPE, (double) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.class, (double) 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Double.class, null));
        Assert.assertFalse(DynamicTestUtils.isTypeAssignableFromValue(Double.class, 1));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Object.class, null));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Object.class, (byte) 11));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Object.class, (short) 11));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Object.class, (int) 11));
        Assert.assertTrue(DynamicTestUtils.isTypeAssignableFromValue(Object.class, (long) 11));

    }


}