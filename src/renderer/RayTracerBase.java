package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * RayTracerBase constructor receiving {@link scene}.
     *
     * @param scene the photographed scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * The function checks what color the ray coming out towards the scene meets
     *
     * @param ray
     * @return Color The color of the points that the ray meets
     */
    public abstract Color traceRay(Ray ray);
    //public abstract Color calcColor(Intersectable.GeoPoint p, Ray ray, int level, double k);
    //public abstract Point findClosestIntersection(Ray ray);


}
