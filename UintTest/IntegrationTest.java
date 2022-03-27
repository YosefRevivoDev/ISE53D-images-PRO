

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Point.ZERO;

import java.util.List;
 import geometries.*;
 import primitives.*;
import renderer.Camera;


/**
 *
 * Testing Integration between camera and Intersectable shapes
 *
 * @author Shai&Avishay
 *
 */
 class integrationTests {

    /**
     * Test method for Intersections of rays that coming out of a {@link Camera}
     * with {@link Sphere}.
     */
    @Test
    public void SphereIntegrationTests() {

        // TC01: Sphere at front of the view plane (2 points)
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        cam.setVPDistance(1);
        cam.setVPSize(3, 3);
        Sphere sph = new Sphere(1, new Point(0, 0, -3));
        assertEquals(countIntersections(cam, sph), 2, "bad Integration - Sphere at front of the view plane");

        // TC02: Sphere between view plane and camera (18 points)
        cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        cam.setVPDistance(1);
        cam.setVPSize(3, 3);
        sph = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(countIntersections(cam, sph), 18, "bad Integration - Sphere between view plane and camera");

        // TC03: Sphere between view plane and camera (10 points)
        sph = new Sphere(2, new Point(0, 0, -2));
        assertEquals(countIntersections(cam, sph), 10, "bad Integration - Sphere in front of the camera");

        // TC04: Camera and view plane inside the sphere (9 points)
        sph = new Sphere(4, new Point(0, 0, -2));
        assertEquals(countIntersections(cam, sph), 9, "bad Integration - Sphere in front of the camera");

        // TC05: Sphere behind the camera (0 points)
        sph = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(countIntersections(cam, sph), 0, "bad Integration - Sphere behind the camera");

    }

    /**
     * Test method for Intersections of rays that coming out of a camera with
     * {@link Plane}.
     */
    @Test
    public void PlaneIntegrationTests() {
        // TC01: Plane parallel to the view plane (9 points)
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        cam.setVPDistance(1);
        cam.setVPSize(3, 3);
        Plane pl = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        assertEquals(countIntersections(cam, pl), 9, "bad Integration - Plane parallel to the view plane");

        // TC02: Plane is tilted at the top a little down (9 points)
        pl = new Plane(new Point(0, 0, -5), new Vector(0, -0.5, 1));
        assertEquals(countIntersections(cam, pl), 9, "bad Integration - Plane is tilted at the top a little down");

        // TC03: Plane is parallel to all the rays coming out from the bottom of the
        // view plane (6 points)
        pl = new Plane(new Point(0, 0, -5), new Vector(0, -1, 1));
        assertEquals(countIntersections(cam, pl), 6, "bad Integration - Plane is parallel to all the rays coming out from the bottom of the view plane");

    }

    /**
     * Test method for Intersections of rays that coming out of a camera with
     * {@link Triangle}.
     */
    @Test
    public void TriangleIntegrationTests() {
        // TC01: A triangle located in front of the central pixel (1 points)
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        cam.setVPDistance(1);
        cam.setVPSize(3, 3);
        Triangle tr = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(countIntersections(cam, tr), 1, "bad Integration - A triangle located in front of the central pixel");

        // TC01: A triangle located opposite the central pixel and the central upper pixel (2 points)
        tr = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(countIntersections(cam, tr), 2, "bad Integration - A triangle located opposite the central pixel and the central upper pixel");

    }


    /**
     * The function receives a {@link Camera} and {@link Intersectable} and count
     * the Intersections of rays that coming out of the camera with the shape.
     *
     * @param cam   Camera with view plane.
     * @param shape Intersectable shape.
     * @return number of intersections.
     */
    private int countIntersections(Camera cam, Intersectable shape) {
        int count = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<Point> intsPoints = shape.findIntsersections(cam.constructRay(3, 3, j, i));
                if (intsPoints != null)
                    count += intsPoints.size();
            }

        return count;
    }
}
