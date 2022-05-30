package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class SpotLight extends PointLight implements LightSource {

    final Vector direction;

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

    @Override
    public Color getIntensity(Point p) {
        double cosA = Util.alignZero(direction.dotProduct(this.getL(p)));
        if (0 > cosA)
            return Color.BLACK;
        return super.getIntensity(p).scale(cosA);
    }

    @Override
    public List<Vector> getLs(Point p) {
        return getLs(direction,p);
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