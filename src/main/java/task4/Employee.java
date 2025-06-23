package task4;

import java.math.BigDecimal;

public class Employee {
    private String name;
    private int experience;
    private BigDecimal basePayment;

    public Employee(String name, int experience, BigDecimal basePayment) {
        this.name = name;
        this.experience = experience;
        this.basePayment = basePayment;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public BigDecimal getPayment() {
        return basePayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (experience != employee.experience) return false;
        if (!name.equals(employee.name)) return false;
        return basePayment.equals(employee.basePayment);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + experience;
        result = 31 * result + basePayment.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Employee[" +
                "name='" + name + '\'' +
                ", experience=" + experience +
                ", basePayment=" + basePayment +
                ']';
    }
}
