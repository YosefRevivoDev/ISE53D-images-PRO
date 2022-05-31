package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import renderer.Camera;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * This class defines us the RAY by a group of
 * points that are on the far side of the point and are called "RAY head"
 */
public class Ray {

    private Point _p0;
    private Vector dir;
    private static final double DELTA = 0.1;

    /**
     * Constructor of point vector normalized direction
     * @param p0
     * @param dir
     */
    public Ray(Point p0, Vector dir) {
        _p0 = p0;
        this.dir = dir.normalize();
    }


    /**
     * Constructor calculate the movement normal by delta
     * @param vecDir
     * @param p
     * @param n
     */
    public Ray(Vector vecDir, Point p , Vector n) {
        dir = vecDir.normalize();
        Vector delta = n.scale(alignZero(n.dotProduct(dir) > 0 ? DELTA : -DELTA));
        _p0 = p.add(delta);
    }

    public Point getPoint() { return (_p0);}

    public Point getP0() {
        return _p0;
    }

    public Point getPoint(double t) {
        return getP0().add(getDir().scale(t));
    }

    public Vector getDir() { return dir;}


    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList())._point;
    }


    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {

        double minDistance = Double.MAX_VALUE;
        double pointDistance;
        GeoPoint closestPoint = null;
        for (GeoPoint geopoint : geoPoints) {
            pointDistance = geopoint._point.distanceSquared(_p0);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestPoint = geopoint;
            }
        }
        return closestPoint;
    }

    /**
     * @param length
     * @return new Point
     */
    public Point getTargetPoint(double length) {
        return isZero(length ) ? _p0 : _p0.add(dir.scale(length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray other = (Ray) o;
        return this._p0.equals(other._p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "P0=" + _p0 +
                ", dir=" + dir +
                '}';
    }
}
