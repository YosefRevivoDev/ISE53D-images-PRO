package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.*;
import java.util.List;

import static primitives.Util.alignZero;


public class Sphere implements Geometry{

    private Point center;
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

    @Override
    public List<Point> findIntsersections(Ray ray) {
        Point p0 = ray.getPoint();
        Vector v = ray.getDir();
        Vector u;

        try {
            u = center.subtract(p0);   // p0 == center
        } catch (IllegalArgumentException e) {
            return List.of(new Point(this,ray.getTargetPoint(radius)));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) {
            return List.of(new Point(this, (ray.getTargetPoint(t1)))
                    ,new Point(this, (ray.getTargetPoint(t2))));//P1 , P2
        }
        if (t1 > 0)
            return List.of(new Point(this,(ray.getTargetPoint(t1))));
        else if (t2 > 0)
            return List.of(new Point(this,(ray.getTargetPoint(t2))));
        return null;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(center);
        return normal.normalize();
    }

}
