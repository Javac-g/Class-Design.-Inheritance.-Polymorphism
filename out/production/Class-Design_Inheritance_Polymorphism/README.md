# 🧠 Class Design, Inheritance & Polymorphism Practice

Welcome to a series of Java practice tasks focusing on core Object-Oriented Programming principles: **inheritance**, **polymorphism**, **interfaces**, and **clean code design**.

---

## ✅ Task 1 — Refactor Classes

**Goal:** Refactor the classes below to eliminate code duplication and follow clean design principles.

```java
class Person {
    String childIDNumber;    
}

class Child {
    int age;
    String healthInfo;
    String name;
    String getHealthStatus() {
        return name + " " + healthInfo;
    }
}

class Adult {
    int age;
    String healthInfo;
    String passportNumber;   
    String name;
    String getHealthStatus() {
        return name + " " + healthInfo;
    }
}
```

- Adults don't have `childIDNumber`.
- Children don't have `passportNumber`.
- Create constructors in each class to initialize all fields (first parameter should be `int`).
- Refactor to reduce duplicate code.

---

## ☕ Task 2 — Coffee Interfaces and Ratings

**Goal:** Create a flexible coffee system using interfaces and class hierarchy.

### Interfaces:

- `DrinkReceipt`:  
  ```java
  String getName();
  DrinkReceipt addComponent(String componentName, int componentCount);
  ```

- `DrinkPreparation`:  
  ```java
  Map<String, Integer> makeDrink();
  ```

- `Rating`:  
  ```java
  int getRating();
  ```

### Classes:

- `Caffee`:
  - Fields: `String name`, `int rating`, `Map<String, Integer> ingredients`
  - Implements all three interfaces
  - `makeDrink()` should return `{Water=100, Arabica=20}`

- `Espresso`:
  - Inherits `Caffee`
  - Overrides `makeDrink()` → `{Water=50, Arabica=20}`

- `Cappuccino`:
  - Inherits `Caffee`
  - Overrides `makeDrink()` → `{Water=100, Arabica=20, Milk=50}`

### Utility:

- Method `averageRating()` in `MyUtils` should return a `Map<String, Double>` representing average rating for each drink name.

#### Example:
Input:
```java
[Espresso(name=Espresso, rating=8),
 Cappuccino(name=Cappuccino, rating=10),
 Espresso(name=Espresso, rating=10),
 Cappuccino(name=Cappuccino, rating=6),
 Caffee(name=Caffee, rating=6)]
```
Output:
```java
{Espresso=9.00, Cappuccino=8.00, Caffee=6.00}
```

---

## 🎓 Task 3 — Max Duration

**Goal:** Identify the students with the longest study and workers with the most experience.

### Classes:
- `Person(String name)`
- `Student(String studyPlace, int studyYears)` → extends `Person`
- `Worker(String workPosition, int experienceYears)` → extends `Person`

All classes must have proper getters.

### Utility:

- Method `maxDuration(List<Person>)` in `MyUtils` should return a `List<Person>` with:
  - Workers with **max experience**
  - Students with **max study years**

#### Example:
Input:
```java
[Person(name=Ivan),
 Student(name=Petro, studyPlace=University, studyYears=3),
 Worker(name=Andriy, workPosition=Developer, experienceYears=12),
 Student(name=Stepan, studyPlace=College, studyYears=4),
 Worker(name=Ira, workPosition=Manager, experienceYears=8),
 Student(name=Ihor, studyPlace=University, studyYears=4)]
```

Output:
```java
[Worker(name=Andriy, ...), Student(name=Stepan, ...), Student(name=Ihor, ...)]
```

---

## 💼 Task 4 — Max Experience & Payment

**Goal:** Handle payment logic and filter out the most experienced and highest-paid employees.

### Classes:

- `Employee(String name, int experience, BigDecimal basePayment)`
  - Methods: `getName()`, `getExperience()`, `getPayment()`

- `Manager(double coefficient)` → extends `Employee`
  - Overrides `getPayment()` to return `basePayment * coefficient`

### Utility:

- Method `largestEmployees(List<Employee>)` in `MyUtils` should return:
  - Unique employees with **max experience**
  - Unique employees with **max payment**

#### Example:
Input:
```java
[Employee(Ivan, 10, 3000.00),
 Manager(Petro, 9, 3000.00, 1.5),
 Employee(Stepan, 8, 4000.00),
 Employee(Andriy, 7, 3500.00),
 Employee(Ihor, 5, 4500.00),
 Manager(Vasyl, 8, 2000.00, 2.0)]
```

Output:
```java
[Employee(Ivan, ...), Manager(Petro, ...), Employee(Ihor, ...)]
```

---

## 📏 Task 5 — Geometry: Avoid Duplicate Logic

**Goal:** Create figure classes and avoid duplicated perimeter logic.

### Classes:
- `Square(double width)`
- `Rectang(double width, double height)`

Both implement `getPerimeter()` method.

### Utility:

- Method `sumPerimeter(List<?>)` in `MyUtils` should return the **total perimeter** of all figures.

#### Example:
Input:
```java
[Square(width=4.00), Square(width=5.00), Rectang(height=2.00, width=3.00)]
```

Output:  
`46.00`

---

## 🔺 Task 6 — Polymorphism for Shapes

### Initial Class (to be refactored):

```java
public class Shape {
    private String name;
    public Shape(String name) { this.name = name; }
    public String getName() { return name; }
    public double getArea() {
        if (getName().equals("Circle")) return getCircleArea();
        else return getRectangleArea();
    }
}
```

### Goal:

- Replace conditional logic with **polymorphism**.
- Make `Shape` an abstract class with an abstract method `double getArea()`.
- Create `Circle` and `Rectangle` classes that override `getArea()`.

### Utility:

- Method `maxAreas(List<Shape>)` in `MyUtils` should return:
  - All shapes that have the **maximum area**.

#### Example:
Input:
```java
[Circle(radius=2.00),
 Rectangle(height=2.00, width=3.00),
 Circle(radius=1.00),
 Rectangle(height=3.00, width=2.00),
 Circle(radius=0.50),
 Rectangle(height=1.00, width=2.00)]
```

Output:
```java
[Circle(radius=2.00), Rectangle(2x3), Rectangle(3x2)]
```

---

## 📌 Summary

This project is a collection of OOP practice problems that enhance your understanding of:

- Inheritance and class hierarchy
- Interfaces and implementation
- Refactoring and clean code
- Polymorphism and abstraction
- Working with collections, streams, and maps
