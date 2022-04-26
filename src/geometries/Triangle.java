package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray);

        if (planeIntersections == null){
            return null;
        }

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(p0);// p0->p1
        Vector v2 = p2.subtract(p0);// p0->p2
        Vector v3 = p3.subtract(p0);// p0->p3

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double d1 = n1.dotProduct(v);
        double d2 = n2.dotProduct(v);
        double d3 = n3.dotProduct(v);

        // if the intersection is inside triangle
        if ((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0)) {
            return List.of(new GeoPoint(this,planeIntersections.get(0)._point));
        }
        return null;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
