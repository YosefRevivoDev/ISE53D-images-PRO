package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

public class Plane extends Geometry {

    Point p0;
    primitives.Vector normal;

    /**
     * Plane Constructor receiving a point and normal vector
     *
     * @param p0      point
     * @param normal vector
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
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


    /**
     * @return p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * get normal of point
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * get normal empty
     * @return
     */
    public Vector getNormal(){return this.normal;}

    @Override
    public String toString() {
        return "Plane{" +
                "p0 =" + p0 +
                ", normal=" + normal +
                '}';
    }
    /**
     * Finding intersection points between the ray and The plane.
     * @param ray Ray
     * @return
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // denominator
        double nv = n.dotProduct(v);

        if (isZero(nv))
            return null;

        Vector P0_Q = p0.subtract(P0);
        double t = alignZero(n.dotProduct(P0_Q) / nv);

        if (alignZero(t - maxDistance) > 0) {
            return null;
        }

        // if t<0 thn the ray is not in the right direction
        //if t==0 the ray origin alay on the
        if (t > 0) {
            Point P = P0.add(v.scale(t));
            return List.of(new GeoPoint(this, P));// returns a Geopoint
        }
        return null;
    }
}
