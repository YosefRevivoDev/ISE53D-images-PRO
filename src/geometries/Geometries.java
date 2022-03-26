package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Geometries is the class representing collection of geometries according to composite pattern
 * @author Yossef
 * @author Chaim
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _geometries = new LinkedList<>();

    /**
     * constructor
     * @param _geometries
     */
    public Geometries(Intersectable... _geometries) {
        add(_geometries);
    }

    /**
     * constructor that recieved array
     * @param geometries
     */
    public void Geometries(Intersectable... geometries) {
        _geometries.addAll(Arrays.asList(geometries));
    }

    // ***************** Operations ******************** //

    /**
     * Adds a new shape to the list
     * @param geometries
     * - The shape to add (One of the realists of Geometry)
     */
    public void add(Intersectable... geometries) {
        _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * @param ray Ray -  the ray that intersect the geometries
     * @return list of Point that intersect the Collection
     */
    @Override
    public List<Point> findIntsersections(Ray ray) {

        if (_geometries.isEmpty()) return null;
        List<Point> intersections = null;

        for (Intersectable geo : _geometries) {
            List<Point> tempIntersections = geo.findIntsersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }
}
