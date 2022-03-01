package geometries;

import java.util.List;

import primitives.Point;


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
}
