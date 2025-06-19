

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    final private static String PACKAGE = "";

    @DisplayName("Check that Classes is present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClasses(String cl) {
        try {
            assertNotNull(Class.forName(PACKAGE + cl));
            assertEquals(cl, Class.forName(PACKAGE + cl).getSimpleName());
        } catch (ClassNotFoundException e) {
            fail("There is no class " + cl);
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(Arguments.of("Person"), Arguments.of("Child"), Arguments.of("Adult"));
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
        String parent = "Person";
        String child1 = "Child";
        String child2 = "Adult";
        return Stream.of(Arguments.of(parent, child1), Arguments.of(parent, child2));
    }

    @DisplayName("Check that Class has fields")
    @ParameterizedTest
    @MethodSource("listOfClassesAndFields")
    void hasTypeDeclaredField(String clas, String field) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            assertNotNull(clazz.getDeclaredField(field));
        } catch (ClassNotFoundException e) {
            fail("There is no class " + clas);
        } catch (NoSuchFieldException e) {
            fail("There is no field " + field);
        }
    }

    private static Stream<Arguments> listOfClassesAndFields() {
        return Stream.of(Arguments.of("Person", "age"), Arguments.of("Person", "healthInfo"), Arguments.of("Person", "name"),
                Arguments.of("Adult", "passportNumber"), Arguments.of("Child", "childIDNumber"));
    }

    @DisplayName("Check that fields is String")
    @ParameterizedTest
    @MethodSource("listOfStringFields")
    void hasFieldTypeString(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field[] fields = clazz.getDeclaredFields();
            boolean isField = false;
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    isField = true;
                    assertEquals(field.getType(), String.class);
                    break;
                }
            }
            assertTrue(isField, "There is no field");
        } catch (ClassNotFoundException e) {
            fail("There is no class " + clas);
        }
    }

    private static Stream<Arguments> listOfStringFields() {
        return Stream.of(Arguments.of("Person", "healthInfo"), Arguments.of("Person", "name"),
                Arguments.of("Adult", "passportNumber"), Arguments.of("Child", "childIDNumber"));
    }

    @DisplayName("Check that is field int")
    @Test
    void hasFieldTypeInt() {
        try {
            Class<?> clazz = Class.forName(PACKAGE + "Person");
            Field[] fields = clazz.getDeclaredFields();
            boolean isField = false;
            for (Field field : fields) {
                if ("age".equals(field.getName())) {
                    isField = true;
                    assertEquals("int",String.valueOf(field.getType()));
                    break;
                }
            }
            assertTrue(isField, "There is no field");
        } catch (ClassNotFoundException e) {
            fail("There is no class Person");
        }
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
        return Stream.of(Arguments.of("Person", "age"), Arguments.of("Person", "healthInfo"), Arguments.of("Person", "name"),
                Arguments.of("Adult", "passportNumber"), Arguments.of("Child", "childIDNumber"));
    }

    @DisplayName("Check that Person class contains 'getHealthStatus' method")
    @Test
    void hasGetHealthStatusMethod() {
        Method[] methods = null;
        try {
            methods = Class.forName(PACKAGE + "Person").getDeclaredMethods();
        } catch (ClassNotFoundException e) {
            fail("There is no class Person");
        }
        boolean isPersonMethod = false;
        for (Method method: methods) {
            if (method.getName().equals("getHealthStatus")) {
                isPersonMethod = true;
                break;
            }
        }
        assertTrue(isPersonMethod);
    }

    @DisplayName("Check that 'getHealthStatus' method returns String")
    @Test
    void hasMethodReturnType() {
        Method[] methods = null;
        try {
            methods = Class.forName(PACKAGE + "Person").getDeclaredMethods();
        } catch (ClassNotFoundException e) {
            fail("There is no class Person");
        }
        boolean isMyPersonMethod = false;
        for (Method method: methods) {
            if (method.getName().equals("getHealthStatus")) {
                isMyPersonMethod = true;
                assertEquals(method.getReturnType(), String.class);
                break;
            }
        }
        assertTrue(isMyPersonMethod);
    }

    @DisplayName("Check declared Constructors")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void hasTypeDeclaredConstructor(String clas, String[] parameterTypesName) {
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
                    assertArrayEquals(parameterTypes, parameterTypesName);
                    break;
                }
            }
            assertTrue(isConstructorCorrect, "Constructor is no correct");
        } catch (ClassNotFoundException e) {
            fail("There is no class " + clas);
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(Arguments.of("Person", new String[]{"int", "String", "String"}),
                Arguments.of("Adult", new String[]{"int", "String", "String", "String"}),
                Arguments.of("Child", new String[]{"int", "String", "String", "String"}));
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
            assertTrue(isConstructorCorrect, "Constructor is no Public");
        } catch (ClassNotFoundException e) {
            fail("There is no class " + clas);
        }
    }


}
