package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtil {
    
    public static BufferedImage getFrom(String imagePath) {
        BufferedImage img = null;
        try {
            File file = new File("resources/" + imagePath);
            img = ImageIO.read(file);
        } catch(Exception e) {
            System.err.println(e.getMessage() + " at resources/" + imagePath);
        }
        return img;
    }
}