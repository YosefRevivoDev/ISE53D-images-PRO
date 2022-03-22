package geometries;


import geometries.Plane;
import primitives.Ray;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Planes
 *  @author Yossef Revivo and Chaim Gootwain
 */

class PlaneTest {

    @Test
    public void testFindIntersections() {

        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 2, 1), new Point(2, 0, 1));


        // ============ Equivalence Partitions Tests ==============

        //TC01: Ray's neither orthogonal nor parallel to the plane and intersects the plane (1 points)
        assertEquals(1, plane.findIntsersections(new Ray(new Point(0, -2, 0), new Vector(0, 2, 1))).size(),"Ray's line out of plane");

        //TC02: Ray's neither orthogonal nor parallel to the plane and not intersects the plane (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(0, -1, 0), new Vector(-1, -1, 0))),"Ray's line is in plane");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the plane

        // TC11: Ray included in the plane (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),"Ray's line is not in the plane");

        // TC12: Ray are not included in the plane (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(2, -1, 0), new Vector(-1, 0, 0))),"Ray's line is in plane");

        // **** Group: Ray's line is orthogonal to the plane

        //TC13 Ray is  orthogonal to the plane before (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(0, 1, 0), new Vector(0, 2, 0))),"the Ray is not orthogonal to the plane");

        //TC14 Ray is  orthogonal to the plane after (0 points)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(0, -1, 0), new Vector(0, -2, 0))),"the Ray is not orthogonal to the plane");

        //TC15 Ray is  orthogonal in the plane
        assertEquals(null, plane.findIntsersections(new Ray(new Point(2, 0, 1), new Vector(0, 1, 0))),"the Ray is not orthogonal in the plane");

        //TC16 Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane (Q)
        assertEquals(null, plane.findIntsersections(new Ray(new Point(2, 0, 1), new Vector(0, 2, -1))),"Ray's not start at Q0");

    }

        /**
         * Test method for
         * {@link Plane#getNormal(Point)}
         */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        Plane result = new Plane(
                new Point(0 ,0 ,1),
                new Point(1, 0, 0),
                new Point(0, 1, 0));

        double sqrt3 = Math.sqrt(1d / 3);

        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), result.getNormal(new Point(0, 0, 1)),"Wrong plane normal");
    }
}
