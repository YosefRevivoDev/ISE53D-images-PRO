package primitives;

import java.util.Objects;

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
        this.dir = dir;
    }
    public Point getPoint() { return (_p0);}

    public Point getP0() {
        return _p0;
    }

    public Vector getDir() { return (dir);}

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
