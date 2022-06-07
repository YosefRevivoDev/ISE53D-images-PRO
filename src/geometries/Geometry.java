package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;


public abstract class Geometry extends Intersect {

    protected Color emission = Color.BLACK;
    private Material material=new Material();

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
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * setter for material according to builder design pattern
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter for material
     * @return
     */
    public Material getMaterial() {
        return material;
    }
}
