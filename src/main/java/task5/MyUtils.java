package task5;

import java.util.List;
import java.util.Objects;

public class MyUtils {
    public double sumPerimeter(List<Figure> firures) {

        if (firures.isEmpty() || firures == null) {
            return 0;
        }

        return firures.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Figure::getPerimeter).sum();
    }
}
