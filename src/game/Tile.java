package game;

import util.Img;
import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage image;
    boolean collision;
    
    public Tile(String imagePath, boolean collision) {
        this.image = Img.getFrom(imagePath);
        this.collision = collision;
    }
}
