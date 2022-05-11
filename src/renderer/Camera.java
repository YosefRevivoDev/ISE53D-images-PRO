package renderer;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
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

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    /**
     * Camera constructor receiving three {@link Vector}.
     *
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

    /**
     * Method for creating rays and for every ray gets the color for the pixel
     */
    public void renderImage() {
        // In case that not all of the fields are filled
        if (imageWriter == null || rayTracerBase == null)
            throw new MissingResourceException("Missing", "resource", "exception");

        // The nested loop finds and creates a ray for each pixels, finds its color and
        // writes it to the image pixles
        int nY = this.imageWriter.getNy();
        int nX = this.imageWriter.getNx();

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                imageWriter.writePixel(j, i, castRay(nX, nY, j, i)); // Traces the color of the ray and writes it to the image
            }
        }
    }

    /**
     * The function calculates the ray that hits the pixel and returns the color
     * @return Color
     */
    private Color castRay(int nX, int nY, int j, int i){
        return rayTracerBase.traceRay(constructRayThroughPixel(nX, nY, j, i));
    }

    /**
     * Calculates the ray that goes through the middle of a pixel i,j on the view
     * plane
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return The ray that goes through the middle of a pixel i,j on the view plane
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        // Image center:
        Point pC = p0.add(vTo.scale(this.distance));

        // Ratio:
        double Ry = height / nY;
        double Rx = width / nX;

        // Pixel[i,j] center
        double yi = alignZero(-(i - (nY - 1) / 2.0) * Ry);
        double xj = alignZero((j - (nX - 1) / 2.0) * Rx);

        Point pIJ = pC;

        // To avoid a zero vector exception
        if (xj != 0)
            pIJ = pIJ.add(vRight.scale(xj));
        if (yi != 0)
            pIJ = pIJ.add(vUp.scale(yi));

        Vector vIJ = pIJ.subtract(this.p0);

        return new Ray(p0, vIJ);
    }



    /**
     * The function sends the image writer lines (boundaries) in color and spacing
     * that receiveded lengthwise and widthwise of the image
     * @param interval The interval between the boundaries
     * @param color    The color of the boundaries
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter field is empty", "ImageWriter", "imageWriter");

        for (int i = 0; i < imageWriter.getNx(); i ++)
            for (int j = 0; j < imageWriter.getNy(); j++)
                if (i % interval == 0 && i != 0 || j % interval == 0 && j!=0)
                    imageWriter.writePixel(j, i, color);

        /*for (int j = interval; j < imageWriter.getNy() - 1; j += interval)
            for (int i = 0; i < imageWriter.getNx(); i++)
                imageWriter.writePixel(j, i, color);*/
        }

    /**
     * The function calls the image writer to produce the image that given to it
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter field is empty", "ImageWriter", "imageWriter");
        imageWriter.writeToImage();
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }
}