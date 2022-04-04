package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {

        Ray ray = new Ray(new Point(0,1,0),new Vector(1,0,0));
        List<Point> points = new LinkedList<Point>();
        points.add(new Point(3,4,0));
        points.add(new Point(1,1,0));
        points.add(new Point(2,3,0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: The closest point to the head of the ray is in the middle of the list
        assertEquals(ray.findClosestPoint(points), points.get(1), "Wrong point");

        // =============== Boundary Values Tests ==================

        // TC11: The closest point to the head of the ray is the first point
        points.add(0,points.remove(1));
        assertEquals(ray.findClosestPoint(points), points.get(0), "Wrong point");

        // TC12: The closest point to the head of the ray is the last point
        points.add(points.remove(0));
        assertEquals(ray.findClosestPoint(points), points.get(2), "Wrong point");

        // TC13: empty list
        points.clear();
        assertNull(ray.findClosestPoint(points),"empty list");
        // TC14: empty list(2)the list is null
        points=null;
        assertNull(ray.findClosestPoint(points),"empty list");
    }
}