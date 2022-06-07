package lighting;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene.SceneBuilder("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

    private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(200, 200) //
            .setVPDistance(1000);
    //תוספת
    private Camera camera3 = new Camera(new Point(0, 50, 160),
            new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(200, 200) //
            .setVPDistance(110);

//    private Point[] p = { // The Triangles' vertices:
//            new Point(-110, -110, -150), // the shared left-bottom
//            new Point(80, 100, -150), // the shared right-top
//            new Point(110, -110, -150), // the right-bottom
//            new Point(-75, 85, 0) }; // the left-top
//    private Point trPL = new Point(50, 30, -100); // Triangles test Position of Light
//    private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
//    private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
//    private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
//    private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
//    private Material material = new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300);
//    private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
//    private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
//    private Geometry sphere = new Sphere(50d, new Point(0, 0, -50)) //
//            .setEmission(new Color(BLUE).reduce(2)) //
//            .setMaterial(new Material().setkD(new Double3(0.5)).setkS(new Double3(0.5)).setnShininess(300));


    //הוספה

    private static Geometry triangle10 = new Triangle( //
            new Point(-150, -150, -150), new Point(150, -150, -150), new Point(75, 75, -150));
    private static Geometry triangle20 = new Triangle( //
            new Point(-150, -150, -150), new Point(-70, 70, -50), new Point(75, 75, -150));
    private static Geometry sphere0 = new Sphere(50, new Point(0, 0, -50))
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

    private static Geometry sphere1 = new Sphere(100, new Point(0, 0, -100));
    private static Geometry sphere2 = new Sphere(50, new Point(100, 0, -100));
    private static Geometry sphere3 = new Sphere(50, new Point(-100, 0, -100));


    private static Geometry planeA = new Plane(new Point(500,0,0),
            new Vector(1,0,0));
    private static Geometry planeB = new Plane(new Point(-500,0,0),
            new Vector(1,0,0));
    private static Geometry planeC = new Plane(new Point(0,0,-20),
            new Vector(0,0,1));

//    /**
//     * Produce a picture of a sphere lighted by a directional light
//     */
//    @Test
//    public void sphereDirectional() {
//        scene1.getGeometries().add(sphere);
//        scene1.getLights().add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene1)) //
//                .renderImage(); //
//                camera1.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a point light
//     */
//    @Test
//    public void spherePoint() {
//        scene1.getGeometries().add(sphere);
//        scene1.getLights().add(new PointLight(spCL, spPL).setkL(0.001).setkQ(0.0002));
//
//        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene1)) //
//                .renderImage(); //
//                camera1.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a spot light
//     */
//    @Test
//    public void sphereSpot() {
//        scene1.getGeometries().add(sphere);
//        scene1.getLights().add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setkL(0.001).setkQ(0.0001));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene1)) //
//                .renderImage(); //
//                camera1.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a two triangles lighted by a directional light
//     */
//    @Test
//    public void trianglesDirectional() {
//        scene2.getGeometries().add(triangle1, triangle2);
//        scene2.getLights().add(new DirectionalLight(trCL, trDL));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene2)) //
//                .renderImage(); //
//                camera2.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a two triangles lighted by a point light
//     */
//    @Test
//    public void trianglesPoint() {
//        scene2.getGeometries().add(triangle1, triangle2);
//        scene2.getLights().add(new PointLight(trCL, trPL).setkL(0.001).setkQ(0.0002));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene2)) //
//                .renderImage(); //
//                camera2.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a two triangles lighted by a spot light
//     */
//    @Test
//    public void trianglesSpot() {
//        scene2.getGeometries().add(triangle1, triangle2);
//        scene2.getLights().add(new SpotLight(trCL, trPL, trDL).setkL(0.001).setkQ(0.0001));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene2)) //
//                .renderImage(); //
//                camera2.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a two triangles lighted by all lights
//     */
//    @Test
//    public void trianglesSpotSharp() {
//        scene2.getGeometries().add(triangle1, triangle2);
//        scene2.getLights().add(new DirectionalLight(new Color(80, 100, 50), new Vector(-1, -1, -1)));
//        scene2.getLights().add(new PointLight(new Color(250, 250, 50), new Point(10, -75, -100)).setkL(0.0005).setkQ(0.00005));
//        scene2.getLights().add(new SpotLight(new Color(500, 0, 250), new Point(0, -10, -130), new Vector(-10, -5, -1))
//                .setkL(0.00055).setkQ(0.000005));
//
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
//        camera2.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene2))
//                .renderImage();//
//                camera2.writeToImage();
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a narrow spot light
//     */
//    @Test
//    public void sphereSpot2() {
//        scene1.getGeometries().add(sphere);
//        scene1.getLights()
//                .add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setkL(0.001).setkQ(0.00004));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene1)) //
//                .renderImage(); //
//                camera1.writeToImage(); //
//    }
//
//    /**
//     * Produce a picture of a sphere lighted by a narrow spot light
//     */
//    @Test
//    public void sphereTwoSpotLight() {
//        scene1.getGeometries().add(sphere);
//        scene1.getLights()
//                .add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setkL(0.001).setkQ(0.00004));
//
//        scene1.getLights()
//                .add(new SpotLight(new Color(800, 300, 200),  new Point(50, 30, 25), new Vector(-1, -1, -1)).setkL(0.001).setkQ(0.00004));
//
//        ImageWriter imageWriter = new ImageWriter("lightSphereTwoSpotSharp", 500, 500);
//        camera1.setImageWriter(imageWriter) //
//                .setRayTracerBase(new RayTracerBasic(scene1)) //
//                .renderImage();//
//                camera1.writeToImage(); //
//    }

    @Test
    public void miniProject01() {
        scene2.getGeometries().add(
                new Polygon(new Point(0,31,0),  new Point(0,61,0), new Point(15,61,0),  new Point(15,31,0))
                        .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                        .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),

                new Polygon(new Point(0,0,0),  new Point(-1,0,0), new Point(-1,61,0),  new Point(0,61,0))
                        .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),

                new Polygon(new Point(-1,31,0),  new Point(-1,61,0), new Point(-16,61,0),  new Point(-16,31,0))
                        .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                        .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),
                new Polygon(new Point(-16,0,0),  new Point(-17,0,0), new Point(-17,61,0),  new Point(-16,61,0))
                        .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                /////
                new Polygon(new Point(15,31,0),  new Point(15,29,0), new Point(-16,29,0),  new Point(-16,31,0))
                        .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-32,0,0),  new Point(-33,0,0), new Point(-33,61,0),  new Point(-32,61,0))
                        .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(0,30,0),  new Point(0,0,0), new Point(15,0,0),  new Point(15,30,0))
                        .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                        .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),
                new Polygon(new Point(-1,30,0),  new Point(-1,0,0), new Point(-16,0,0),  new Point(-16,30,0))
                        .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                        .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400)),

               new Polygon(new Point(-17,61,0),  new Point(-17,0,0),
                        new Point(-32,0,0),  new Point(-32,61,0))
                        .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setKr(1.0)),
       new Polygon(new Point(-33,61,0),  new Point(-33,0,0),
                new Point(-48,0,0),  new Point(-48,61,0))
                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setKr(1.0)),

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

                new Sphere(1, new Point(2,33,1)).setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Sphere(1, new Point(2,28,1)).setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Sphere(1, new Point(-14,33,1)).setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Sphere(1, new Point(-14,28,1)).setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),


                //foot1
                new Polygon(new Point(-40,0,60),new Point(-40,0,62), new Point(-40,20,62),new Point(-40,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-40,0,62),new Point(-42,0,62), new Point(-42,20,62),new Point(-40,20,62))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-42,0,60),new Point(-42,0,62), new Point(-42,20,62),new Point(-42,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Polygon(new Point(-40,0,60),new Point(-42,0,60), new Point(-42,20,60),new Point(-40,20,60))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),

                //foot2

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

                //decor outer
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
                        .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8)),

