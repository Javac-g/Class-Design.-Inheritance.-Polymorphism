# 🧠 Java OOP Practice: Class Design, Inheritance & Polymorphism

Welcome to a collection of practical Java exercises focused on core Object-Oriented Programming concepts:  
**Inheritance**, **Polymorphism**, **Interfaces**, and **Clean Code Design**.

---

## 📚 Table of Contents
1. [Task 1 — Refactor Classes](#task-1--refactor-classes)
2. [Task 2 — Coffee Interfaces and Ratings](#task-2--coffee-interfaces-and-ratings)
3. [Task 3 — Max Duration](#task-3--max-duration)
4. [Task 4 — Max Experience & Payment](#task-4--max-experience--payment)
5. [Task 5 — Geometry: Avoid Duplicate Logic](#task-5--geometry-avoid-duplicate-logic)
6. [Task 6 — Polymorphism for Shapes](#task-6--polymorphism-for-shapes)

---

## ✅ Task 1 — Refactor Classes

**Goal:** Eliminate duplication and design a clear class hierarchy.

<details>
<summary>🧾 Initial Code</summary>

```
class task3.Person {
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

</details>

🔧 **Refactor Hints:**
- Use a common base class with shared fields.
- Children don't need `passportNumber`, adults don't need `childIDNumber`.
- Add constructors that initialize all fields.

---

## ☕ Task 2 — Coffee Interfaces and Ratings

**Goal:** Design a flexible coffee system with interface-based composition.

### 🔌 Interfaces

```
interface DrinkReceipt {
    String getName();
    DrinkReceipt addComponent(String componentName, int componentCount);
}

interface DrinkPreparation {
    Map<String, Integer> makeDrink();
}

interface Rating {
    int getRating();
}
```

### 🧩 Class Hierarchy

- `Caffee` (base class) implements all three interfaces.
- `Espresso` and `Cappuccino` override `makeDrink()` to adjust ingredients.

### 📈 Utility Method

```
Map<String, Double> MyUtils.averageRating(List<? extends Rating>)
```

<details>
<summary>📊 Example Input & Output</summary>

Input:
```
[Espresso(8), Cappuccino(10), Espresso(10), Cappuccino(6), Caffee(6)]
```

Output:
```
{Espresso=9.00, Cappuccino=8.00, Caffee=6.00}
```

</details>

---

## 🎓 Task 3 — Max Duration

**Goal:** Find students with the maximum study years and workers with the maximum experience.

### 👥 Class Overview

```
Person(String name)
Student(String studyPlace, int studyYears) // extends Person
Worker(String workPosition, int experienceYears) // extends Person
```

### 🧮 Utility

```
List<Person> MyUtils.maxDuration(List<Person> people)
```

<details>
<summary>📊 Example</summary>

Output contains:
- Students with max `studyYears`
- Workers with max `experienceYears`

</details>

---

## 💼 Task 4 — Max Experience & Payment

### 🧾 Class Design

```
Employee(String name, int experience, BigDecimal basePayment)
Manager(double coefficient) // extends Employee
```

- `Manager` overrides `getPayment()` → `basePayment * coefficient`

### 📊 Utility Method

```
List<Employee> MyUtils.largestEmployees(List<Employee> employees)
```

Returns employees with:
- Max experience
- Max payment (unique by name)

---

## 📏 Task 5 — Geometry: Avoid Duplicate Logic

**Goal:** Avoid perimeter duplication in `Square` and `Rectangle`.

### 📦 Class Overview

```
Square(double width)
Rectang(double width, double height)
```

Both implement:

```
double getPerimeter();
```

### 🧮 Utility

```
double MyUtils.sumPerimeter(List<?> figures)
```

---

## 🔺 Task 6 — Polymorphism for Shapes

**Before:**
```
double getArea() {
  if (name.equals("Circle")) return getCircleArea();
  else return getRectangleArea();
}
```

### ✅ Goal:

- Convert to:
  ```
  abstract class Shape { abstract double getArea(); }
  ```

- Implement `Circle` and `Rectangle` as concrete classes.

### 🧮 Utility

```
List<Shape> MyUtils.maxAreas(List<Shape> shapes)
```

Returns shapes with the **maximum area**.

---

## 🧾 Summary

These tasks strengthen your understanding of:
- ✅ Clean class hierarchy
- ✅ Interfaces and polymorphism
- ✅ Collection and stream operations
- ✅ Method overriding and abstraction
