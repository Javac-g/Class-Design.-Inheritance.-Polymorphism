package task4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyUtils {
    public List<Employee> largestEmployees(List<Employee> workers) {
        if (workers == null || workers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Employee> largestEmployees = new ArrayList<>();

        int maxExperience = workers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Employee::getExperience)
                .max()
                .orElse(0);

        BigDecimal maxFinalPayment = workers.stream()
                .filter(Objects::nonNull)
                .map(Employee::getPayment)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        for (Employee e : workers) {
            if (e == null) continue;
            boolean isMaxExperience = e.getExperience() == maxExperience;
            boolean isMaxPayment = e.getPayment().compareTo(maxFinalPayment) == 0;

            if (isMaxExperience || isMaxPayment) {
                largestEmployees.add(e);
            }
        }

        return largestEmployees;
    }
}
