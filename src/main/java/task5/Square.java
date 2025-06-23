package task5;

public class Square implements Figure {

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
