package renderer;


import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Point.ZERO;

class CameraTest {


    @Test
    void testConstructRay() {

        Camera camera = new Camera(ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setVPDistance(10);

        // ============ Equivalence Partitions Tests ==============

        // TC01: 4X4 Inside (1,1)
        assertEquals(new Ray(ZERO, new Vector(-1, -1, 10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 1),"Wrong Ray");

        // TC02: 4X4 Corner (0,0)
        assertEquals(new Ray(ZERO, new Vector(-3, -3, 10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 0, 0),"Wrong Ray");

        // TC03: 4X4 Side (0,1)
        assertEquals(new Ray(ZERO, new Vector(-1, -3, 10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 0),"Wrong Ray");

        // TC04: 3X3 Corner (0,0)
        assertEquals(new Ray(ZERO, new Vector(-2, -2, 10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 0),"Wrong Ray");


        // =============== Boundary Values Tests ==================
        // BV01: 3X3 Center (1,1)
        assertEquals(new Ray(ZERO, new Vector(0, 0, 10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 1, 1), "Wrong Ray");

        // BV02: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(ZERO, new Vector(0, -2, 10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 1, 0), "Wrong Ray");

        // BV03: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(ZERO, new Vector(-2, 0, 10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 1), "Wrong Ray");

        // BV04: 3X3 Corner (0,0)
        assertEquals(new Ray(ZERO, new Vector(-2, -2, 10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 0), "Wrong Ray");

        // BV05: 4X4 Corner (0,0)
        assertEquals(new Ray(ZERO, new Vector(-3, -3, 10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 0, 0), "Wrong Ray");

        // BV06: 4X4 Side (0,1)
        assertEquals(new Ray(ZERO, new Vector(-1, -3, 10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 0), "Wrong Ray");

    }
}