package task1;

public class Adult extends Person {
    private int age;

    private String healthInfo, passportNumber, name;

    public Adult(int age, String healthInfo, String passportNumber, String name) {
        super(age,healthInfo,name);
        this.passportNumber = passportNumber;
    }

    String getHealthStatus(){ return name +" " + healthInfo; }
}
