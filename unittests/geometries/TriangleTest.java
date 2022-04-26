package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


/**
 * Testing Triangle
 *  @author Yossef Revivo and Chaim Gootwain
 */

public class TriangleTest {

    /**
     * Test Method for
     * {@link Triangle#findIntsersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point(0,1,0),
                new Point(1,0,0),
                new Point(0.5, 0.5, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside triangle (1 point)
        assertEquals(List.of(new Point(0.48, 0.52, 0.23)),
                triangle.findIntersections(new Ray(new Point(-1.6, 0, 0), new Vector(2.08, 0.52,0.23))),"line is inside triangle");

        // TC02: Ray's line is outside against edge (0 points)
        assertEquals(null, triangle.findIntersections(new Ray(new Point(-1.6, 0, 0), new Vector(1.6, 1,0.33))),"line is outside against edge");

        // TC03: Ray's line is outside against vertex (0 points)
        assertEquals(null, triangle.findIntersections(new Ray(new Point(-1.6, 0, 0), new Vector(2.1, 0.5,1.31))),"line is outside against vertex" );


        // =============== Boundary Values Tests ==================

        // TC11: Ray's line is on edge (0 points)
        assertEquals(null, triangle.findIntersections(new Ray(new Point(-1.6, 0, 0), new Vector(1.76, 0.84,0.33))),"line is on edge");

        //TC12: Ray's line is on vertex (O points)
        assertEquals(null, triangle.findIntersections(new Ray(new Point(-1.6, 0, 0), new Vector(2.1, 0.5,1))),"line is on vertex");

        //TC13: Ray's line is on edge's continuation (0 points)
        assertEquals(null, triangle.findIntersections(new Ray(new Point(-1.6, 0, 0), new Vector(1.9, 0.7,1.41))),"line is on edge's continuation");
    }

    /**
     * Test method for
     * {@link Triangle#getNormal(Point)}
     */
    @Test
    void getNormal() {
// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point(0, 0, 1)),"Bad normal to triangle");
    }
}
