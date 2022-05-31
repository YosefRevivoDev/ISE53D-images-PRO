package renderer;


import geometries.Intersectable.*;
import geometries.*;
import lighting.LightSource;
import lighting.SourceArea;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    public static final Double3 INITIAL_K = Double3.ONE;
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
    /**
     * traces the ray and its intersections with geometries to find the closest point and return its colour
     *
     * @param ray the ray being traced
     * @return the calculated color of the closest point- to colour thus themathcing pixel
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint interPoint = findClosestIntersection(ray);
        if (interPoint == null) {
            return scene.getBackground();
        }
        return calcColor(interPoint, ray);
    }


    /**
     * @param ray
     * @return The closest intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }


    /**
     * calculate color from the current point
     * @param point
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }

    /**
     * Calculate the color of a certain point
     *
     * @param point
     * @return The color of the point (calculated with local effects)
     */
    private Color calcColor(GeoPoint point, Ray ray, int level, Double3 k) {
        Color color = point._geometry.getEmission().add(calcLocalEffects(point,ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects(point, ray, level, k));
    }

    /**
     * Calculate the effects of lights
     *
     * @param intersection
     * @param ray
     * @return The color resulted by local effecrs calculation
     */
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
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(intersection._point);
            double nl = alignZero(n.dotProduct(l));

            List<Vector> ls;

            // Create or get a creation of shadow rays
            if (lightSource instanceof SourceArea) {
                ls = ((SourceArea) lightSource).getLs(intersection._point);
            } else
                ls = List.of(lightSource.getL(intersection._point));

            Double3 ktr = transparency(lightSource, ls, n, nv, intersection);

                //if (unshaded(lightSource, l, n, intersection))
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection._point).scale(ktr);
                    if (nl * nv > 0) { // checks if sign(nl) == sing(nv)
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
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
        Material material = gp._geometry.getMaterial();
        // Double3 MIN_CALC=new Double3(MIN_CALC_COLOR_K,MIN_CALC_COLOR_K,MIN_CALC_COLOR_K);
        Double3 kr = material.kR;
        Double3 kkr = k.product(kr);
        Vector n = gp._geometry.getNormal(gp._point);//
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(n , gp._point, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }
        Double3 kt = material.kT;
        Double3 kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(n,gp._point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
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
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx).scale(kx));
    }

    /**
     * Calculate the reflection ray
     * @param n
     * @param point
     * @param ray
     * @return The new ray after the reflection calculate
     */
    private Ray constructReflectedRay(Vector n, Point point, Ray ray) {
        Vector v = ray.getDir();
        Vector r = v.subtract(n.scale(alignZero(2 * (n.dotProduct(v)))));
        return new Ray(r.normalize(), point, n);
    }

    /**
     * Calculate the refracted ray
     * @param n
     * @param point
     * @param ray
     * @return The new ray refracted ray
     */
    private Ray constructRefractedRay(Vector n, Point point, Ray ray) {
        return new Ray(ray.getDir(), point, n);
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
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null)
            return true;

        double lightDistance = light.getDistance(gp._point);
        for (GeoPoint geopoint : intersections) {
            if (alignZero(geopoint._point.distance(gp._point) - lightDistance) <= 0)
                if(gp._geometry.getMaterial().kT.equals(Double3.ZERO))
                    return false;
        }
        return true; //in case all intersections are in between lightDistance and gp.
    }

    /**
     * Checks if there is no shade between a point and a light source
     * @param ls
     * @param lList
     * @param n
     * @param nv
     * @param geoPoint
     * @return Double value if the transparency check was successful
     */
    private Double3 transparency(LightSource ls,List<Vector> lList, Vector n, double nv, GeoPoint geoPoint) {

        Double3 sum = Double3.ZERO;
        Material material = geoPoint._geometry.getMaterial();
        for (Vector l : lList) {

            double nl = Util.alignZero(n.dotProduct(l));
            Double3 ktr = Double3.ONE;
            if (nl * nv <= 0) // sign(nl) != sing(nv)
                ktr =ktr.product(material.kT);

            Vector lightDirection = l.scale(-1); // from point to light source
            Ray lightRay = new Ray(lightDirection,geoPoint._point, n);
            List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay,
                    ls.getDistance(geoPoint._point));
            if (intersections != null)
                for (GeoPoint gp : intersections) {
                    ktr = gp._geometry.getMaterial().kT;
                    if (ktr.lowerThan(MIN_CALC_COLOR_K))
                        ktr = Double3.ZERO;
                }
            sum = sum.add(ktr);
        }
        return sum.reduce(lList.size());
    }
}