package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    public void writePixel() {
        ImageWriter imageWriter = new ImageWriter("Base image", 800, 500);


        for(int i = 0; i < imageWriter.getNx(); i++)
            for(int j = 0; j < imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j, new Color(200, 50, 70));

        final int interval = 50;
        for(int i = interval; i < imageWriter.getNx() - 1; i += interval)
            for(int j = 0; j <imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j,  Color.BLACK);

        for(int j = interval; j < imageWriter.getNy() - 1; j += interval)
            for(int i = 0; i <imageWriter.getNx(); i++)
                imageWriter.writePixel(i, j, Color.BLACK);

        imageWriter.writeToImage();
    }
}