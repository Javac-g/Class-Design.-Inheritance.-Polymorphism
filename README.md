# Class-Design.-Inheritance.-Polymorphism
Practice tasks

##################### --- Task 1 --- #####################

Please, make refactoring of the code:
<pre>
<code>
class Person {
    String childIDNumber;    
}

class Child {
    int age;
    String healthInfo;
    String name;
    String getHealthStatus(){ return name +" " + healthInfo; }
}

class Adult {
    int age;
    String healthInfo;
    String passportNumber;   
    String name;
    String getHealthStatus(){ return name +" " + healthInfo; }
}
</code>
</pre>
We know that adult  doesn't have childIDNumber.
Child doesn't have passportNumber.
Create a public constructor in each class to initialize all their fields (make the first parameter of type int).

##################### --- Task 2 --- #####################
-
Create interface DrinkReceipt with methods String getName() and DrinkReceipt addComponent(String componentName, int componentCount). Create interface DrinkPreparation with method Map<String, Integer> makeDrink() to return a drink components. Create interface Rating with method int getRating().
Class Caffee contains fields String name, int rating, Map of ingredients and implements interfaces DrinkReceipt, DrinkPreparation and Rating. Method makeDrink() prepare coffee with typically components: {Water=100, Arabica=20}. Espresso and Cappuccino classes extends the Caffee Class and override method makeDrink(). Espresso caffee has 50 g. of Water. Cappuccino caffee has an additional of 50 g. of Milk.
Create a averageRating() method of the MyUtils class to return a Map with coffee name as key and coffee average rating as value.
For example, for a given list
[Espresso [name=Espresso, rating=8], Cappuccino [name=Cappuccino, rating=10], Espresso [name=Espresso, rating=10], Cappuccino [name=Cappuccino, rating=6], Caffee [name=Caffee, rating=6]]
you should get
{Espresso=9.00, Cappuccino=8.00, Caffee=6.00}


##################### --- Task 3 --- #####################
-
Create next types: Person (field String name), Student (fields String studyPlace, int studyYears) and Worker (fields String workPosition, int experienceYears). Classes Student and Worker are derived from class Person. All classes have getters to return fields.
Create a maxDuration() method of the MyUtils class to return a list of Students with maximum duration of study and Workers with maximum experience.
For example, for a given list
[Person [name=Ivan], Student [name=Petro, studyPlace=University, studyYears=3], Worker [name=Andriy, workPosition=Developer, experienceYears=12], Student [name=Stepan, studyPlace=College, studyYears=4], Worker [name=Ira, workPosition=Manager, experienceYears=8], Student [name=Ihor, studyPlace=University, studyYears=4]]
you should get
[Worker [name=Andriy, workPosition=Developer, experienceYears=12], Student [name=Stepan, studyPlace=College, studyYears=4], Student [name=Ihor, studyPlace=University, studyYears=4]]


##################### --- Task 4 --- #####################
-
Create classes Employee (fields String name, int experience and BigDecimal basePayment) and Manager (field double coefficient) with methods which return the general working experience and payment for work done.
A getter methods of class Employee return value of all fields: getName(), getExperience() and getPayment().
Classes Manager is derived from class Employee and override getPayment() method to return multiplication of a coefficient and base payment.
Create a largestEmployees() method of the MyUtils class to return a List of unique employees with maximal working experience and payment without duplicate objects.
The original list must be unchanged.
For example, for a given list
 [Employee [name=Ivan, experience=10, basePayment=3000.00], Manager [name=Petro, experience=9, basePayment=3000.00, coefficient=1.5],  Employee [name=Stepan, experience=8, basePayment=4000.00], Employee [name=Andriy, experience=7, basePayment=3500.00], Employee [name=Ihor, experience=5, basePayment=4500.00], Manager [name=Vasyl, experience=8, basePayment=2000.00, coefficient=2.0]]
you should get
[Employee [name=Ivan, experience=10, basePayment=3000.00], Manager [name=Petro, experience=9, basePayment=3000.00, coefficient=1.5], Employee [name=Ihor, experience=5, basePayment=4500.00]].

##################### --- Task 5 --- #####################
-
Create classes Square and Rectang with method (double getPerimeter()) for calculating of perimeter.
Find solution for avoiding of duplicate code.
Create a double sumPerimeter(List<?> firures) method of the MyUtils class to return a sum perimeters of all figures.
For example, for a given list
[[Square [width=4.00], Square [width=5.00], Rectang [height=2.00, width=3.00]]
you should get 46
##################### --- Task 6 --- #####################
-
<pre>
<code>
public class Shape{
    private String name;
    public Shape(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public double getCircleArea(){
        //Code
    }
    public double getRectangleArea(){
        //Code
    }
    public double getArea(){
        
        if( getName().equals("Circle") ){
            
            return getCircleArea();
        }else {
            return getRectangleArea();
        }
        //Code
    }
}
</code>
</pre>
Please create class Shape with abstract method to calculate area of figure and field name. Replace code in method getArea() according to principles of polymorphism i.e. Circle and Rectangle classes extends Shape class and override double getArea() method. Develop maxAreas() method of the MyUtils class to return a List with instances of maximum area.
The original list must be unchanged.
For example, for a given list
[Circle [radius=2.00], Rectangle [height=2.00, width=3.00], Circle [radius=1.00], Rectangle [height=3.00, width=2.00],  Circle [radius=0.50], Rectangle [height=1.00, width=2.00]]
you should get
[Circle [radius=2.00], Rectangle [height=2.00, width=3.00], Rectangle [height=3.00, width=2.00]]
