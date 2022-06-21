package renderer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static primitives.Util.isZero;
import primitives.*;
import primitives.Vector;



public class Camera {

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;
    final String adaptive ="adaptive";
    final String superSampling = "superSampling";
    final String regular = "regular";

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;
    private int amountOfRaysSampling = 3;
    private int adaptiveMaxRecursionLevel = 5;
    private double printInterval;
    private String superSamplingType;

    /**
     * Camera constructor receiving three {@link Vector}.
     * @param p0  the position of the camera
     * @param vUp "Rotate" the camera
     * @param vTo The direction in which the camera is facing
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("\"up and to\" vectors are not orthogonal");
        }
        this.p0 = p0;

        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    public Camera setMultithreading(int interval) {
        this.printInterval = interval;
        return this;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public void setvUp(Vector vUp) {
        this.vUp = vUp;
    }

    public void setvTo(Vector vTo) {
        this.vTo = vTo;
    }

    public void setvRight(Vector vRight) {
        this.vRight = vRight;
    }

    /**
     * //@param distance the width and height to set
     * @return this camera
     */
    public Camera setVPSize(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * @param distance the distance (from ViewPlane) to set
     * @return this camera
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    //set number of rays to supersample
    public Camera setNumOfRaysSupersampling(int amountOfRaysSampling) {
        this.amountOfRaysSampling = amountOfRaysSampling;
        return this;
    }
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    //set adaptiveMaxRecursionLevel
    public Camera setAdaptiveMaxRecursionLevel(int adaptiveMaxRecursionLevel) {
        this.adaptiveMaxRecursionLevel = adaptiveMaxRecursionLevel;
        return this;
    }

    //set supersamplingType
    public Camera setSupersamplingType(String type) {
        this.superSamplingType = type;
        return this;
    }

    /**
     * The function construct a ray from the camera towards a desired pixel
     * @param nX number of columns
     * @param nY number of rows
     * @param j  Pixel column index
     * @param i  Pixel row index
     * @return the ray from the camera towards a desired pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i)  {

        //Calculation of the center of the view plane
        Point pCenter = p0.add(vTo.scale(distance));

        double Ry = height / nY, Rx = width / nX;

        //If the center is on the grid — move it to the nearest upper left pixel
        if (nX % 2 == 0)
            pCenter = pCenter.add(vRight.scale(-Rx / 2));
        if (nY % 2 == 0)
            pCenter = pCenter.add(vUp.scale(Ry / 2));

        //Find the center point of the desired pixel
        double yI = -(i - (nY - 1) / 2) * Ry;
        double xJ = (j - (nX - 1) / 2) * Rx;
        Point pIJ = pCenter;

        //The conditions prevent a situation that creates a zero vector when the desired pixel center is on one of the axes of the plane
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));

        return new Ray(p0, pIJ.subtract(p0));
    }
    public Ray constructRayThroughPoint(Point point) {
        return new Ray(this.p0, point.subtract(this.p0));
    }

    /**
     * Method for creating rays and for every ray gets the color for the pixel
     * with threding
     */
    public Camera renderImage(boolean threads) {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            if (threads == true) {
                Pixel.initialize(nY, nX, printInterval);
                IntStream.range(0, nY).parallel().forEach(i -> {
                    IntStream.range(0, nX).parallel().forEach(j -> {
                        Color pixelColor = castRay(nX, nY, j, i);
                        imageWriter.writePixel(i, j, pixelColor);
                        Pixel.pixelDone();
                        Pixel.printPixel();
                    });
                });
            }
            else{
                for (int i = 0; i < nY; i++) {
                    for (int j = 0; j < nX; j++) {
                        Ray ray = constructRay(nX, nY, j, i);
                        Color pixelColor = rayTracerBase.traceRay(ray);
                        imageWriter.writePixel(j, i, pixelColor);
                    }
                }
            }
            return this;
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }


    private Color calcAdaptiveColor(Ray rayCenter, double Width, double Height,  int level) {

        // compute half cell pixel width for locating rays and for recursive calls
        double halfbreadth = Width / 2;
        double halfHeight = Height / 2;

        // calculate the centers of each quarter of the cell pixel
        List<Ray> quartreRays = this.constructAdaptiveSupersamplingRays(rayCenter, halfbreadth, halfHeight);

        // get colors for each ray
        List<Color> ColorsInQuarter = quartreRays.stream().map(ray -> rayTracerBase.traceRay(ray)).collect(Collectors.toList());

        // stop when maximum recursion level is reached
        if (level <= 1) {
            // return average of the quadrant colors
            return ColorsInQuarter.get(0).add(ColorsInQuarter.get(1), ColorsInQuarter.get(2), ColorsInQuarter.get(3)).reduce(4);
        }

        // if all centers are the same color, return any quadrant color
        if (ColorsInQuarter.get(0).getColor().equals(ColorsInQuarter.get(1).getColor())//
                && ColorsInQuarter.get(0).getColor().equals(ColorsInQuarter.get(2).getColor()) //
                && ColorsInQuarter.get(0).getColor().equals(ColorsInQuarter.get(3).getColor())) {
            return ColorsInQuarter.get(0);
        }

        // calculate average colors of the four quadrants
        return calcAdaptiveColor(quartreRays.get(0), halfbreadth, halfHeight,  level - 1)
                .add(calcAdaptiveColor(quartreRays.get(1), halfbreadth, halfHeight,  level - 1),
                        calcAdaptiveColor(quartreRays.get(2), halfbreadth, halfHeight,  level - 1),
                        calcAdaptiveColor(quartreRays.get(3), halfbreadth, halfHeight, level - 1))
                .reduce(4);
    }

    public List<Ray> constructAdaptiveSupersamplingRays(Ray center, double halfbreadth, double halfHeight) {
        // position of top left ray center ray
        Point upLeft = center.getPoint(distance).add(vRight.scale(-halfbreadth / 2))
                .add(vUp.scale(halfHeight / 2));
        return createGridOfBeams(upLeft, 2, halfHeight, halfbreadth);
    }

    public List<Ray> createGridOfBeams(Point upLeft, int gridSize, double intervalsVertical,
                                         double intervalsHorizontal) {
        List<Ray> rays = new ArrayList<>();
        // create grid of rays for supersampling
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Point newPoint = upLeft;
                if (row > 0) {
                    newPoint = newPoint.add(vUp.scale(-row * intervalsVertical));
                }
                if (col > 0) {
                    newPoint = newPoint.add(vRight.scale(col * intervalsHorizontal));
                }
                rays.add(constructRayThroughPoint(newPoint));
            }
        }
        return rays;
    }


    /**
     * The function calculates the ray that hits the pixel and returns the color
     * @return Color
     */
    private Color castRay(int nX, int nY, int j, int i){
        Ray pixelRay = this.constructRay(nX,nY,i,j);
        Color pixelColor = null;
        //pixelWidth  camera width / number of pixels wide
        //pixelHeight camera height / number of pixels high
        double pixelWidth = this.width / imageWriter.getNx();
        double pixelHeight = this.height / imageWriter.getNy();

        switch(superSamplingType){
            case adaptive:
                pixelColor = calcAdaptiveColor(pixelRay, pixelWidth, pixelHeight, adaptiveMaxRecursionLevel);
                break;
            case superSampling:
                pixelColor = calcSupersamplingColor(nX,nY,i,j, amountOfRaysSampling);
                break;
            case regular:
                pixelColor = rayTracerBase.traceRay(pixelRay);
                break;
        }
        imageWriter.writePixel(i, j, pixelColor);
        return rayTracerBase.traceRay(pixelRay);
    }

    public Color calcSupersamplingColor(int x,int  y,int  i,int  j,int numOfRays) {
        Color pixelColor = Color.BLACK; //the base color of the pixel
        for (int k = -1 * numOfRays / 2; k < numOfRays / 2 + 1; ++k) { //grid pattern for each pixel x
            for (int f = -1 * numOfRays / 2; f < numOfRays / 2 + 1; ++f) { //grid pattern for each pixel y
                Ray pixelRay = constructRayThroughPixelForSampling(x, y, i, j, k, f, numOfRays); //construct the ray through the pixel with the grid pattern
                pixelColor = pixelColor.add(rayTracerBase.traceRay(pixelRay)); //add the color of the ray to the pixel color
            }
        }
        if (numOfRays % 2 == 0) //because the grid pattern is odd we need to divide by the number of rays otherwise we will get the sum of the rays
            pixelColor = pixelColor.reduce(numOfRays * numOfRays + numOfRays * 2);
        else
            pixelColor = pixelColor.reduce(numOfRays * numOfRays);
        return pixelColor;
    }

    /**
     * Calculates the ray that goes through the middle of a pixel i,j on the view
     * plane
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return The ray that goes through the middle of a pixel i,j on the view plane
     */
    public Ray constructRayThroughPixelForSampling(int nX, int nY, int j, int i, int k, int f, int numOfrays) {
        Point Pc = p0.add(vTo.scale(distance)); // get the center of the image

        double Ry = height / nY; // get the hight of the image
        double Rx = width / nX; // get the width of the image
        //Pixel[i,j] center

        // changes the point for super sampling
        if (!(k == 0)) //if k is not 0 we need to change a little bit the point to get the super sampling affect
            Pc = Pc.add(new Vector(Rx / numOfrays, Rx / numOfrays, Rx / numOfrays).scale(k));
        if (!(f == 0)) //same thing for f
            Pc = Pc.add(new Vector(Ry / numOfrays, Ry / numOfrays, Ry / numOfrays).scale(f));

        // get the point of the pixel
        double Yi = ((i - (nY - 1) / 2d) * Ry) * -1;
        double Xj = ((j - (nX - 1) / 2d) * Rx) * -1;
        Point Pij = Pc;

        if (Xj != 0) // if x is not 0 we need to add the vector to the point
            Pij = Pij.add(vRight.scale(Xj).scale(-1));
        if (Yi != 0) // if y is not 0 we need to add the vector to the point
            Pij = Pij.add(vUp.scale(Yi));

        Vector Vij = Pij.subtract(p0); // get the vector from the camera to the pixel
        return new Ray(p0,Vij.normalize()); // create a ray from the camera to the pixel
    }

    /**
     * The function sends the image writer lines (boundaries) in color and spacing
     * that receiveded lengthwise and widthwise of the image
     * @param interval The interval between the boundaries
     * @param color    The color of the boundaries
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter field is empty", "ImageWriter", "imageWriter");
        int x = imageWriter.getNx();
        int y = imageWriter.getNy();
        for (int i=0;i<x;i++)
        {
            for (int j =0;j<y;++j)
            {
                if(i % interval == 0 || j % interval ==0)
                    imageWriter.writePixel(i,j,color);
            }
        }
        return this;
    }

    /**
     * The function calls the image writer to produce the image that given to it
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter field is empty", "ImageWriter", "imageWriter");
        imageWriter.writeToImage();
    }


    public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    public double getDistance(Point point) {
        return p0.distance(point);
    }
}