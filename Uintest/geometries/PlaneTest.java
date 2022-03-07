package geometries;


import geometries.Plane;
import primitives.Ray;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;


class PlaneTest {

    @Test
    void getP0() {
    }

    /**
     * Test method for
     * {@link Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        assertEquals(new Vector(3, 9, 1).normalize(),
                new Plane(
                        new Point(-1, 1, 2),
                        new Point(-4, 2, 2),
                        new Point(-2, 1, 5)).getNormal(null),"Wrong plane normal");
    }
}






