package renderer;

import java.util.MissingResourceException;
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
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null)
                throw new MissingResourceException("imageWriter field is empty", "ImageWriter", "imageWriter");
            if (rayTracerBase == null)
                throw new MissingResourceException("rayTracerBase field is empty", "RayTracerBase", "RayTracerBase");

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = constructRay(nX, nY, j, i);
                    Color pixelColor = rayTracerBase.traceRay(ray);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }

        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
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