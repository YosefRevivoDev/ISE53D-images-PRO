package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Plane;
import geometries.Polygon;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Triangle
 *  @author Yossef Revivo and Chaim Gootwain
 */

public class TriangleTest {

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
