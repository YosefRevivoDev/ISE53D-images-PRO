/**
 *
 */
package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {

        Scene scene = new Scene.SceneBuilder("Test scene").build();

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.getGeometries().add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.getLights().add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2),0.15) //
                        .setkL(0.0004).setkQ(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(.1))).build();

        scene.getGeometries().add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setKt(0.5)),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1.0)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.getLights().add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4),0.15) //
                .setkL(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
        scene.getGeometries().add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.3)));

        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1),0.15) //
                .setkL(4E-5).setkQ(2E-7).setRadius(4).setBeamsNum(100));


        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void anotherPictureTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1),0.15) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(300, 400, 400), new Point(-10, -10, 0), new Vector(-1, 2, -1),0.15) //
                .setkL(4E-5).setkQ(2E-3));

        scene.getGeometries().add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(30d, new Point(40, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.6)),
                new Triangle(new Point(-50, -50, -15), new Point(50, -50, -35), new Point(25, 25, -50)
                        .add(new Vector(30,-10,5))).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.3)),
                new Tube(10d, new Ray(new Point(-60,-50,50), new Vector(0,1,-8))).setEmission(new Color(GREEN)) //
                         .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(30).setKt(0.9999999999999999)),
                new Cylinder(10d,new Ray(new Point(-50,100,50),new Vector(0,-1,-3)),0.1).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(10).setkD(0.9999999999999)));

        ImageWriter imageWriter = new ImageWriter("another test", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void anotherPictureTest2() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 0, 0), new Vector(0, 0, -1),0.15) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(300, 400, 400), new Point(10, 10, 0), new Vector(-1, 2, -1),0.15) //
                .setkL(4E-5).setkQ(2E-3));

        scene.getGeometries().add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(30d, new Point(40, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.6)),
                new Triangle(new Point(-50, -50, -15), new Point(50, -50, -35), new Point(25, 25, -50)
                        .add(new Vector(30,-10,5))).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.3)),
                new Tube(10d, new Ray(new Point(-60,-50,50), new Vector(0,1,-8))).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(30).setKt(0.9999999999999999)),
                new Cylinder(10d,new Ray(new Point(-50,100,50),new Vector(0,-1,-3)),0.1).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(10).setkD(0.9999999999999)));

        ImageWriter imageWriter = new ImageWriter("another test 2", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void anotherPictureTest3() {
        Camera camera = new Camera(new Point(0, 0, 1200), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1),0.15) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(300, 400, 400), new Point(-10, -10, 0), new Vector(-1, 2, -1),0.15) //
                .setkL(4E-5).setkQ(2E-3));

        scene.getGeometries().add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(30d, new Point(40, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.6)),
                new Triangle(new Point(-50, -50, -15), new Point(50, -50, -35), new Point(25, 25, -50)
                        .add(new Vector(30,-10,5))).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.3)),
                new Tube(10d, new Ray(new Point(-60,-50,50), new Vector(0,1,-8))).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(30).setKt(0.9999999999999999)),
                new Cylinder(10d,new Ray(new Point(-50,100,50),new Vector(0,-1,-3)),0.1).setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(10).setkD(0.9999999999999)),
                new Plane(new Point(-50, 60, 0), new Vector(5, 10, 0)).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(10).setKt(0.999999999999)));
        ImageWriter imageWriter = new ImageWriter("another test 3", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light with a plane below
     * producing a soft shading
     */
    @Test
    public void SphereSoftShading() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(400, 400).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getGeometries().add( //
                new Tube(50d,new Ray(new Point(-60,-50,50), new Vector(0,1,-8)))
                        .setEmission(new Color(DARK_GRAY))
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(60).setKt(0.9999999999999999)),
                new Sphere(20d, new Point(-30, -10, 240)) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.6)));


        scene.getLights().add(new SpotLight(new Color(400, 300, 400), new Point(-25, 25, -25)
                , new Vector(0, 0, -1),0.15) //
                .setkL(4E-5).setkQ(2E-6).setRadius(4).setBeamsNum(150));

        ImageWriter imageWriter = new ImageWriter("SoftShading", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracerBase(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void SphereSoftShading2() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(400, 400).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getGeometries().add( //
                new Tube(50d,new Ray(new Point(-60,-50,50), new Vector(0,1,-8)))
                        .setEmission(new Color(DARK_GRAY))
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(60).setKt(0.9999999999999999)),
                new Sphere(20d, new Point(-30, -10, 240)) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.6)));


        scene.getLights().add(new SpotLight(new Color(400, 300, 400), new Point(-25, 25, -25)
                , new Vector(1, -1, 1),0.15) //
                .setkL(4E-5).setkQ(2E-6).setRadius(3).setBeamsNum(150));

        ImageWriter imageWriter = new ImageWriter("SoftShading2", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracerBase(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
