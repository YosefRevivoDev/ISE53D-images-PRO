package geometries;

import primitives.*;
import primitives.Ray;
import java.util.stream.Collectors;
import java.util.Objects;

import java.util.*;

public abstract class Intersectable {

    /**
     * returns list of intersections between ray and the intersectable
     * @param ray Ray
     * @return list of intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp._point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     * GeoPoint is just a tuple holding
     * references to a specific point in a specific geometry
     */
    public static class GeoPoint {

        public Geometry _geometry;
        public Point _point;


        /**
         * @param _geometry
         * @param _point
         */
        public GeoPoint(Geometry _geometry, Point _point) {
            this._geometry = _geometry;
            this._point = _point;
        }

        /**
         * @return _point
         */
        public Point getPoint(){
            return _point;
        }

        /**
         * @return _geometry
         */
        public Geometry getGeometry(){
            return _geometry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(_geometry, geoPoint._geometry) &&
                    Objects.equals(_point, geoPoint._point);
        }
    }
    @Override
    public String toString() {
        return "Intersectable{}";
    }
 }
