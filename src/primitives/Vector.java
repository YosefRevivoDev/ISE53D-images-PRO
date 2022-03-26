package primitives;

/**
 * This class defines for us a vector consisting of a direction and a point
 */
public class Vector extends Point {

    /**
     * c'tor Gets three values from type double
     *
     * @param d1
     * @param d2
     * @param d3
     */
    public Vector(double d1, double d2, double d3) {

        this(new Double3(d1, d2, d3));
    }

    /**
     * c'tor Gets a type of type Double3
     * With catch that check if this vector is zero
     *
     * @param xyz
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector can not be the vector zero");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }

    /**
     * Vector insert function
     *
     * @param vector
     * @return
     */
    public Point add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Multiplies vector and scalar and returns a new vector
     *
     * @param number
     * @return
     */
    public Vector scale(double number) {
        return new Vector(xyz.scale(number));
    }

    /**
     * Calculate vector length squared
     *
     * @return
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * Calculate vector length
     *
     * @return
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Multiply vector by scalar according to the rules in the exercise
     *
     * @param v3
     * @return
     */
    public double dotProduct(Vector v3) {
        Double3 result = xyz.product(v3.xyz);
        return result.d1 + result.d2 + result.d3;
    }

    /**
     * Multiplies a vector in vector and returns a new vector
     * that is perpendicular to the two existing vectors
     *
     * @param vector
     * @return
     */
    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Normalizes the vector and returns a new normalized vector
     * in the same direction as the original vector
     *
     * @return
     */

    public Vector normalize() {
        xyz = scale(1 / length()).xyz;
        return this;
    }




}
