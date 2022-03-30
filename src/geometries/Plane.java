package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

public class Plane implements Geometry {

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
     * Finding intersection points between the ray and The plane.
     * @param ray Ray
     * @return
     */
    @Override
    public List<Point> findIntsersections(Ray ray) {
        if (ray.getP0().equals(p0) || isZero(this.normal.dotProduct(ray.getDir()))
                || isZero(this.normal.dotProduct(p0.subtract(ray.getP0()))))
            return null;

        double t = (this.normal.dotProduct(p0.subtract(ray.getP0()))) / (this.normal.dotProduct(ray.getDir()));
        if (t < 0) // In case there is no intersection with the plane return null
            return null;

        //In case there is intersection with the plane return the point
        Point p = ray.getPoint(t);
        LinkedList<Point> result = new LinkedList<Point>();
        result.add(p);
        return result;
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

}
