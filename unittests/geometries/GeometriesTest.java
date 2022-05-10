package geometries;

import static org.junit.jupiter.api.Assertions.*;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Point;

import java.util.List;

public class GeometriesTest {

    /**
     * Test Method for
     * {@link Geometries#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() {
        Sphere sphere = new Sphere(1d, new Point(3, 0 ,0));
        Triangle triangle = new Triangle(
                new Point(0,1,0), new Point(0,4,0), new Point(0,3,1.82));
        Plane plane = new Plane(new Point(1,0,0), new Point(3,4,0), new Point(2,2,3));

        // ============ Equivalence Partitions Tests ==============

        //TC01: Objects Collection is empty (0 points)
        Geometries geometries = new Geometries();
        assertEquals(null, geometries.findIntersections(new Ray(new Point(5.07,-2.24,0), new Vector(-5.07, 5.24, 0.62))),
                "Collection is empty");

        geometries.add(sphere, triangle, plane);

        //TC02: No geometry is intersected (0 points)
        assertEquals(null, geometries.findIntersections(new Ray(new Point(2,-3,0), new Vector(5, 0, 0))),
                "No geometry is intersected");

        //TC03: Only one geometry is intersected (1 point)
        assertEquals(1, (geometries.findIntersections(new Ray(new Point(2,-3,0), new Vector(-2, 1, 1.94)))).size(),"one geometry is intersected");

        //TC04: All the geometries are intersected (4 points)
        assertEquals(4, (geometries.findIntersections(new Ray(new Point(5.07,-2.24,0), new Vector(-5.07, 5.24, 0.62)))).size(),"All geometries are intersected");

        // =============== Boundary Values Tests ==================

        //TC11: Many (But not All) geometries are intersected (2 points)
        assertEquals(2, (geometries.findIntersections(new Ray(new Point(2,-3,0), new Vector(-2, 6, 0.62)))).size(), "Not all geometries intersected");

    }
}