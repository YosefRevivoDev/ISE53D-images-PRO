package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {

    public List<GeoPoint> findIntsersections(Ray ray);

    /**
     * GeoPoint is just a tuple holding
     * references to a specific point in a specific geometry
     */
    public static class GeoPoint {

        public Geometry _geometry;
        public Point _point;

        // ***************** Constructors ********************** //

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
    }




}