//wallSpheres
               new Sphere(4, new Point(35, 30, 50))
                        .setEmission(new Color(java.awt.Color.RED).scale(0.5))
                        .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100)),
                new Sphere(4, new Point(35, 30, 75))
                 .setEmission(new Color(java.awt.Color.ORANGE).scale(0.5))
                 .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100)),
                 new Sphere(4, new Point(35, 30, 100))
                  .setEmission(new Color(java.awt.Color.GREEN).scale(0.3))
                  .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100)));


        scene2.getLights().add(new PointLight(new Color(java.awt.Color.YELLOW)
                .add(new Color(java.awt.Color.YELLOW)).scale(0.2), new Point(0, 50, 40)).setBeamsNum(0));
        scene2.getLights().add(new SpotLight(new Color(java.awt.Color.orange).scale(0.2),
                new Point(-56, 50, 71), new Vector(0,-1,0)).setBeamsNum(0));
        scene2.getLights().add(new SpotLight(new Color(java.awt.Color.CYAN).scale(0.2),
                new Point(10, 55, 75), new Vector(1.5,-1,0)).setBeamsNum(0));


        ImageWriter imageWriter = new ImageWriter("test project", 1000, 1000);
        camera3.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void miniProject2() {
        Geometry door1 = new Polygon(new Point(0,31,0),  new Point(0,61,0),
                new Point(15,61,0),  new Point(15,31,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));

        Geometry middle1 = new Polygon(new Point(0,0,0),  new Point(-1,0,0),
                new Point(-1,61,0),  new Point(0,61,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300));

        Geometry door2 = new Polygon(new Point(-1,31,0),  new Point(-1,61,0),
                new Point(-16,61,0),  new Point(-16,31,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));
        Geometry middle2 = new Polygon(new Point(-16,0,0),  new Point(-17,0,0),
                new Point(-17,61,0),  new Point(-16,61,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        /////
        Geometry middle3 = new Polygon(new Point(15,31,0),  new Point(15,29,0),
                new Point(-16,29,0),  new Point(-16,31,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry middle4 = new Polygon(new Point(-32,0,0),  new Point(-33,0,0),
                new Point(-33,61,0),  new Point(-32,61,0))
                .setEmission(new Color(java.awt.Color.BLACK).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry door3 = new Polygon(new Point(0,30,0),  new Point(0,0,0),
                new Point(15,0,0),  new Point(15,30,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));
        Geometry door4 = new Polygon(new Point(-1,30,0),  new Point(-1,0,0),
                new Point(-16,0,0),  new Point(-16,30,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.7))
                .setMaterial(new Material().setkD(0.6).setkS(0.8).setnShininess(400));

        Geometry door5 = new Polygon(new Point(-17,61,0),  new Point(-17,0,0),
                new Point(-32,0,0),  new Point(-32,61,0))
                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setKr(1.0));
        Geometry door6 = new Polygon(new Point(-33,61,0),  new Point(-33,0,0),
                new Point(-48,0,0),  new Point(-48,61,0))
                .setMaterial(new Material().setkD(0.1).setkS(0.7).setnShininess(300).setKr(1.0));
        Geometry floor= new Plane(new Point(0,0,0),new Vector(0,1,0))
                .setEmission(new Color(java.awt.Color.GRAY))
                .setMaterial(new Material().setkD(0.4).setkS(0.05).setnShininess(100));
        Geometry roof= new Plane(new Point(0,80,0),new Vector(0,1,0))
                .setEmission(new Color(java.awt.Color.WHITE).scale(0.3))
                .setMaterial(new Material().setkD(0.7));
        Geometry wallRight= new Plane(new Point(35,0,0),new Vector(1,0,0))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5));
        Geometry wallLeft= new Plane(new Point(-100,0,0),new Vector(1,0,0))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry wallBehind= new Plane(new Point(0,0,200),new Vector(0,0,1))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5));
        Geometry wallFront= new Plane(new Point(0,0,-20),new Vector(0,0,1))
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                .setMaterial(new Material().setkD(0.5));

        Geometry handle1 = new Sphere(1, new Point(2,33,1))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry handle2 = new Sphere(1, new Point(2,28,1))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry handle3 = new Sphere(1, new Point(-14,33,1))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry handle4 = new Sphere(1, new Point(-14,28,1))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));


        //foot1
        Geometry foot11 = new Polygon(new Point(-40,0,60),new Point(-40,0,62),
                new Point(-40,20,62),new Point(-40,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot12 = new Polygon(new Point(-40,0,62),new Point(-42,0,62),
                new Point(-42,20,62),new Point(-40,20,62))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot13 = new Polygon(new Point(-42,0,60),new Point(-42,0,62),
                new Point(-42,20,62),new Point(-42,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot14 = new Polygon(new Point(-40,0,60),new Point(-42,0,60),
                new Point(-42,20,60),new Point(-40,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        //foot2

        Geometry foot21 = new Polygon(new Point(-40,0,80),new Point(-40,0,82),
                new Point(-40,20,82),new Point(-40,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot22 = new Polygon(new Point(-40,0,82),new Point(-42,0,82),
                new Point(-42,20,82),new Point(-40,20,82))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot23 = new Polygon(new Point(-42,0,80),new Point(-42,0,82),
                new Point(-42,20,82),new Point(-42,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot24 = new Polygon(new Point(-40,0,80),new Point(-42,0,80),
                new Point(-42,20,80),new Point(-40,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        //foot3
        Geometry foot31 = new Polygon(new Point(-70,0,60),new Point(-70,0,62),
                new Point(-70,20,62),new Point(-70,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot32 = new Polygon(new Point(-70,0,62),new Point(-72,0,62),
                new Point(-72,20,62),new Point(-70,20,62))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot33 = new Polygon(new Point(-72,0,60),new Point(-72,0,62),
                new Point(-72,20,62),new Point(-72,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot34 = new Polygon(new Point(-70,0,60),new Point(-72,0,60),
                new Point(-72,20,60),new Point(-70,20,60))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        //foot4
        Geometry foot41 = new Polygon(new Point(-70,0,80),new Point(-70,0,82),
                new Point(-70,20,82),new Point(-70,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot42 = new Polygon(new Point(-70,0,82),new Point(-72,0,82),
                new Point(-72,20,82),new Point(-70,20,82))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot43 = new Polygon(new Point(-72,0,80),new Point(-72,0,82),
                new Point(-72,20,82),new Point(-72,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry foot44 = new Polygon(new Point(-70,0,80),new Point(-72,0,80),
                new Point(-72,20,80),new Point(-70,20,80))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        Geometry plateDown = new Polygon(new Point(-37,20,58),new Point(-37,20,84),
                new Point(-75,20,84),new Point(-75,20,58))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateUp = new Polygon(new Point(-37,21,58),new Point(-37,21,84),
                new Point(-75,21,84),new Point(-75,21,58))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide1 = new Polygon(new Point(-37,20,58),new Point(-37,21,58),
                new Point(-75,21,58),new Point(-75,20,58))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide2 = new Polygon(new Point(-37,20,58),new Point(-37,21,58),
                new Point(-37,21,84),new Point(-37,20,84))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide3 = new Polygon(new Point(-37,20,84),new Point(-37,21,84),
                new Point(-75,21,84),new Point(-75,20,84))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry plateSide4 = new Polygon(new Point(-75,20,58),new Point(-75,21,58),
                new Point(-75,21,84),new Point(-75,20,84))
                .setEmission(new Color(java.awt.Color.RED).scale(0.1))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        //decor outer
        Geometry triangle1 = new Triangle((new Point(-56,25,71)),new Point(-53,21,69),
                new Point(-53,21,73))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        Geometry triangle2 = new Triangle((new Point(-56,25,71)),new Point(-53,21,73),
                new Point(-59,21,73))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        Geometry triangle3 = new Triangle((new Point(-56,25,71)),new Point(-59,21,73),
                new Point(-59,21,69))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        Geometry triangle4 = new Triangle((new Point(-56,25,71)),new Point(-59,21,69),
                new Point(-53,21,69))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100));
        //decor inner
        Geometry triangle5 = new Triangle((new Point(-56,30,71)),new Point(-50,21,66),
                new Point(-50,21,76))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8));
        Geometry triangle6 = new Triangle((new Point(-56,30,71)),new Point(-50,21,76),
                new Point(-62,21,76))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8));
        Geometry triangle7 = new Triangle((new Point(-56,30,71)),new Point(-62,21,76),
                new Point(-62,21,66))
                .setEmission(new Color(java.awt.Color.pink).scale(0.1))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8));
        Geometry triangle8 = new Triangle((new Point(-56,30,71)),new Point(-62,21,66),
                new Point(-50,21,66))
                .setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setKt(0.8));

//wallSpheres
        Geometry sphere1 = new Sphere(4, new Point(35, 30, 50))
                .setEmission(new Color(java.awt.Color.RED).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100));
        Geometry sphere2 = new Sphere(4, new Point(35, 30, 75))
                .setEmission(new Color(java.awt.Color.ORANGE).scale(0.5))
                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100));
        Geometry sphere3 = new Sphere(4, new Point(35, 30, 100))
                .setEmission(new Color(java.awt.Color.GREEN).scale(0.3))
                .setMaterial(new Material().setkD(0.5).setkS(0.9).setnShininess(100));


        scene1.getGeometries().add(door1,middle1,middle2,door2,door3,door4,door5,middle3,floor,wallBehind,handle1,handle2,handle3,handle4,roof
                ,wallRight,wallLeft,foot11,foot12,foot13,foot14,foot21,foot22,foot23,foot24,foot31,foot32,foot33,foot34,foot41,foot42,foot43,foot44,
                plateUp,plateDown,plateSide1,plateSide2,plateSide3,plateSide4,middle4,door6,triangle1,triangle2,triangle3,triangle4,
                triangle5,triangle6,triangle7,triangle8,wallFront,sphere1,sphere2,sphere3);
        scene1.getLights().add(new PointLight(new Color(java.awt.Color.YELLOW)
                .add(new Color(java.awt.Color.YELLOW)).scale(0.2), new Point(0, 50, 40)));
        scene1.getLights().add(new SpotLight(new Color(java.awt.Color.orange).scale(0.2),
                new Point(-56, 50, 71), new Vector(0,-1,0)));
        scene1.getLights().add(new SpotLight(new Color(java.awt.Color.CYAN).scale(0.2),
                new Point(10, 55, 75), new Vector(1.5,-1,0)));


        ImageWriter imageWriter = new ImageWriter("test project2", 1000, 1000);
        camera3.setImageWriter(imageWriter) //
                .setRayTracerBase(new RayTracerBasic(scene1)) //
                .renderImage(); //
                camera3.writeToImage();
    }
}


