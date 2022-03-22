package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public interface Geometry extends Intersectable {

    Vector getNormal(Point point);

}
