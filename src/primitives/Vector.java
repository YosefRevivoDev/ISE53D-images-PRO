package primitives;

public class Vector extends Point{


    public Vector(double d1, double d2, double d3) {
        this(new Double3(d1, d2, d3));
    }

    public Vector(Double3 xyz){
        super(xyz);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector can not be the vector zero");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point))
            return false;
        Point other = (Point) obj;
        return super.equals(other);
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }

    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    public Vector scale(double number){
        return new Vector(xyz.scale(number));
    }

    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double dotProduct(Vector v3) {
        Double3 result = xyz.product(v3.xyz);
        return result.d1 + result.d2 + result.d3;
    }


    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }

    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }
}
