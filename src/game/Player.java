package game;

import util.KeHandler;
import util.Image;

import java.awt.*;
import java.awt.image.BufferedImage;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    private final BufferedImage[] directionImage = new BufferedImage[8];
    
    public Player(double x, double y, String imageFilename) {
        super(x, y, imageFilename);
        //directionImage counterclockwise
        //TODO change Image[] directionImage to a hashmap
        directionImage[0] = Image.getFrom("resources/images/playerImages/top.jpg");
        directionImage[1] = Image.getFrom("resources/images/playerImages/left.jpg");
        directionImage[2] = Image.getFrom("resources/images/playerImages/bottom.png");
        directionImage[3] = Image.getFrom("resources/images/playerImages/right.png");
        directionImage[4] = Image.getFrom("resources/images/playerImages/top_left.png");
        directionImage[5] = Image.getFrom("resources/images/playerImages/bottom_left.png");
        directionImage[6] = Image.getFrom("resources/images/playerImages/bottom_right.png");
        directionImage[7] = Image.getFrom("resources/images/playerImages/top_right.jpg");
    }

    public void draw(Graphics g) {
        super.draw(g);
    }
    
    public void update() {
        movement();
    }
    
    public void movement() {
        boolean up = KeHandler.isPressed(VK_UP);
        boolean down = KeHandler.isPressed(VK_DOWN);
        boolean right = KeHandler.isPressed(VK_RIGHT);
        boolean left = KeHandler.isPressed(VK_LEFT);
        
        if(up && !(down || right || left)) {
            position.add(0, -10);
            image = directionImage[0];
        } else if(down && !(up || right || left)) {
            position.add(0,10);
            image = directionImage[2];
        } else if(right && !(up || down || left)) {
            position.add(10,0);
            image = directionImage[3];
        } else if(left && !(up || down || right)) {
            position.add(-10,0);
            image = directionImage[1];
        } else if((up && right) && !(down || left)) {
            position.add(7,-7);
            image = directionImage[7];
        } else if((up && left) && !(down || right)) {
            position.add(-7,-7);
            image = directionImage[4];
        } else if((down && right) && !(up || left )) {
            position.add(7,7);
            image = directionImage[6];
        } else if((down && left) && !(up || right)) {
            position.add(-7,7);
            image = directionImage[5];
        }
    }
}
