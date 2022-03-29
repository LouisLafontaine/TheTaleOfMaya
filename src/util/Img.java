package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Img {
    
    public static BufferedImage load(String imagePath) {
        BufferedImage img = null;
        try {
            File file = new File(imagePath);
            img = ImageIO.read(file);
        } catch(Exception e) {
            System.err.println(e.getMessage() + "\nat " + imagePath + "");
        }
        return img;
    }
}