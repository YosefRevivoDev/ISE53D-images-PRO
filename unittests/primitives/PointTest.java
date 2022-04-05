package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Point class
 * @author Yosef Revivo + Chaim Gootwain
 */
class PointTest {

    Point p0 = new Point(1,2,3);
    Point p1 = new Point(1,1,1);

    /**
     * Test method for {@link Point#distanceSquared(Point)} )}
     */
    @Test
    void testDistanceSquared() {
        assertEquals(p0.distanceSquared(p1),5, "distanceSquared doesnt work properly");
    }

    /**
     * Test method for {@link Point#distance(Point)} )}
     */
    @Test
    void testDistance() {
        assertEquals(p0.distance(p1),Math.sqrt(5),"distance doesn't work properly");
    }

    /**
     * Test method for { primitives.Point# add (primitives.Point)}.
     */
    @Test
    void TestAdd() {

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(4,5,6), p0.add(new Vector(3,3,3)), "Not could add a point");
    }

    @Test
    void subtract() {
      assertEquals(new Vector(2,4,6), p0.subtract(new Point(-1,-2,-3)), "Error sub: Not could sub the point" );
    }


}