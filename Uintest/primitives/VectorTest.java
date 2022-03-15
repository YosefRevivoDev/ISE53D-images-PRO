package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *  @author Yossef Revivo and Chaim Gootwain
 */

class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void testAdd (){
        // =============== Boundary Values Tests ==================
        //TC11: test if operator add succeed
        assertEquals(new Vector(1, 0, 0), v1.add(new Vector(0, -2, -3)),"ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1,1,1).scale(2), new Vector(2,2,2),"Wrong vector scale");
    }

    @Test
    void testLengthSquared() {

        // =============== Boundary Values Tests ==================
        //TC11: test lengthSquared..
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {

        // =============== Boundary Values Tests ==================
        //TC11: test length..
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }

    @Test
    void testDotProduct() {

        // =============== Boundary Values Tests ==================
        // TC11: test if orthogonal vectors is not zero
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");

        //TC12: test if wrong value
        assertEquals(v1.dotProduct(v2) ,-28,0.00001, "ERROR: dotProduct() wrong value");
    }

    @Test
    void testCrossProduct() {
        Vector vTest = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector vTest2 = new Vector(0, 3, -2);
        Vector vTemp = vTest.crossProduct(vTest2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(vTest.length() * vTest2.length(), vTemp.length(), 0.00001, "crossProduct() Wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vTemp.dotProduct(vTest)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vTemp.dotProduct(vTest2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector vTest3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> vTest.crossProduct(vTest3), "(crossProduct() for parallel vectors does not throw an exception");
    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(1,2,3);
        Vector u = v.normalize();

        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");

        // =============== Boundary Values Tests ==================

        //TC03: normalized() does not create a new vector
        if (u == v)
            fail("ERROR: normalized() function does not create a new vector");
    }
}