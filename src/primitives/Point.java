package primitives;

/**
 * Produces a point in the plane that is represented by 3 values and in addition
 * adds the point to a collector and also subtracts
 */
public class Point {

    Double3 xyz;

    /**
     * c'tor Gets a type of type Double3
     * @param xyz
     */
    public Point(Double3 xyz) {
         this.xyz = xyz;
    }

    /**
     * c'tor Gets three values from type double
     * @param d1
     * @param d2
     * @param d3
     */
    public Point(double d1, double d2, double d3) {

        this(new Double3(d1, d2, d3));
    }

    /**
     * Boolean comparison function between objects
     * @param obj
     * @return
     */
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

    /**
     * Vector insert function
     * @param vector
     * @return
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Vector subtraction function
     * @param p1
     * @return
     */
    public Vector subtract(Point p1) {

        return new Vector(this.xyz.subtract(p1.xyz));
    }
}
