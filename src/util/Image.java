package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {
    
    public static BufferedImage getFrom(String filename) {
        BufferedImage img = null;
        try {
            File file = new File(filename);
            img = ImageIO.read(file);
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }
        return img;
    }
}