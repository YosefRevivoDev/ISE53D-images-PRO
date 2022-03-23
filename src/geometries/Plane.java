package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public List<Point> findIntsersections(Ray ray) {

        Vector p0Q;

        try {
            p0Q = p0.subtract(ray.getPoint());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }


        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv)) //if the ray is parallel to the plane - no intersection
            return null;

        double t = alignZero(normal.dotProduct(p0Q) / nv);

        if (t > 0) {
            return List.of(new Point(this, ray.getTargetPoint(t)));
        } else {
            return null;
        }
    }

    public Point getP0() {
        return p0;
    }


    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    public Vector getNormal(){return this.normal;}
}
