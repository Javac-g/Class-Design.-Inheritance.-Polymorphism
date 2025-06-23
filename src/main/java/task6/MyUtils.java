package task6;

import java.util.List;
import java.util.stream.Collectors;

public class MyUtils {
    public List<Shape> maxAreas(List<Shape> shapes) {

        double maxCircleArea = shapes.stream()
                .filter(i -> i instanceof Circle)
                .mapToDouble(Shape::getArea)
                .max().orElse(0);

        double maxRectangleArea = shapes.stream()
                .filter(i -> i instanceof Rectangle)
                .mapToDouble(Shape::getArea)
                .max().orElse(0);

        return shapes.stream()
                .filter(i -> (i instanceof Circle && i.getArea() == maxCircleArea) |
                        (i instanceof Rectangle && i.getArea() == maxRectangleArea))
                .collect(Collectors.toList());
    }
}
