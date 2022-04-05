package primitives;

import geometries.Intersectable;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class defines us the RAY by a group of
 * points that are on the far side of the point and are called "RAY head"
 */
public class Ray {

    private Point _p0;
    private Vector dir;

    /**
     * Constructor of point vector normalized direction
     * @param p0
     * @param dir
     */
    public Ray(Point p0, Vector dir) {
        _p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getPoint() { return (_p0);}

    public Point getP0() {
        return _p0;
    }

    public Point getPoint(double t) {
        return getP0().add(getDir().scale(t));
    }

    public Vector getDir() { return (dir);}

    /**
     * @param length
     * @return new Point
     */
    public Point getTargetPoint(double length) {
        return isZero(length ) ? _p0 : _p0.add(dir.scale(length));
    }

    public Point findClosestPoint(List<Point> pointList) {
        if (pointList == null || pointList.size() == 0)
            return null;
        Point closestPoint  = pointList.get(0);
        double distance = _p0.distance(closestPoint );
        for (Point point : pointList) {
            double newDist = point.distance(_p0);
            if (newDist < distance) {
                distance = newDist;
                closestPoint  = point;
            }
        }
        return closestPoint ;
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
