package game;

import util.ImageUtil;
import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage image;
    boolean collision;
    
    public Tile(String imagePath, boolean collision) {
        this.image = ImageUtil.getFrom(imagePath);
        this.collision = collision;
    }
    
    public Tile(BufferedImage bufferedImage, boolean collision) {
        this.image = bufferedImage;
        this.collision = collision;
    }
    
    public Tile() {
        this.image = null;
        this. collision = false;
    }
}
