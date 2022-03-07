package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;


public class Cylinder extends Tube{

    private double height;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius,axisRay);
    }

    @Override
    public Vector getNormal(Point point) {

        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        return null;
    }
}
