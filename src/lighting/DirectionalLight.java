package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    /**
     * @param intensity
     */

    private final Vector Direction;

    /**
     *
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        Direction = direction.normalize();
    }



    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    public Vector getL(Point p)
    {
        return Direction;

    }
}
