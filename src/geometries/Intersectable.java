package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

public interface Intersectable {

    /**
     * returns list of intersections between ray and the intersectable
     * @param ray Ray
     * @return list of intersections
     */
    public List<GeoPoint> findIntsersections(Ray ray);

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
 }
