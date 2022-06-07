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
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
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

        scene.getLights().add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
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

        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
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
    public void trianglesTransparentSphere2() {
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

        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        //scene.getLights().add(new DirectionalLight(new Color(BLUE), new Vector(0,0,-1)));


        ImageWriter imageWriter = new ImageWriter("refractionShadow2", 600, 600);
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

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(300, 400, 400), new Point(-10, -10, 0), new Vector(-1, 2, -1)) //
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

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 0, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(300, 400, 400), new Point(10, 10, 0), new Vector(-1, 2, -1)) //
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

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(300, 400, 400), new Point(-10, -10, 0), new Vector(-1, 2, -1)) //
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
                new Plane(new Point(10, 10, 0), new Vector(1, 1, 0)).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(10).setKt(0.999999999999)),
                new Tube(50d,new Ray(new Point(-30,-50,50), new Vector(0,1,-8)))
                        .setEmission(new Color(DARK_GRAY))
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(60).setKt(0.9999999999999999)),
                new Sphere(25d, new Point(10, 50, -50)).setEmission(new Color(CYAN)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.3)));

        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(10, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7).setRadius(4).setBeamsNum(100));
        scene.getLights().add(new DirectionalLight(new Color(BLUE), new Vector(0,0,-1)));

        ImageWriter imageWriter = new ImageWriter("SoftShading", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracerBase(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void SphereSoftShading2() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getGeometries().add( //
                new Tube(50d,new Ray(new Point(-30,-50,50), new Vector(0,1,-8)))
                        .setEmission(new Color(DARK_GRAY))
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(60)),
                new Sphere(20d, new Point(-10, 20, 30)).setEmission(new Color(CYAN)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setKt(0.3)));

        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(-10, 20, 80), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("SoftShading2", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracerBase(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
//    @Test
//    public void miniProject01() {
//
//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setVPSize(600, 600).setVPDistance(1000);
//
//        Scene scene = new Scene.SceneBuilder("Test scene").
//                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
//
//        scene.getGeometries().add(
//                new Polygon(new Point(0,31,0),  new Point(0,61,0),
//                new Point(15,61,0),  new Point(15,31,0))
//                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
//                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),
//
//                new Polygon(new Point(0,0,0),  new Point(-1,0,0),
//                new Point(-1,61,0),  new Point(0,61,0))
//                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
//
//                new Polygon(new Point(-1,31,0),  new Point(-1,61,0),
//                new Point(-16,61,0),  new Point(-16,31,0))
//                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
//                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),
//                new Polygon(new Point(-16,0,0),  new Point(-17,0,0),
//                new Point(-17,61,0),  new Point(-16,61,0))
//                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//        /////
//                new Polygon(new Point(15,31,0),  new Point(15,29,0),
//                new Point(-16,29,0),  new Point(-16,31,0))
//                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-32,0,0),  new Point(-33,0,0),
//                new Point(-33,61,0),  new Point(-32,61,0))
//                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(0,30,0),  new Point(0,0,0),
//                new Point(15,0,0),  new Point(15,30,0))
//                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
//                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),
//                new Polygon(new Point(-1,30,0),  new Point(-1,0,0),
//                new Point(-16,0,0),  new Point(-16,30,0))
//                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
//                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),
//
//                new Polygon(new Point(-17,61,0),  new Point(-17,0,0),
//                new Point(-32,0,0),  new Point(-32,61,0))
//                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setKr(1.0)),
//                new Polygon(new Point(-33,61,0),  new Point(-33,0,0),
//                new Point(-48,0,0),  new Point(-48,61,0))
//                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setKr(1.0)),                new Plane(new Point(0,0,0),new Vector(0,1,0))
//                .setEmission(new Color(java.awt.Color.GRAY))
//                .setMaterial(new Material().setkD(0.4).setkS(0.05).setnShininess(100)),
//                new Plane(new Point(0,80,0),new Vector(0,1,0))
//                .setEmission(new Color(java.awt.Color.WHITE).scale(0.3))
//                .setMaterial(new Material().setkD(0.7)),
//                new Plane(new Point(35,0,0),new Vector(1,0,0))
//                .setEmission(new Color(GRAY))
//                .setMaterial(new Material().setkD(0.5)),
//                new Plane(new Point(-100,0,0),new Vector(1,0,0))
//                .setEmission(new Color(GRAY))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Plane(new Point(0,0,200),new Vector(0,0,1))
//                .setEmission(new Color(java.awt.Color.GRAY))
//                .setMaterial(new Material().setkD(0.5)),
//                new Plane(new Point(0,0,-20),new Vector(0,0,1))
//                .setEmission(new Color(java.awt.Color.GRAY))
//                .setMaterial(new Material().setkD(0.5)),
//
//                new Sphere(1, new Point(2,33,1))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Sphere(1, new Point(2,28,1))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Sphere(1, new Point(-14,33,1))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Sphere(1, new Point(-14,28,1))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//
//
//        //foot1
//                new Polygon(new Point(-40,0,60),new Point(-40,0,62),
//                new Point(-40,20,62),new Point(-40,20,60))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-40,0,62),new Point(-42,0,62),
//                new Point(-42,20,62),new Point(-40,20,62))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-42,0,60),new Point(-42,0,62),
//                new Point(-42,20,62),new Point(-42,20,60))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-40,0,60),new Point(-42,0,60),
//                new Point(-42,20,60),new Point(-40,20,60))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//
//        //foot2
//
//                new Polygon(new Point(-40,0,80),new Point(-40,0,82),
//                new Point(-40,20,82),new Point(-40,20,80))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-40,0,82),new Point(-42,0,82),
//                new Point(-42,20,82),new Point(-40,20,82))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-42,0,80),new Point(-42,0,82),
//                new Point(-42,20,82),new Point(-42,20,80))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-40,0,80),new Point(-42,0,80),
//                new Point(-42,20,80),new Point(-40,20,80))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//        //foot3
//                new Polygon(new Point(-70,0,60),new Point(-70,0,62),
//                new Point(-70,20,62),new Point(-70,20,60))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-70,0,62),new Point(-72,0,62),
//                new Point(-72,20,62),new Point(-70,20,62))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-72,0,60),new Point(-72,0,62),
//                new Point(-72,20,62),new Point(-72,20,60))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-70,0,60),new Point(-72,0,60),
//                new Point(-72,20,60),new Point(-70,20,60))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//        //foot4
//                new Polygon(new Point(-70,0,80),new Point(-70,0,82),
//                new Point(-70,20,82),new Point(-70,20,80))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-70,0,82),new Point(-72,0,82),
//                new Point(-72,20,82),new Point(-70,20,82))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-72,0,80),new Point(-72,0,82),
//                new Point(-72,20,82),new Point(-72,20,80))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-70,0,80),new Point(-72,0,80),
//                new Point(-72,20,80),new Point(-70,20,80))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//
//                new Polygon(new Point(-37,20,58),new Point(-37,20,84),
//                new Point(-75,20,84),new Point(-75,20,58))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-37,21,58),new Point(-37,21,84),
//                new Point(-75,21,84),new Point(-75,21,58))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-37,20,58),new Point(-37,21,58),
//                new Point(-75,21,58),new Point(-75,20,58))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-37,20,58),new Point(-37,21,58),
//                new Point(-37,21,84),new Point(-37,20,84))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-37,20,84),new Point(-37,21,84),
//                new Point(-75,21,84),new Point(-75,20,84))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//                new Polygon(new Point(-75,20,58),new Point(-75,21,58),
//                new Point(-75,21,84),new Point(-75,20,84))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
//                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//
//        //decor outer
//                new Triangle((new Point(-56,25,71)),new Point(-53,21,69),
//                new Point(-53,21,73))
//                .setEmission(new Color(YELLOW).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
//                new Triangle((new Point(-56,25,71)),new Point(-53,21,73),
//                new Point(-59,21,73))
//                .setEmission(new Color(YELLOW).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
//                new Triangle((new Point(-56,25,71)),new Point(-59,21,73),
//                new Point(-59,21,69))
//                .setEmission(new Color(YELLOW).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
//                new Triangle((new Point(-56,25,71)),new Point(-59,21,69),
//                new Point(-53,21,69))
//                .setEmission(new Color(YELLOW).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
//        //decor inner
//                 new Triangle((new Point(-56,30,71)),new Point(-50,21,66),
//                new Point(-50,21,76))
//                .setEmission(new Color(YELLOW).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
//                new Triangle((new Point(-56,30,71)),new Point(-50,21,76),
//                new Point(-62,21,76))
//                .setEmission(new Color(YELLOW).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
//                new Triangle((new Point(-56,30,71)),new Point(-62,21,76),
//                new Point(-62,21,66))
//                .setEmission(new Color(GRAY).scale(0.1))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
//                 new Triangle((new Point(-56,30,71)),new Point(-62,21,66),
//                new Point(-50,21,66))
//                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
//
////wallSpheres
//                new Sphere(4, new Point(35, 30, 50))
//                .setEmission(new Color(java.awt.Color.RED).scale(0.5))
//                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100)),
//                new Sphere(4, new Point(35, 30, 75))
//                .setEmission(new Color(java.awt.Color.ORANGE).scale(0.5))
//                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100)),
//                new Sphere(4, new Point(35, 30, 100))
//                .setEmission(new Color(java.awt.Color.GREEN).scale(0.3))
//                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100)));
//
//
//
//        scene.getLights().add(new PointLight(new Color(java.awt.Color.YELLOW), new Point(0, 50, 40)));
//        scene.getLights().add(new SpotLight(new Color(java.awt.Color.orange), new Point(-56, 50, 71), new Vector(0,-1,0), 3));
//        scene.getLights().add(new SpotLight(new Color(java.awt.Color.CYAN).scale(0.8), new Point(10, 55, 75), new Vector(1.5,-1,0), 3));
//
//        ImageWriter imageWriter = new ImageWriter("testpro", 600, 600);
//        camera.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene)) //
//                .renderImage() //
//                .writeToImage();
//    }

    @Test
    public void polygonTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getGeometries().add( //
                new Polygon(new Point(0,31,0),  new Point(0,61,0),
                        new Point(15,61,0),  new Point(15,31,0))
                        .setEmission(new Color(YELLOW).scale(0.7))
                        .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)));

        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7).setRadius(4).setBeamsNum(100));


        ImageWriter imageWriter = new ImageWriter("polygonT", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void BonusImage() {

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);


        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.setAmbientLight(new AmbientLight(new Color(BLACK), new Double3(0.3)));

        scene.getGeometries().add(
                new Plane(new Point(-150, -150, -100), new Point(-100, 100, -100), new Point(75, 75, -100))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(20)).setEmission(new Color(blue)),
                new Sphere(20, new Point(30, 35, 5)) //
                        .setEmission(new Color(lightGray)) //
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(50).setKt(0.0001)),
                //the middle part of the snow man
                new Sphere(30, new Point(30, -10, 5)) //
                        .setEmission(new Color(lightGray)) //
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(50).setKt(0.00001)),
                //the bottom part of the snow man
                new Sphere(40, new Point(30, -70, 5)) //
                        .setEmission(new Color(lightGray)) //
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(50).setKt(0.00001)),
                new Sphere(4, new Point(35, 36, 85)) //
                        .setEmission(new Color(0,100,0)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(50).setKt(0.1)),
                new Sphere(4, new Point(21, 36, 85)) //
                        .setEmission(new Color(0,100,0)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(50).setKt(0.1)),
                //the snow mans nose
                new Triangle(new Point(25.5, 30, 70), new Point(28.5, 20, 70), new Point(31.5, 30, 70))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setKt(0.3)).setEmission(new Color(255,69,0)),
                //the snow mans buttons
                new Sphere(4, new Point(28, -22, 78)) //
                        .setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(50).setKt(0.1)),
                new Sphere(4, new Point(28, -7, 78)) //
                        .setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(50).setKt(0.1)),
                new Sphere(4, new Point(28, 8, 78)) //
                        .setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(50).setKt(0.1)),
                new Sphere(40, new Point(-70, 80, 60)) //
                        .setEmission(new Color(YELLOW)) //
                        .setMaterial(new Material().setkD(0.8).setkS(0.8).setnShininess(50).setKt(0.65))
        );

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(-95, 90, 0), new Vector(1, 0, -1)) //
                .setkL(0.0001).setkQ(0.0005).setBeamsNum(0));

        scene.getLights().add(new DirectionalLight(new Color(YELLOW), new Vector(1, 0, 5)));
