package primitives;
import geometries.Geometry;


/**
 * Produces a point in the plane that is represented by 3 values and in addition
 * adds the point to a collector and also subtracts
 */
public class Point {

    Double3 xyz;
    /**
     * A constant static variable that represents the "zero point"
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * c'tor Gets a type of type Double3
     * @param xyz
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Point(Point p){
        xyz = p.xyz;
    }
    /**
     * c'tor Gets three values from type double
     * @param d1
     * @param d2
     * @param d3
     */
    public Point(double d1, double d2, double d3) {

        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * Boolean comparison function between objects
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }


    @Override
    public String toString() {

        return "Point "  + xyz ;
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

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }
    /**
     * The function returns a copy of the points
     * @return New point with the same coordinates of this point
     */
    public Point getCopy() {
        return new Point(this.getX(), this.getY(), this.getZ());
    }
    /**
     * Calculates the distance between two points squared
     *
     * @param point The other point
     * @return The distance between two points squared
     */
    public double distanceSquared(Point point) {

        return (xyz.d1 - point.xyz.d1) * (xyz.d1 - point.xyz.d1)
                + (xyz.d2 - point.xyz.d2) * (xyz.d2 - point.xyz.d2)
                + (xyz.d3 - point.xyz.d3) * (xyz.d3 - point.xyz.d3);
    }

    /**
     * Calculates the distance between two points
     *
     * @param point The other point
     * @return The distance between two points squared
     */

    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }
}
