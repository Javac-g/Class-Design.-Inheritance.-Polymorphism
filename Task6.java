import java.util.List;
import java.util.stream.Collectors;

abstract class Shape {

    private String name;

    public Shape(String name) {
        this.name = name;
    }

    public abstract double getArea();

    public String getName() {
        return name;
    }
}

class Circle extends Shape {

    private double radius;

    public Circle(String name, double radius) {
        super(name);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;

        Circle circle = (Circle) o;

        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(radius);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "Circle[radius=" + radius + ']';
    }

    public double getRadius() {
        return radius;
    }
}

class Rectangle extends Shape {

    private double height;
    private double width;

    public Rectangle(String name, double height, double width) {
        super(name);
        this.height = height;
        this.width = width;
    }

    @Override
    public double getArea() {
        return height * width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;

        Rectangle rectangle = (Rectangle) o;

        if (Double.compare(rectangle.height, height) != 0) return false;
        return Double.compare(rectangle.width, width) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(height);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Rectangle[height=" + height + ", width=" + width + ']';
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}

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
