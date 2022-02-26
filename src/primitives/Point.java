package primitives;

public class Point {

    protected final Double3 xyz;

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Point(double d1, double d2, double d3) {
        this(new Double3(d1, d2, d3));
    }

    public Double3 getXyz() {
        return xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }
    public Vector subtract(Point p1) {
        return new Vector(p1.xyz.subtract(xyz));
    }
}
