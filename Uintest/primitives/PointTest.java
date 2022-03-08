package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 * @author Yosef Revivo + Chaim Gootwain
 */
class PointTest {

    Point po = new Point(1,2,3);
    /**
     * Test method for { primitives.Point# add (primitives.Point)}.
     */

    @Test
    void TestAdd() {

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(4,5,6), po.add(new Vector(3,3,3)), "Not could add a point");
    }

    @Test
    void subtract() {
      assertEquals(new Point(0,0,0), po.subtract(new Point(-1,-2,-3)), "Error sub: Not could sub the point" );
    }
}