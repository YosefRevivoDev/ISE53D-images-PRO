package renderer;

import geometries.Intersectable.*;
import geometries.*;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
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
        List<GeoPoint> interPoint = scene.geometries.findGeoIntersections(ray);
        if (interPoint == null){
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(interPoint);
        return calcColor(closestPoint,ray);
    }

    /**
     * calculate color from the current point
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray) {

        return scene.getAmbientLight().getIntensity()
                .add(calcLocalEffects(gp, ray));
    }

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp._geometry.getEmission();
        Vector v = ray.getDir().normalize();
        Vector n = gp._geometry.getNormal(gp._point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp._geometry.getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp._point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp._point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();
        return material.kS.scale( Math.pow(Math.max(0, r.dotProduct(v.scale(-1d))), material.nShininess));

    }

    /**
     * checks whether a point on  a geometry is shaded
     * @param gp
     * @param lightSource
     * @param n
     * @param nl
     * @return
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector n, double nl, double nv, Vector l){
        Point point = gp._point;
        //  Vector l = lightSource.getL(point);
        Vector lightDirection  = l.scale(-1);
        Vector delVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point pointRay = point.add(delVector);
        Ray lightRay = new Ray(gp._point, lightDirection);
        double maxDistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);
        return intersections == null;
    }
}

