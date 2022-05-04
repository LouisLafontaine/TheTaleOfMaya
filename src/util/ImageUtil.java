package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtil {
    
    public static BufferedImage getFrom(String imagePath) {
        BufferedImage img = null;
        if(imagePath != null) {
            try {
                File file = new File(imagePath);
                img = ImageIO.read(file);
            } catch(Exception e) {
                System.err.println(e.getMessage() + " couldn't load image at "+ imagePath);
            }
        }
        return img;
    }
}