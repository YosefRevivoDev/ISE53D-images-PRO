package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 */
class PointTest {

    /**
     * Test method for { primitives.Point# add (primitives.Point)}.
     */

    @Test
    void add() {
        Point PT = new Point( 3,5,7);
        // ============ Equivalence Partitions Tests ==============1,5,12
        Point PT2 = new Point(2,0,-5 );
    }

    @Test
    void subtract() {

        //if (!new Vector(1, 1, 1).equals(new Point(2, 3, 4).subtract(p1)))
        //            out.println("ERROR: Point - Point does not work correctly");

    }
}