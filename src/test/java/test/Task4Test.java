package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import task4.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task4Test {

    final private static String PACKAGE = "";

    @DisplayName("Check that Classes is present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypePresent(String cl) {
        try {
            Class<?> clazz = Class.forName(cl);
            assertNotNull(clazz);
            assertEquals(cl.substring(cl.lastIndexOf('.') + 1), clazz.getSimpleName());
        } catch (ClassNotFoundException e) {
            fail("There is no class " + cl);
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(Arguments.of("task3.MyUtils"),
                Arguments.of("task4.MyUtils"),
                Arguments.of("task5.MyUtils"),
                Arguments.of("task6.MyUtils"),
                Arguments.of("task4.Manager"),
                Arguments.of("task4.Employee"));
    }

    @DisplayName("Check that is classes in project")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName( cl);
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
        return Stream.of(Arguments.of("task4.Employee", new String[]{"String", "int", "BigDecimal"}),
                Arguments.of("task4.Manager", new String[]{"String", "int", "BigDecimal", "double"}));
    }

    @DisplayName("Check that class contains method")
    @ParameterizedTest
    @MethodSource("listClassesAndMethods")
    void isMethodPresent(String cl, String m) {
        Method[] methods = null;
        try {
            methods = Class.forName( cl).getDeclaredMethods();
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
        return Stream.of(
                Arguments.of("task3.MyUtils","maxDuration"),
                Arguments.of("task4.MyUtils","largestEmployees"),
                Arguments.of("task5.MyUtils","sumPerimeter"),
                Arguments.of("task6.MyUtils", "maxAreas"),
                Arguments.of("task4.Employee", "getName"),
                Arguments.of("task4.Employee", "getExperience"),
                Arguments.of("task4.Employee", "getPayment"),
                Arguments.of("task4.Manager", "getCoefficient"));
    }

    @DisplayName("Check that child class extends Parent")
    @ParameterizedTest
    @MethodSource("listOfChildren")
    void extendsTypeClass(String parent, String child) {
        try {
            final Class<?> parentClazz = Class.forName( parent);
            final Class<?> childClazz = Class.forName( child);
            assertTrue(parentClazz.isAssignableFrom(childClazz));
        } catch (ClassNotFoundException e) {
            fail("There is no extends " + child + " the parent class " + parent);
        }
    }

    private static Stream<Arguments> listOfChildren() {
        String parent = "task4.Employee";
        String child1 = "task4.Manager";
        return Stream.of(Arguments.of(parent, child1));
    }

    @DisplayName("Check that fields is private")
    @ParameterizedTest
    @MethodSource("listPrivateFields")
    void isFieldPrivate(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName( clas);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no " + clas + " class");
        } catch (NoSuchFieldException e) {
            fail("There is no " + fieldName + " field");
        }
    }

    private static Stream<Arguments> listPrivateFields() {
        return Stream.of(Arguments.of("task4.Employee", "name"),
                Arguments.of("task4.Employee", "experience"),
                Arguments.of("task4.Employee", "basePayment"),
                Arguments.of("task4.Manager", "coefficient"));
    }

    @DisplayName("Check if original list unchanged in the largestEmployees method")
    @Test
    void checkOriginUnchanged() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        originList.add((Employee) new Manager("Petro", 9, new BigDecimal(3000.0), 1.5));
        originList.add(new Employee("Stepan", 8, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(3500.0)));
        originList.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> sendList = new ArrayList<Employee>(originList);
        try {
            new MyUtils().largestEmployees((List) sendList);
            assertEquals(originList, sendList);
        } catch (Exception e) {
            fail("Original parameters changed in method");
        }
    }

    @DisplayName("Check that use parameters without duplicate employees ")
    @Test
    void checkUniqueAll() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        originList.add((Employee) new Manager("Petro", 9, new BigDecimal(3000.0), 1.5));
        originList.add(new Employee("Stepan", 8, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(3500.0)));
        originList.add(new Employee("Ihor", 5, new BigDecimal(3500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Maxim", 8, new BigDecimal(4000.0)));
        expected.add(new Employee("Anton", 10, new BigDecimal(3000.0)));
        expected.add((Employee) new Manager("Dmitri", 9, new BigDecimal(3000.0), 1.5));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>) new MyUtils().largestEmployees((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with unique names");
        }
    }

    @DisplayName("Check that use equal experience in two employees")
    @Test
    void checkDuplicateExperience() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        originList.add((Employee) new Manager("Petro", 9, new BigDecimal(2000.0), 1.5));
        originList.add(new Employee("Stepan", 8, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(1500.0)));
        originList.add(new Employee("Ihor", 11, new BigDecimal(3500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Ivan", 12, new BigDecimal(5000.0)));
        expected.add((Employee) new Manager("Petro", 9, new BigDecimal(6000.0), 1.5));
        expected.add(new Employee("Ihor", 10, new BigDecimal(3500.0)));
        expected.add(new Employee("Stepan", 9, new BigDecimal(4000.0)));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>) new MyUtils().largestEmployees((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with equal experience in two employees");
        }
    }

    @DisplayName("Check that use equal payment in two employees")
    @Test
    void checkDuplicatePayment() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        originList.add((Employee) new Manager("Petro", 9, new BigDecimal(5000.0), 1.5));
        originList.add(new Employee("Stepan", 8, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(3500.0)));
        originList.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Ivan", 10, new BigDecimal(7000.0)));
        expected.add((Employee) new Manager("Petro", 9, new BigDecimal(1000.0), 1.5));
        expected.add(new Employee("Ihor", 5, new BigDecimal(1500.0)));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>) new MyUtils().largestEmployees((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with equal payment in two employees");
        }
    }

    @DisplayName("Check that use duplicate employees in the largestEmployees method  parameter")
    @Test
    void checkDuplicateEmployee() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(1500.0)));
        originList.add((Employee) new Manager("Petro", 9, new BigDecimal(2500.0), 1.5));
        originList.add(new Employee("Stepan", 10, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(3500.0)));
        originList.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Ivan", 10, new BigDecimal(1600.0)));
        expected.add((Employee) new Manager("Petro", 9, new BigDecimal(1700.0), 1.5));
        expected.add(new Employee("Stepan", 10, new BigDecimal(1800.0)));
        expected.add(new Employee("Ihor", 5, new BigDecimal(2900.0)));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>) new MyUtils().largestEmployees((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with duplicate employees in the largestEmployees method  parameter");
        }
    }

    @DisplayName("Check that use equal payments by calculate of two employees")
    @Test
    void checkDuplicateCondition() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        originList.add((Employee) new Manager("Petro", 10, new BigDecimal(3000.0), 1.5));
        originList.add(new Employee("Stepan", 8, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(3500.0)));
        originList.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        expected.add((Employee) new Manager("Petro", 10, new BigDecimal(3000.0), 1.5));
        expected.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>) new MyUtils().largestEmployees((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with equal payments by calculate of two employees");
        }
    }

    @DisplayName("Check that use equals experience and payment in two employees")
    @Test
    void checkDuplicateExperiencePayment() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        originList.add((Employee) new Manager("Petro", 9, new BigDecimal(3000.0), 1.5));
        originList.add(new Employee("Stepan", 10, new BigDecimal(4000.0)));
        originList.add(new Employee("Andriy", 7, new BigDecimal(3500.0)));
        originList.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        originList.add((Employee) new Manager("Vasyl", 8, new BigDecimal(2000.0), 2.0));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        expected.add((Employee) new Manager("Petro", 9, new BigDecimal(3000.0), 1.5));
        expected.add(new Employee("Stepan", 10, new BigDecimal(4000.0)));
        expected.add(new Employee("Ihor", 5, new BigDecimal(4500.0)));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>) new MyUtils().largestEmployees((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with equals experience and payment in two employees");
        }
    }

    @DisplayName("Check that one Employee in the List")
    @Test
    void checkOneEmployee() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee("Ivan", 10, new BigDecimal(3000.0)));
        List<Employee> actual = null;
        try {
            actual = (List<Employee>)new MyUtils().largestEmployees((List)originList);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Do not work correct with one Employee");
        }
    }

    @DisplayName("Check if original list is empty")
    @Test
    void checkEmptyList() {
        final List<Employee> originList = new ArrayList<Employee>();
        final List<Employee> expected = new ArrayList<Employee>();
        List<Employee> actual = null;
        try {
            actual = (List<Employee>)new MyUtils().largestEmployees((List)originList);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Do not work correct with empty List");
        }
    }

    @DisplayName("Check if content is null")
    @Test
    void checkNullContent() {
        final List<Employee> originList = new ArrayList<Employee>();
        originList.add(null);
        final List<Employee> expected = new ArrayList<Employee>();
        expected.add(null);
        List<Employee> actual = null;
        try {
            actual = (List<Employee>)new MyUtils().largestEmployees((List)originList);
            assertTrue(actual.size() == 0 || expected.equals(actual));
        } catch (Exception e) {
            fail("Content is null");
        }
    }

}
