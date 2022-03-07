package geometries;

import primitives.*;


public class Sphere implements Geometry{

    private Point center;
    private double radius;

    /**
     * Sphere Constructor receiving radius and center
     * @param radius radius
     * @param center center
     */
    public Sphere(double radius, Point center) {
    }

    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(center);
        return normal.normalize();
    }

}
