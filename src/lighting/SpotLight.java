package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight implements LightSource {

    final Vector direction;
    private double radius;

    /**
     *
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Constructor of Spotlight which receives four params
     *
     * @param intensity light intensity
     * @param position  Light starts location
     * @param direction direction of the light
     * @param radius
     */
    public SpotLight(Color intensity, Point position, Vector direction, double radius) {
        super(intensity, position);
        this.direction = direction.normalize();
        this.radius = radius;
    }

    public Color getIntensity(Point p)
    {
        double projection = direction.dotProduct(getL(p));
        double factor = Math.max(0, projection);

        Color pointIntensity = super.getIntensity(p);
        return pointIntensity.scale(factor);
    }
}


//    private SpotLight(SpotLightBuilder builder,Color intensity, Point position,)
//    {
//        super(intensity, position);
//        this.direction=builder.direction;
//        this.kC=builder.kC;
//        this.kL=builder.kL;
//        this.kQ=builder.kQ;
//    }

//    public static class SpotLightBuilder {
//        private final Vector direction;
//        private final double kC;
//        private final double kL;
//        private final double kQ;
//
//        public SpotLightBuilder(Vector direction, double kC, double kL, double kQ) {
//            this.direction = direction;
//            this.kC = kC;
//            this.kL = kL;
//            this.kQ = kQ;
//        }
//
//    }