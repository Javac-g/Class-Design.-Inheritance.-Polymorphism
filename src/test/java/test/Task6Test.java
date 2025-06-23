package java.test;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import task6.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class Task6Test {
    final private static String PACKAGE = "src.main.java.task6.";
 
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
        return Stream.of(Arguments.of("task3.task4.task5.task6.MyUtils"), Arguments.of("Rectangle"), Arguments.of("Circle"),
                Arguments.of("Shape"));
    }

    @DisplayName("Check that is classes in project")
    @ParameterizedTest
    @MethodSource("listOfClass")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no " + cl + " class");
        }
    }

    private static Stream<Arguments> listOfClass() {
        return Stream.of(Arguments.of("task3.task4.task5.task6.MyUtils"), Arguments.of("Rectangle"), Arguments.of("Circle"));
    }

    @DisplayName("Check that classes is abstract")
    @Test
    void isTypeAbstractClass() {
        try {
            Class<?> clazz = Class.forName(PACKAGE + "Shape");
            assertTrue(Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("Shape is no abstract class");
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
        return Stream.of(Arguments.of("Shape", new String[]{"String"}),
                Arguments.of("Circle", new String[]{"String", "double"}),
                Arguments.of("Rectangle", new String[]{"String", "double", "double"}));
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
        return Stream.of(Arguments.of("task3.task4.task5.task6.MyUtils", "maxAreas"),
                Arguments.of("Shape", "getName"), Arguments.of("Circle", "getRadius"),
                Arguments.of("Rectangle", "getHeight"), Arguments.of("Rectangle", "getWidth"));
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
        String parent = "Shape";
        String child1 = "Circle";
        String child2 = "Rectangle";
        return Stream.of(Arguments.of(parent, child1), Arguments.of(parent, child2));
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
        return Stream.of(Arguments.of("Shape", "name"), Arguments.of("Circle", "radius"),
                Arguments.of("Rectangle", "height"), Arguments.of("Rectangle", "width"));
    }

    @DisplayName("Check if original list unchanged in the maxAreas method")
    @Test
    void checkOriginUnchanged() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape) new Circle("Circle", 2.0));
        originList.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        originList.add((Shape) new Circle("Circle", 1.0));
        originList.add((Shape) new Rectangle("Rectangle", 3.0, 2.0));
        originList.add((Shape) new Circle("Circle", 0.5));
        originList.add((Shape) new Rectangle("Rectangle", 1.0, 2.0));
        final List<Shape> sendList = new ArrayList<Shape>(originList);
        try {
            new MyUtils().maxAreas((List) sendList);
            assertEquals(originList, sendList);
        } catch (Exception e) {
            fail("Original parameters changed in method");
        }
    }

    @DisplayName("Check that use parameters without duplicate figures")
    @Test
    void checkUniqueAll() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape) new Circle("Circle", 2.0));
        originList.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        originList.add((Shape) new Circle("Circle", 1.0));
        originList.add((Shape) new Circle("Circle", 0.5));
        originList.add((Shape) new Rectangle("Rectangle", 1.0, 2.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape) new Circle("Circle", 2.0));
        expected.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with unique names");
        }
    }

    @DisplayName("Check that use two equal circles in the maxAreas method parameter")
    @Test
    void checkDuplicateCircle() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape) new Circle("Circle", 2.0));
        originList.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        originList.add((Shape) new Circle("Circle", 1.0));
        originList.add((Shape) new Circle("Circle", 0.5));
        originList.add((Shape) new Rectangle("Rectangle", 1.0, 2.0));
        originList.add((Shape) new Circle("Circle", 2.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape) new Circle("Circle", 2.0));
        expected.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with two equal squares  in  the sumPerimeter method  parameter");
        }
    }

    @DisplayName("Check that use two equal rectangle in the maxAreas method parameter")
    @Test
    void checkDuplicateRectangle() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape) new Circle("Circle", 2.0));
        originList.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        originList.add((Shape) new Circle("Circle", 1.0));
        originList.add((Shape) new Circle("Circle", 0.5));
        originList.add((Shape) new Rectangle("Rectangle", 1.0, 2.0));
        originList.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape) new Circle("Circle", 2.0));
        expected.add((Shape) new Rectangle("Rectangle", 2.0, 3.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with two equal rectangle in the maxAreas method parameter");
        }
    }

    @DisplayName("Check that use duplicate circles and rectangles in the maxAreas method parameter")
    @Test
    void checkDuplicateCircleRectangle() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape)new Circle("Circle", 2.0));
        originList.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        originList.add((Shape)new Circle("Circle", 1.0));
        originList.add((Shape)new Circle("Circle", 0.5));
        originList.add((Shape)new Rectangle("Rectangle", 1.0, 2.0));
        originList.add((Shape)new Circle("Circle", 2.0));
        originList.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape)new Circle("Circle", 2.0));
        expected.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with two duplicate circles and rectangles in the maxAreas method parameter");
        }
    }

    @DisplayName("Check that use figures with equal area by calculate")
    @Test
    void checkDuplicateCondition() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape)new Circle("Circle", 2.0));
        originList.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        originList.add((Shape)new Circle("Circle", 1.0));
        originList.add((Shape)new Rectangle("Rectangle", 3.0, 2.0));
        originList.add((Shape)new Circle("Circle", 0.5));
        originList.add((Shape)new Rectangle("Rectangle", 1.0, 2.0));
        originList.add((Shape)new Circle("Circle", 2.0));
        originList.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape)new Circle("Circle", 2.0));
        expected.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        expected.add((Shape)new Rectangle("Rectangle", 3.0, 2.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with figures with equal area by calculate");
        }
    }

    @DisplayName("Check that one circle in the List")
    @Test
    void checkOneSquare() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape)new Rectangle("Rectangle", 2.0, 3.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with one circle");
        }
    }

    @DisplayName("Check that one Rectangle in the List")
    @Test
    void checkOneRectangle() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add((Shape)new Circle("Circle", 2.0));
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add((Shape)new Circle("Circle", 2.0));
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with one Rectangle");
        }
    }

    @DisplayName("Check if original list is empty")
    @Test
    void checkEmptyList() {
        final List<Shape> originList = new ArrayList<Shape>();
        final List<Shape> expected = new ArrayList<Shape>();
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with empty List");
        }
    }

    @DisplayName("Check if content is null")
    @Test
    void checkNullContent() {
        final List<Shape> originList = new ArrayList<Shape>();
        originList.add(null);
        final List<Shape> expected = new ArrayList<Shape>();
        expected.add(null);
        List<Shape> actual = null;
        try {
            actual = (List<Shape>) new MyUtils().maxAreas((List) originList);
            assertTrue(actual.size() == 0 || expected.equals(actual));
        } catch (Exception e) {
            fail("Do not work correct if content is null");
        }
    }

}
