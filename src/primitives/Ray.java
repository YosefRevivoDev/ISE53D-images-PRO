package primitives;

import java.util.Objects;

public class Ray {

    private final Point P0;
    private final Vector dir;


    public Ray(Point p0, Vector dir) {
        P0 = p0;
        this.dir = dir;
    }

    public Point getP0() {
        return P0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray other = (Ray) o;
        return this.P0.equals(other.P0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "P0=" + P0 +
                ", dir=" + dir +
                '}';
    }
}
