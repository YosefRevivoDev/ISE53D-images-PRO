package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Util;
import primitives.Vector;
import java.util.LinkedList;
import java.util.List;

import java.util.Random;

public class PointLight extends Light implements LightSource, SourceArea {

    private Point position;
    private Double3 kC = Double3.ONE;
    private Double3 kL = Double3.ZERO;
    private Double3 kQ = Double3.ZERO;

    public double radius;
    private int xAndY;// not sure

    public PointLight(Color intensity, Point _position) {
        super(intensity);
        position = _position;
        this.kC = Double3.ONE;
        this.kL = Double3.ZERO;
        this.kQ = Double3.ZERO;
        this.radius = 0;
        this.xAndY = 1;
    }

    public PointLight setkC(double kC) {
        this.kC = new Double3(kC);
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = new Double3(kL);
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }

    /**
     * Builder pattern Setter
     *
     * @param radius the radius to set
     */
    public PointLight setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Builder pattern Setter
     *
     * @param beamsNum the beamsNum to set
     */
    public PointLight setBeamsNum(int beamsNum) {
        this.xAndY = (int) Math.sqrt(beamsNum);
        return this;
    }


//    @Override
//    public Color getIntensity() {
//        return super.getIntensity();
//    }


    //protected double intensityHelp(Point p) {
    //   double ds = p.distanceSquared(position);
     //   double d = p.distance(position);
     //   return (kC + d * kL + ds * kQ);
   // }

    public Color getIntensity(Point p) {

        Color lightIntensity = getIntensity();

        double ds = p.distanceSquared(position);
        double d = p.distance(position);
        Double3 denominator = kC.add(kL.scale(d)).add( kQ.scale(ds));

        return lightIntensity.reduce(denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public List<Vector> getLs(Point p) {
        return getLs(getL(p), p);

    }

    /**
     * Help function for producing a target area and calculating the light beams
     * coming from it to the received point
     *
     * @param p the point
     * @param vCenter orthogonal to the target area
     * @return light beams vectors coming from the area to the point
     */
    protected List<Vector> getLs(Vector vCenter, Point p) {

        LinkedList<Vector> beams = new LinkedList<>(List.of(getL(p)));

        if (radius == 0 || xAndY == 1)
            return beams;

        //*********Find the vectors that define the slope of the help grid***************
        Vector vUp = vCenter.getOrthogonal();
        Vector vRight = vCenter.crossProduct(vUp).normalize();

        // ******************************************************************************

        double interval = (2 * radius) / xAndY;//The length and width of each square in the grid
        Point pI = position.getCopy();

        // ****If the center is on the grid — move it to the nearest upper left pixel****
        if (xAndY % 2 == 0) {
            pI = pI.add(vRight.scale(-interval / 2));
            pI = pI.add(vUp.scale(interval / 2));
        }
        // ******************************************************************************

        // ---------------------Find the center point of the first pixel--------------------

        double yI = -(0 - (xAndY - 1) / 2) * interval;
        double xJ = (0 - (xAndY - 1) / 2) * interval;

        // ********The conditions Prevent a situation that creates a zero
        // vector*********
        // ******(When the desired pixel center is on one of the axes of the
        // plane)******
        if (xJ != 0)
            pI = pI.add(vRight.scale(xJ));
        if (yI != 0)
            pI = pI.add(vUp.scale(yI));
        // ******************************************************************************

        // -----------------------------------------------------------------------------------

        Random rand = new Random();

        for (int i = 0; i < xAndY; i++, pI = pI.add(vUp.scale(-interval))) {

            Point pJ = pI.getCopy();

            for (int j = 0; j < xAndY; j++, pJ = pJ.add(vRight.scale(interval))) {

                Point pIJ = pJ.getCopy();


                double movementR = rand.nextDouble() * interval - interval / 2;
                double movementU = rand.nextDouble() * interval - interval / 2;

                // ********The conditions Prevent a situation that creates a zero vector*****
                // *********(When the desired pixel is on one of the axes of the plane)******
                if (!Util.isZero(movementR))
                    pIJ = pIJ.add(vRight.scale(movementR));
                if (!Util.isZero(movementU))
                    pIJ = pIJ.add(vUp.scale(movementU));
                // ******************************************************************************

                double distance = position.distance(pIJ);
                if (distance > radius) {
                    Vector revVector = position.subtract(pIJ).normalize();
                    pIJ = pIJ.add(revVector.scale(distance - radius));
                }
                beams.add(p.subtract(pIJ).normalize());
            }
        }
        return beams;
    }

    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }
}