package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Double3;
import geometries.Cylinder;
import geometries.Tube;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Cylinder
 *  @author Yossef Revivo and Chaim Gootwain
 */

class CylinderTest {

    /**
     * Test method for
     * {@link Cylinder#getNormal(Point)}
     */
    @Test
    void getNormal() {

            Vector v = new Vector(0,0,1);
            Ray r =  new Ray(new Point(0,0,0), v);
            Cylinder cy = new Cylinder(1.0, r, 2.0);

            // ============ Equivalence Partitions Tests ==============
            // TC01: simple test
            assertEquals(new Vector(0,1,0) ,
                    cy.getNormal(new Point(0,1,1)),"Wrong Tube getNormal()");

            // TC02: normal from point on base 1
            assertEquals(v, cy.getNormal(new Point(0.5,0,0)),"Wrong Cylinder getNormal() from point on base 1");

            //TC03: normal from point on base 2
            assertEquals(v, cy.getNormal(new Point(0.5,0,2.0)),"Wrong Cylinder getNormal() from point on base 2");


            // =============== Boundary Values Tests ==================
            //TC04: normal from point on surface and base 1
            assertEquals(v, cy.getNormal(new Point(0,1,0)),"Wrong Cylinder getNormal() from point on base 1");

            //TC05: normal from point on surface and base 2
            assertEquals(v, cy.getNormal(new Point(0,1,2)),"Wrong Cylinder getNormal() from point on base 1" );
    }
}



