package task1;

public class Person {

    private int age;
    private String childIDNumber, healthInfo, passportNumber,name;

    public Person(String childIDNumber) {
        this.childIDNumber = childIDNumber;
    }

    public Person(int age,  String healthInfo, String name) {
        this.age = age;
        this.healthInfo = healthInfo;
        this.name = name;
    }

    String getHealthStatus(){ return name +" " + healthInfo; }
}
