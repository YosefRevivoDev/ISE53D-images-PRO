package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Double3;

public class Scene {

    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight;
    public Geometries geometries = new Geometries();

    /**
     * CTOR
     * @param name The name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Builder pattern Setter
     * @param background the background to set
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Builder pattern Setter
     * @param ambientLight the ambientLight to set
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Builder pattern Setter
     * @param geometries the geometries to set
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


}
