package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
class SphereTest {

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