package geometries;

import primitives.Point;
import primitives.Vector;


public class Plane implements Geometry {
    private final Point p0;
    private final Vector normal;

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    /**
     * A ctor that gets 3 parameters(Point type).
     *
     * @param p1 point p1
     * @param p2 point p2
     * @param p3 point p3
     */
    public Plane(Point p1, Point p2, Point p3) {

        if ((p1 == p2) || (p2 == p3) || (p3 == p1)) throw new IllegalArgumentException("Two points are equals");

        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

        try {
            Vector cross = v1.crossProduct(v2);

            p0 = p2;
            normal = cross.normalize();
        } catch (Exception ignored) {
            throw new IllegalArgumentException("The points are on the same line");
        }
    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal(Point point) {
        return normal;
    }
}
