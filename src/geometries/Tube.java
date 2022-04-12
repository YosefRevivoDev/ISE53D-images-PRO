package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

public class Tube extends Geometry {

    protected double radius;
    protected Ray axisRay;

    /**
     * Tube Constructor receiving radius and axis Ray
     *
     * @param radius radius
     * @param axisRay axisRay
     */
    public Tube(double radius, Ray axisRay) {
        this.axisRay = axisRay;
    }

    public double getRadius() {
        return radius;
    }
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }

    /**
     * func that calculate the length between
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {

        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        Vector P0_To_P = point.subtract(P0);

        Double t = alignZero(P0_To_P.dotProduct(v));// alignZero means =  if the result is close to zero so result will be zero
        // בדיקה אם הנקודה הנתונה והנקודת ההתחלה נמצאות באותו מקום - כלומר מאונכות אחת לשניה
        if (isZero(t)){ // if we recive 0 so we return tje Normal
             return P0_To_P.normalize();
        }

        Point O = P0.add(v.scale(t));// במידה ולא אז נוסיף לו את הוקטור V ונכפיל אותו בסקלר T פעמים

        Vector O_To_P = point.subtract(O); // מחזיר את הוקטור בין הנקודה 0 לנקודה P

        return O_To_P.normalize();
    }
}

