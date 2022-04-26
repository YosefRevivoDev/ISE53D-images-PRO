package geometries;

import primitives.*;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;


public class Sphere extends Geometry{

    Point center;
    private double radius;

    /**
     * Sphere Constructor receiving radius and center
     * @param radius radius
     * @param center center
     */
    public Sphere(double radius, Point center) {
        this.radius = radius;
        this.center = center;
    }

    /**
     * get center
     * @return
     */
    public Point getCenter(){
        return center;
    }

    /**
     * get radius
     * @return
     */
    public double getRadius(){
        return radius;
    }

    /**
     * Finding intersection points between the ray and The sphere
     * @param ray Ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        double r = this.radius;
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        // Special case: if point p0 == center, that mean that all we need to calculate
        // is the radios mult scalar with the direction, and add p0
        if (center.equals(ray.getP0())) {
            return List.of(new GeoPoint(this, center.add(v.scale(radius))) );
        }

        Vector u = center.subtract(ray.getP0());
        double tm = u.dotProduct(ray.getDir());
        double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));

        if (d >= r) //also In case the cut is tangent to the object still return null - d = r
            return null;

        double th = alignZero(Math.sqrt(radius * radius - d * d)); // distance from p1 to intersection with d
        double t1 = alignZero(tm - th); // from p0 to p1
        double t2 = alignZero(tm + th);// from p0 to p2

        if (t1 > 0 && t2 > 0) // take only t > 0 (going in the right direction)
        {
//            Point P1 = P0.add(v.scale(t1));
//            Point P2 = P0.add(v.scale(t2));
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this, P1),new GeoPoint(this, P2));
        }
        if (t1 > 0) {
//            Point P1 = P0.add(v.scale(t1));
            Point P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        if (t2 > 0) {
//            Point P2 = P0.add(v.scale(t2));
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }
        return null;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(center);
        return normal.normalize();
    }

    @Override
    public String toString(){
        return "Point is: " + center + "\nradius is: " + radius;
    }

}