//        scene.getLights().add(new PointLight(new Color(YELLOW), new Point(-90, 80, 5)) //
//                .setkL(0.000000000001).setkQ(0.0000000000005).setBeamsNum(0));

        ImageWriter imageWriter = new ImageWriter("Bonus image", 600, 600);

        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene))
                .renderImage()//
                .writeToImage(); //
    }
    @Test
    public void trianglesTransparentSphere3() {
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


        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7).setRadius(4).setBeamsNum(100));


        ImageWriter imageWriter = new ImageWriter("refractionShadow3", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void finalImage() {
        Camera camera = new Camera(new Point(0, 50, 160), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(110);

        Scene scene = new Scene.SceneBuilder("Test scene").
                setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        scene.getGeometries().add( //
                new Triangle((new Point(-56,25,71)),new Point(-53,21,69), new Point(-53,21,73))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                new Triangle((new Point(-56,25,71)),new Point(-53,21,73), new Point(-59,21,73))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                new Triangle((new Point(-56,25,71)),new Point(-59,21,73), new Point(-59,21,69))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                new Triangle((new Point(-56,25,71)),new Point(-59,21,69), new Point(-53,21,69))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),

                new Plane(new Point(0,0,0),new Vector(0,1,0))
                        .setMaterial(new Material().setkD(0.4).setkS(0.05).setnShininess(100)),
                new Plane(new Point(0,80,0),new Vector(0,1,0))
                        .setMaterial(new Material().setkD(0.7)),
                new Plane(new Point(35,0,0),new Vector(1,0,0))
                        .setMaterial(new Material().setkD(0.5)),
                new Plane(new Point(-100,0,0),new Vector(1,0,0))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Plane(new Point(0,0,200),new Vector(0,0,1))
                        .setMaterial(new Material().setkD(0.5)),
                new Plane(new Point(0,0,-20),new Vector(0,0,1))
                        .setMaterial(new Material().setkD(0.5)),

                new Polygon(new Point(-40,0,60),new Point(-40,0,62), new Point(-40,20,62),new Point(-40,20,60))
                      .setEmission(new Color(java.awt.Color.RED).scale(0.1)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-40,0,62),new Point(-42,0,62), new Point(-42,20,62),new Point(-40,20,62))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-42,0,60),new Point(-42,0,62), new Point(-42,20,62),new Point(-42,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-40,0,60),new Point(-42,0,60), new Point(-42,20,60),new Point(-40,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),


                new Polygon(new Point(-40,0,80),new Point(-40,0,82), new Point(-40,20,82),new Point(-40,20,80))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-40,0,82),new Point(-42,0,82), new Point(-42,20,82),new Point(-40,20,82))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-42,0,80),new Point(-42,0,82), new Point(-42,20,82),new Point(-42,20,80))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-40,0,80),new Point(-42,0,80), new Point(-42,20,80),new Point(-40,20,80))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                //foot3
                new Polygon(new Point(-70,0,60),new Point(-70,0,62), new Point(-70,20,62),new Point(-70,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-70,0,62),new Point(-72,0,62), new Point(-72,20,62),new Point(-70,20,62))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-72,0,60),new Point(-72,0,62), new Point(-72,20,62),new Point(-72,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-70,0,60),new Point(-72,0,60), new Point(-72,20,60),new Point(-70,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                //foot4
                new Polygon(new Point(-70,0,80),new Point(-70,0,82), new Point(-70,20,82),new Point(-70,20,80))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-70,0,82),new Point(-72,0,82), new Point(-72,20,82),new Point(-70,20,82))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-72,0,80),new Point(-72,0,82), new Point(-72,20,82),new Point(-72,20,80))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-70,0,80),new Point(-72,0,80), new Point(-72,20,80),new Point(-70,20,80))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),

                new Polygon(new Point(-37,20,58),new Point(-37,20,84), new Point(-75,20,84),new Point(-75,20,58))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-37,21,58),new Point(-37,21,84), new Point(-75,21,84),new Point(-75,21,58))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-37,20,58),new Point(-37,21,58), new Point(-75,21,58),new Point(-75,20,58))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-37,20,58),new Point(-37,21,58), new Point(-37,21,84),new Point(-37,20,84))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-37,20,84),new Point(-37,21,84), new Point(-75,21,84),new Point(-75,20,84))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-75,20,58),new Point(-75,21,58), new Point(-75,21,84),new Point(-75,20,84))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),

                new Triangle((new Point(-56,25,71)),new Point(-53,21,69), new Point(-53,21,73))
                        .setEmission(new Color(YELLOW).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                new Triangle((new Point(-56,25,71)),new Point(-53,21,73), new Point(-59,21,73))
                        .setEmission(new Color(YELLOW).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                new Triangle((new Point(-56,25,71)),new Point(-59,21,73), new Point(-59,21,69))
                        .setEmission(new Color(YELLOW).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                new Triangle((new Point(-56,25,71)),new Point(-59,21,69), new Point(-53,21,69))
                        .setEmission(new Color(YELLOW).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100)),
                //decor inner
                new Triangle((new Point(-56,30,71)),new Point(-50,21,66), new Point(-50,21,76))
                        .setEmission(new Color(YELLOW).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
                new Triangle((new Point(-56,30,71)),new Point(-50,21,76), new Point(-62,21,76))
                        .setEmission(new Color(YELLOW).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
                new Triangle((new Point(-56,30,71)),new Point(-62,21,76), new Point(-62,21,66))
                        .setEmission(new Color(GRAY).scale(0.1))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),
                new Triangle((new Point(-56,30,71)),new Point(-62,21,66), new Point(-50,21,66))
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)));


        scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(-56, 50, 71), new Vector(0, -1,0)) //
                .setkL(4E-5).setkQ(2E-7).setRadius(4).setBeamsNum(100));


        ImageWriter imageWriter = new ImageWriter("final", 600, 700);
        camera.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
