class Person {
    int age;
    String childIDNumber, healthInfo, passportNumber,name;

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


class Child extends Person {

    int age;

    String healthInfo,childIDNumber, name;

    public Child(int age, String healthInfo, String name,String childIDNumber) {

        super(age,healthInfo,name);
        this.childIDNumber = childIDNumber;

    }
    

    String getHealthStatus(){ return name +" " + healthInfo; }


}

class Adult extends Person {
    int age;

    String healthInfo, passportNumber, name;

    public Adult(int age, String healthInfo, String passportNumber, String name) {
        super(age,healthInfo,name);
        this.passportNumber = passportNumber;
    }

    String getHealthStatus(){ return name +" " + healthInfo; }
}
