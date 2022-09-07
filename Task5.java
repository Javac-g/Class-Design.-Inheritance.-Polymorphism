import java.util.List;
import java.util.Objects;

interface Figure {
    double getPerimeter();
}

class Rectang implements Figure {
    private double height;
    private double width;

    public Rectang(double height, double width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectang)) return false;

        Rectang rectang = (Rectang) o;

        if (Double.compare(rectang.width, width) != 0) return false;
        return Double.compare(rectang.height, height) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(width);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Rectang[" +
                "height=" + height +
                ", width=" + width +
                ']';
    }
}

class Square implements Figure {

    private double width;

    public Square(double width) {
        this.width = width;
    }

    @Override
    public double getPerimeter() {
        return 4 * width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;

        Square square = (Square) o;

        return Double.compare(square.width, width) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(width);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "Square[" +
                "width=" + width +
                ']';
    }
}

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
