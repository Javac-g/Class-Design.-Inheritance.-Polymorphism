import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Employee {
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

class Manager extends Employee {
    private double coefficient;

    public Manager(String name, int experience, BigDecimal basePayment, double coefficient) {
        super(name, experience, basePayment);
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public BigDecimal getPayment() {
        return super.getPayment().multiply(BigDecimal.valueOf(coefficient));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;
        if (!super.equals(o)) return false;

        Manager manager = (Manager) o;

        return Double.compare(manager.coefficient, coefficient) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(coefficient);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Manager[name=" + getName() + ", experience=" + getExperience() +
                ", coefficient=" + coefficient + ']';
    }
}

public class MyUtils {
    public List<Employee> largestEmployees(List<Employee> workers) {
        if (workers == null) {
            return new ArrayList<>();
        }

        if (workers.size() == 0) {
            return workers;
        }

        List<Employee> largestEmployees = new ArrayList<>();

        int maxExperience = workers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Employee::getExperience)
                .max().orElse(0);

        BigDecimal maxPayment = workers.stream()
                .filter(Objects::nonNull)
                .map(Employee::getPayment)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        workers.stream()
                .filter(i -> i != null &&
                        (i.getPayment().compareTo(maxPayment) == 0 | (i.getExperience() == maxExperience)))
                .forEach(largestEmployees::add);

        return largestEmployees;

    }
}
