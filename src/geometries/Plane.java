package geometries;

import primitives.Point;
import primitives.Vector;


public class Plane implements Geometry {
    private Point p0;
    private Vector normal;

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    public Plane(Point vertex, Point vertex1, Point vertex2) {
    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal(Point point) { return normal; }
}
