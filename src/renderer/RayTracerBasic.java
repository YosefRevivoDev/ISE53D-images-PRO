package renderer;

import geometries.Intersectable.*;
import primitives.*;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    /**
     * RayTracerBasic constructor receiving {@link scene}.
     *
     * @param scene the photographed scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * The function checks what color the ray coming out towards the scene meets
     * @param ray the ray coming out towards the scene
     * @return Color The color of the points that the ray meets
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> interPoint = scene.geometries.findIntsersections(ray);
        if (interPoint == null)
            return scene.background;
        return calcColor(ray.findClosestPoint(interPoint));
    }

    /**
     * calculate color from the current point
     * @param point
     * @return
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}

