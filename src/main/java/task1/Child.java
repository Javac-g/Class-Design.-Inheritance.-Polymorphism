package task1;

public class Child extends Person {

    private int age;

    private String healthInfo,childIDNumber, name;

    public Child(int age, String healthInfo, String name,String childIDNumber) {

        super(age,healthInfo,name);
        this.childIDNumber = childIDNumber;

    }


    String getHealthStatus(){ return name +" " + healthInfo; }


}