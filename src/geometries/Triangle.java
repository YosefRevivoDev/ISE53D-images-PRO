package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;


public class Triangle extends Polygon {

    /**
     * Triangle constructor receiving the vertices of the triangle.
     * @param p1 The first vertex of the triangle
     * @param p2 The second vertex of the triangle
     * @param p3 The third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }


    /**
     * Finding intersection points between the ray and The triangle
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntsersections(Ray ray) {
        if (plane.findIntsersections(ray) == null) return null;
        List<Point> planeIntersections = plane.findIntsersections(ray);


        //Point p0 = ray.getPoint();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(ray.getPoint());
        Vector v2 = vertices.get(1).subtract(ray.getPoint());
        Vector v3 = vertices.get(2).subtract(ray.getPoint());


        double d1 = v.dotProduct(v1.crossProduct(v2));
        if (Util.isZero(d1)) return null;
        double d2 = v.dotProduct(v2.crossProduct(v3));
        if (Util.isZero(d2)) return null;
        double d3 = v.dotProduct(v3.crossProduct(v1));
        if (Util.isZero(d3)) return null;

        // if the intersection is inside triangle
        if ((d1 > 0.0 && d2 > 0.0 && d3 > 0.0) || (d1 < 0.0 && d2 < 0.0 && d3 < 0.0)) {
            List<Point> result = new LinkedList<>();
            for (Point geo : planeIntersections) {
                result.add(new Point(this, geo.getPoint()));
            }
            result.get(0)._geometry = this;
            return result;
        }

        return null;
    }
}
