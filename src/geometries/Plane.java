package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

public class Plane extends Geometry {

    final Point p0;
    final Vector normal;

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
        p0 = p1;
        if ((p1 == p2) || (p2 == p3) || (p3 == p1)) throw new IllegalArgumentException("Two points are equals");

        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

        Vector cross = v1.crossProduct(v2);

        normal = cross.normalize();
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

//    /**
//     * get normal empty
//     * @return
//     */
//    public Vector getNormal(){return this.normal;}

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

        if (p0.equals(P0)) return null;

        Vector P0_Q = p0.subtract(P0);
        double numerator = Util.alignZero(n.dotProduct(P0_Q));
        if (Util.isZero(numerator)) return null;

        double nv = Util.alignZero(n.dotProduct(v));
        if (isZero(nv)) return null;


        double t = Util.alignZero(numerator / nv);
        if (t < 0) return null;

        // if t<0 thn the ray is not in the right direction
        //if t==0 the ray origin alay on the
//        if (t > 0) {
//            Point P = P0.add(v.scale(t));
//            return List.of(new GeoPoint(this, P));// returns a Geopoint
//        }
//        return null;
//    }
        Point P = ray.getPoint(t);

        return List.of(new GeoPoint(this, P));
    }
}
