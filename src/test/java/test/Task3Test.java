package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import task3.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task3Test {

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
        return Stream.of(
                Arguments.of("task3.MyUtils"),
                Arguments.of("task4.MyUtils"),
                Arguments.of("task5.MyUtils"),
                Arguments.of("task6.MyUtils"),
                Arguments.of("task3.Person"),
                Arguments.of("task3.Student"),
                Arguments.of("task3.Worker"));
    }

    @DisplayName("Check that is classes in project")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName( cl);
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) &&
                    !Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no " + cl + " class");
        }
    }

    @DisplayName("Check that Constructor is Public")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void isConstructorPublic(String clas, String[] parameterTypesName) {
        try {
            Class<?> clazz = Class.forName(clas);
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
        return Stream.of(Arguments.of("task3.Person", new String[]{"String"}),
                Arguments.of("task3.Student", new String[]{"String", "String", "int"}),
                Arguments.of("task3.Worker", new String[]{"String", "String", "int"}));
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
                Arguments.of(
                "task3.MyUtils","maxDuration"),
                Arguments.of("task4.MyUtils","largestEmployees"),
                Arguments.of("task5.MyUtils","sumPerimeter"),
                Arguments.of("task6.MyUtils", "maxAreas"),
                Arguments.of("task3.Person", "getName"),
                Arguments.of("task3.Student", "getStudyPlace"),
                Arguments.of("task3.Student", "getStudyYears"),
                Arguments.of("task3.Worker", "getWorkPosition"),
                Arguments.of("task3.Worker", "getExperienceYears"));
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
        String parent = "task3.Person";
        String child1 = "task3.Student";
        String child2 = "task3.Worker";
        return Stream.of(Arguments.of(parent, child1), Arguments.of(parent, child2));
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
        return Stream.of(Arguments.of("task3.Person", "name"),
                Arguments.of("task3.Student", "studyPlace"),
                Arguments.of("task3.Student", "studyYears"),
                Arguments.of("task3.Worker", "workPosition"),
                Arguments.of("task3.Worker", "experienceYears"));
    }

    @DisplayName("Check if original list unchanged in the maxDuration method")
    @Test
    void checkOriginUnchanged() {
        final List<Person> originList = new ArrayList<Person>();
        originList.add(new Person("Ivan"));
        originList.add((Person) new Student("Petro", "University", 3));
        originList.add((Person) new Worker("Andriy", "Developer", 12));
        originList.add((Person) new Student("Stepan", "College", 4));
        originList.add((Person) new Worker("Ira", "Manager", 8));
        originList.add((Person) new Student("Ihor", "University", 4));
        final List<Person> sendList = new ArrayList<Person>(originList);
        try {
            new MyUtils().maxDuration((List) sendList);
            assertEquals(originList, sendList);
        } catch (Exception e) {
            fail("Original parameters changed in the 'maxDuration' method");
        }
    }

    @DisplayName("Check that use parameters without duplicate names")
    @Test
    void checkUniqueAll() {
        final List<Person> originList = new ArrayList<Person>();
        originList.add(new Person("Ivan"));
        originList.add((Person) new Student("Petro", "University", 3));
        originList.add((Person) new Worker("Andriy", "Developer", 12));
        originList.add((Person) new Student("Stepan", "College", 4));
        originList.add((Person) new Worker("Ira", "Manager", 8));
        originList.add((Person) new Student("Ihor", "University", 1));
        final List<Person> expected = new ArrayList<Person>();
        expected.add((Person) new Worker("Andriy", "Developer", 12));
        expected.add((Person) new Student("Stepan", "College", 4));
        List<Person> actual = null;
        try {
            actual = (List<Person>) new MyUtils().maxDuration((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with unique names");
        }
    }

    @DisplayName("Check that use duplicate student in the duplicate method  parameter")
    @Test
    void checkDuplicateStudent() {
        final List<Person> originList = new ArrayList<Person>();
        originList.add(new Person("Ivan"));
        originList.add((Person) new Student("Petro", "University", 3));
        originList.add((Person) new Worker("Andriy", "Developer", 12));
        originList.add((Person) new Student("Stepan", "College", 4));
        originList.add((Person) new Worker("Ira", "Manager", 8));
        originList.add((Person) new Student("Ihor", "University", 4));
        originList.add((Person) new Student("Ihor", "University", 4));
        final List<Person> expected = new ArrayList<Person>();
        expected.add((Person) new Worker("Andriy", "Developer", 12));
        expected.add((Person) new Student("Stepan", "College", 4));
        expected.add((Person) new Student("Ihor", "University", 4));
        List<Person> actual = null;
        try {
            actual = (List<Person>) new MyUtils().maxDuration((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with duplicate student names");
        }
    }

    @DisplayName("Check that use duplicate worker in the duplicate method  parameter")
    @Test
    void checkDuplicateWorker() {
        final List<Person> originList = new ArrayList<Person>();
        originList.add(new Person("Ivan"));
        originList.add((Person) new Student("Petro", "University", 3));
        originList.add((Person) new Worker("Andriy", "Developer", 12));
        originList.add((Person) new Student("Stepan", "College", 4));
        originList.add((Person) new Worker("Ira", "Manager", 12));
        originList.add((Person) new Student("Ihor", "University", 1));
        final List<Person> expected = new ArrayList<Person>();
        expected.add((Person) new Worker("Andriy", "Developer", 12));
        expected.add((Person) new Student("Stepan", "College", 4));
        expected.add((Person) new Worker("Ira", "Manager", 12));
        List<Person> actual = null;
        try {
            actual = (List<Person>) new MyUtils().maxDuration((List) originList);
            assertEquals(new HashSet(expected), new HashSet(actual));
        } catch (Exception e) {
            fail("Do not work correct with duplicate worker names");
        }
    }

    @DisplayName("Check that one person in the List")
    @Test
    void checkOnePerson() {
        final List<Person> originList = new ArrayList<Person>();
        originList.add(new Person("Ivan"));
        final List<Person> expected = new ArrayList<Person>();
        List<Person> actual = null;
        try {
            actual = (List<Person>) new MyUtils().maxDuration((List) originList);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Do not work correct with one person");
        }
    }


    @DisplayName("Check if original list is empty")
    @Test
    void checkEmptyList() {
        final List<Person> originList = new ArrayList<Person>();
        final List<Person> expected = new ArrayList<Person>();
        List<Person> actual = null;
        try {
            actual = (List<Person>) new MyUtils().maxDuration((List) originList);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Do not work correct with empty List");
        }
    }

    @DisplayName("Check if content is null")
    @Test
    void checkNullContent() {
        final List<Person> originList = new ArrayList<Person>();
        originList.add(null);
        final List<Person> expected = new ArrayList<Person>();
        expected.add(null);
        List<Person> actual = null;
        try {
            actual = (List<Person>) new MyUtils().maxDuration((List) originList);
            assertTrue(actual.size() == 0 || expected.equals(actual));
        } catch (Exception e) {
            fail("Content is null");
        }
    }

}
