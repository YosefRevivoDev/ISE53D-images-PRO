package geometries;

import primitives.*;

import java.util.List;


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

    public List<GeoPoint> findIntsersections(Ray ray) {
        return null;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(center);
        return normal.normalize();
    }

}
