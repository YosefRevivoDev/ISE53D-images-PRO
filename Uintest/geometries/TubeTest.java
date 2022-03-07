package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Tube;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class TubeTest {

    @Test
    void getNormal() {

        Vector v = new Vector(0,0,1);
        Ray r =  new Ray(new Point(0,0,0), v);
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        assertEquals(new Vector(0,1,0),new Tube(1.0, r).getNormal(new Point(0,1,1)),"Wrong Tube getNormal()");
    }
}