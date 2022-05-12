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
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
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
     * @param ray
     * @return The closest intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }


    /**
     * calculate color from the current point
     * @param point
     * @param ray
     * @return
     */
    public Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculate the color of a certain point
     *
     * @param point
     * @return The color of the point (calculated with local effects)
     */
    public Color calcColor(GeoPoint point, Ray ray, int level, Double3 k) {
        Color color = point._geometry.getEmission();
        color = color.add(calcLocalEffects(point, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(point, ray, level, k));
    }


    /**
     * calc Global Effects
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return The color with the global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp._geometry.getNormal(gp._point);
        Material material = gp._geometry.getMaterial();

        Double3 kkr = material.kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(constructReflectedRay(gp._geometry.getNormal(gp._point), gp._point, ray), level, material.kR, kkr);

        Double3 kkt = material.kT.product(k);;
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(calcGlobalEffect(constructRefractedRay(gp._geometry.getNormal(ray.getP0()), gp._point, ray), level, material.kT, kkt));

        return color;
    }


    /**
     * help func for calcGlobalEffects
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx));
    }


    /**
     * Calculate the reflection ray
     * @param n
     * @param point
     * @param inRay
     * @return The new ray after the reflection calculate
     */
    private Ray constructReflectedRay(Vector n, Point point, Ray inRay) {
        Vector v = inRay.getDir();
        Vector r = v.subtract(n.scale(alignZero(2 * (n.dotProduct(v)))));
        return new Ray(r.normalize(), point, n);
    }

    /**
     * Calculate the refracted ray
     * @param n
     * @param point
     * @param inRay
     * @return The new ray refracted ray
     */
    private Ray constructRefractedRay(Vector n, Point point, Ray inRay) {
        return new Ray(inRay.getDir(), point, n);
    }



    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = intersection._geometry.getNormal(intersection._point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection._geometry.getMaterial().nShininess;

        Double3 kd = intersection._geometry.getMaterial().kD;
        Double3 ks = intersection._geometry.getMaterial().kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection._point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // checks if sign(nl) == sing(nv)
                //if (unshaded(lightSource, l, n, intersection))
                Double3 ktr = transparency(lightSource, l, n, intersection);
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection._point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }


    /**
     * Calculates diffusive light
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return The color of diffusive effects
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n));
        if (ln < 0)
            ln = ln * -1;
        return lightIntensity.scale(kd.scale(ln));
    }


    /**
     * Calculate specular light
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return The color of specular reflection
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vr = alignZero(v.scale(-1).dotProduct(r));
        if (vr < 0)
            vr = 0;
        vr = Math.pow(vr, nShininess);
        return lightIntensity.scale(ks.scale(vr));
    }


    /**
     * Checks if there is no shade between a point and a light source
     *
     * @param l
     * @param n
     * @param gp
     * @return Boolean value if the unshaded check was successful
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp._point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;

        double lightDistance = light.getDistance(gp._point);
        for (GeoPoint geop : intersections) {
            if (alignZero(geop._point.distance(gp._point) - lightDistance) <= 0)
                if(gp._geometry.getMaterial().kT.equals(Double3.ZERO))
                    return false;
        }
        return true; //in case all intersections are in between lightDistance and gp.
    }


    /**
     * Checks if there is no shade between a point and a light source
     * @param ls
     * @param l
     * @param n
     * @param geoPoint
     * @return Double value if the transparency check was successful
     */
    private Double3 transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(lightDirection, geoPoint._point, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return new Double3(1.0);

        double lightDistance = ls.getDistance(geoPoint._point);
        Double3 ktr = new Double3(1.0);
        for (GeoPoint geop : intersections) {
            if (alignZero(geop._point.distance(geoPoint._point) - lightDistance) <= 0) {
                ktr = ktr.product(geop._geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }
}