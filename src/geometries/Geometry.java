package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    public abstract Vector getNormal(Point point);

    /**
     *A function that returns the power of the image
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     *
     * @param emission
     */
    public void setEmission(Color emission) {
        this.emission = emission;
    }

}
