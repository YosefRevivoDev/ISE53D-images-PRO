package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube{

    private double height;

    @Override
    public Vector getNormal(Point point) {

        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        return null;
    }
}
