package java.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import task5.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task5Test {

    final private static String PACKAGE = "src.main.java.task4.";

    @DisplayName("Check that Classes is present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypePresent(String cl) {
        try {
            assertNotNull(Class.forName(PACKAGE + cl));
            assertEquals(cl, Class.forName(PACKAGE + cl).getSimpleName());
        } catch (ClassNotFoundException e) {
            fail("There is no class " + cl);
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(Arguments.of("task3.task4.task5.task6.MyUtils"), Arguments.of("Rectang"), Arguments.of("Square"));
    }

    @DisplayName("Check that is classes in project")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no " + cl + " class");
        }
    }

    @DisplayName("Check that Constructor is Public")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void isConstructorPublic(String clas, String[] parameterTypesName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Constructor<?>[] declaredConstructors;
            declaredConstructors = clazz.getDeclaredConstructors();
            boolean isConstructorCorrect = false;
            for (final Constructor<?> constructor : declaredConstructors) {
                final Type[] types = constructor.getGenericParameterTypes();
                final String[] parameterTypes = new String[types.length];
                for (int i = 0; i < types.length; ++i) {
                    final String[] parts = types[i].getTypeName().split("\\.");
                    parameterTypes[i] = parts[parts.length - 1];
                }
                if (Arrays.equals(parameterTypes, parameterTypesName)) {
                    isConstructorCorrect = true;
                    assertTrue(Modifier.isPublic(constructor.getModifiers()));
                    break;
                }
            }
            assertTrue(isConstructorCorrect, "Do not have Constructor");
        } catch (ClassNotFoundException e) {
            fail("There is no class " + clas);
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(Arguments.of("Rectang", new String[]{"double", "double"}),
                Arguments.of("Square", new String[]{"double"}));
    }

    @DisplayName("Check that class contains method")
    @ParameterizedTest
    @MethodSource("listClassesAndMethods")
    void isMethodPresent(String cl, String m) {
        Method[] methods = null;
        try {
            methods = Class.forName(PACKAGE + cl).getDeclaredMethods();
            boolean isMethod = false;
            for (Method method : methods) {
                if (method.getName().equals(m)) {
                    isMethod = true;
                    break;
                }
            }
            assertTrue(isMethod, "Class do not have method " + m);
        } catch (ClassNotFoundException e) {
            fail("There is no class " + cl);
        }
    }

    private static Stream<Arguments> listClassesAndMethods() {
        return Stream.of(Arguments.of("task3.task4.task5.task6.MyUtils", "sumPerimeter"),
                Arguments.of("Rectang", "getPerimeter"),
                Arguments.of("Square", "getPerimeter"));
    }

    @DisplayName("Check that child class extends Parent")
    @ParameterizedTest
    @MethodSource("listOfChildren")
    void extendsTypeClass(String parent, String child) {
        try {
            final Class<?> parentClazz = Class.forName(PACKAGE + parent);
            final Class<?> childClazz = Class.forName(PACKAGE + child);
            assertTrue(parentClazz.isAssignableFrom(childClazz));
        } catch (ClassNotFoundException e) {
            fail("There is no extends " + child + " the parent class " + parent);
        }
    }

    private static Stream<Arguments> listOfChildren() {
        String parent = "Rectang";
        String child1 = "Square";
        return Stream.of(Arguments.of(parent, child1));
    }

    @DisplayName("Check that fields is private")
    @ParameterizedTest
    @MethodSource("listPrivateFields")
    void isFieldPrivate(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no " + clas + " class");
        } catch (NoSuchFieldException e) {
            fail("There is no " + fieldName + " field");
        }
    }

    private static Stream<Arguments> listPrivateFields() {
        return Stream.of(Arguments.of("Rectang", "height"), Arguments.of("Rectang", "width"));
    }

    @DisplayName("Check rectangle perimeter")
    @Test
    void checkRectangPerimeter() {
        final Rectang rectang = new Rectang(2.0, 3.0);
        final double expected = 10.0;
        double actual = -1.0;
        try {
            actual = rectang.getPerimeter();
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Perimeter is not correct");
        }
    }

    @DisplayName("Check square perimeter")
    @Test
    void checkSquarePerimeter() {
        final Square square = new Square(2.0);
        final double expected = 8.0;
        double actual = -1.0;
        try {
            actual = square.getPerimeter();
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Perimeter is not correct");
        }
    }

    @DisplayName("Check if original list unchanged in the sumPerimeter method")
    @Test
    void checkOriginUnchanged() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(new Square(4.0));
        originList.add(new Square(5.0));
        originList.add(new Rectang(2.0, 3.0));
        final List<Object> sendList = new ArrayList<Object>(originList);
        try {
            new MyUtils().sumPerimeter((List) sendList);
            assertEquals(originList, sendList);
        } catch (Exception e) {
            fail("Original parameters changed in method");
        }
    }

    @DisplayName("Check that use parameters without duplicate figures")
    @Test
    void checkUniqueAll() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(new Square(4.0));
        originList.add(new Square(5.0));
        originList.add(new Rectang(2.0, 3.0));
        final double expected = 46.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Do not work correct with unique names");
        }
    }

    @DisplayName("Check that use two equal squares  in  the sumPerimeter method  parameter")
    @Test
    void checkDuplicateSquare() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(new Square(4.0));
        originList.add(new Square(4.0));
        final double expected = 32.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Do not work correct with two equal squares  in  the sumPerimeter method  parameter");
        }
    }

    @DisplayName("Check that use two equal rectangle in the sumPerimeter method parameter")
    @Test
    void checkDuplicateRectang() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(new Rectang(2.0, 3.0));
        originList.add(new Rectang(2.0, 3.0));
        final double expected = 20.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Do not work correct with two equal rectangle in the sumPerimeter method parameter");
        }
    }

    @DisplayName("Check that one Square in the List")
    @Test
    void checkOneSquare() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(new Square(4.0));
        final double expected = 16.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Do not work correct with one Square");
        }
    }

    @DisplayName("Check that one Rectang in the List")
    @Test
    void checkOneRectang() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(new Rectang(2.0, 3.0));
        final double expected = 10.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Do not work correct with one Rectang");
        }
    }

    @DisplayName("Check if original list is empty")
    @Test
    void checkEmptyList() {
        final List<Object> originList = new ArrayList<Object>();
        final double expected = 0.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Do not work correct with empty List");
        }
    }

    @DisplayName("Check if content is null")
    @Test
    void checkNullContent() {
        final List<Object> originList = new ArrayList<Object>();
        originList.add(null);
        final double expected = 0.0;
        double actual = -1.0;
        try {
            actual = new MyUtils().sumPerimeter((List) originList);
            assertTrue(Math.abs(expected - actual) < 1.0E-8);
        } catch (Exception e) {
            fail("Content is null");
        }
    }

}
