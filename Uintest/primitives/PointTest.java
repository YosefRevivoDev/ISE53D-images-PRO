package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 * @author Yosef Revivo + Chaim Gootwain
 */
class PointTest {

    Point po = new Point(0,3,3);
    /**
     * Test method for { primitives.Point# add (primitives.Point)}.
     */

    @Test
    void TestAdd() {

        // ============ Equivalence Partitions Tests ==============1,5,12
        assertEquals(new Point(0,3,1), po.add(new Vector(0,1,0)), "Not could add a point");
    }

    @Test
    void subtract() {
      assertEquals(new Point(0,3,6), po.subtract(new Point(0,9,9)), "Error sub: Not could sub the point" );
    }
}