package lighting;

import java.util.List;

import primitives.Point;

import primitives.Vector;

public interface SourceArea {
    /**
     *The function calculates the beams vectors
     *from points in the target area to the received point
     * @param p the point
     * @return list of beams vectors coming from the target area to the received point
     */
    List<Vector> getLs(Point p);
}
