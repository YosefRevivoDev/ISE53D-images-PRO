package scene;

import geometries.Geometries;
import lighting.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;


public class Scene {

    private  String name;
    private  Color background = Color.BLACK;
    private  AmbientLight ambientLight = new AmbientLight();
    private  Geometries geometries = new Geometries();
    private  List<LightSource> lights = new LinkedList<>();


    /**
     * CTOR
     * @param name The name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }


    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights =  builder.lights;
    }
    /**----- GETTERS------- */
    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public List<LightSource> getLights() {
        return lights;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    public static class SceneBuilder {
        private final String name;
        public List<LightSource> lights = new LinkedList<>();
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public SceneBuilder(String name) {
            this.name = name;
        }

        //chaining methods
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build() {
            Scene scene = new Scene(this);
            return scene;
        }
    }
}
