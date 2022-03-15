package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

public class Cylinder extends Tube{

    private double height;

    public Cylinder(double radius, Ray axisRay, double height) {

        super(radius,axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {

        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double tScale;
        try {
            tScale = alignZero(point.subtract(p0).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (tScale == 0 || isZero(height - tScale)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        p0 = p0.add(v.scale(tScale));
        return point.subtract(p0).normalize();
    }
}
