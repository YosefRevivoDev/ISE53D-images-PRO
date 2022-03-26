package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Double3;
import java.util.LinkedList;
import java.util.List;

/**
 * Testing Sphere
 *  @author Yossef Revivo and Chaim Gootwain
 */

class SphereTest {



    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);

        List<Point> result = sphere.findIntsersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntsersections(new Ray(new Point(0.5, 0.5, 0), new Vector(3, 1, 0)));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(p2, result.get(0),"Ray crosses sphere once");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(2, 1, 0),new Vector(3, 1, 0)))
                ,"Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // ** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntsersections(new Ray(new Point(1, -1, 0),new Vector(3, 1, 0)));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(new Point(1.6, -0.8, 0), result.get(0),"Ray crosses sphere once");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(1.6, -0.8, 0),new Vector(3, 1, 0)))
                ,"Ray's line out of sphere");

        // ** Group: Ray's line goes through the center

        //obj for test
        p1 = new Point(0.4, 0.8, 0);
        p2 = new Point(1.6, -0.8, 0);
        Vector v1 = new Vector(-3, 4, 0);
        Vector v2 = new Vector(4, 3, 0);

        // TC13: Ray starts before the sphere (2 points)

        result = sphere.findIntsersections(new Ray(new Point(4, -4, 0),new Vector(-3, 4, 0)));
        assertEquals(2, result.size(),"Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result,"Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntsersections(new Ray(p2, v1));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(new Point(0.4, 0.8, 0), result.get(0),"Ray crosses sphere once");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntsersections(new Ray(new Point(1.3, -0.4, 0),v1));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(p1, result.get(0),"Ray crosses sphere once");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntsersections(new Ray(new Point(1, 0, 0), v1));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(p1, result.get(0),"Ray crosses sphere once");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntsersections(new Ray(p1 ,v1))
                ,"Ray's line out of sphere");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(-0.2, 1.6, 0), v1))
                ,"Ray's line out of sphere");

        // ** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(0.8, 1.1, 0), v1))
                ,"Ray's line out of sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntsersections(new Ray(p1, v2))
                ,"Ray's line out of sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(0.8, 1.1, 0), v2))
                ,"Ray's line out of sphere");

        // ** Group: Special cases

        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull(sphere.findIntsersections(new Ray(new Point(0, 1, 0), v2))
                ,"Ray's line out of sphere");
    }



    /**
     * Test method for
     * {@link Sphere#getNormal(Point)}
     */
    @Test
    public void getNormal() {

        Sphere sp = new Sphere(1.0, new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 2)),"Wrong sphere normal");
    }
}