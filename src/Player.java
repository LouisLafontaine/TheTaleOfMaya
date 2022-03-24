import util.Keyboard;
import util.Image;

import java.awt.*;
import java.awt.image.BufferedImage;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    private final BufferedImage[] directionImage = new BufferedImage[8];
    
    public Player(double x, double y, String imageFilename) {
        super(x, y, imageFilename);
        //directionImage counterclockwise
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
        if(Keyboard.isPressed(VK_UP) || Keyboard.isPressed(VK_W)) {
            position.add(0,-10);
            image = directionImage[0];
        }
        if(Keyboard.isPressed(VK_DOWN) || Keyboard.isPressed(VK_S)) {
            position.add(0,10);
            image = directionImage[2];
        }
        if(Keyboard.isPressed(VK_RIGHT) || Keyboard.isPressed(VK_D)) {
            position.add(10,0);
            image = directionImage[3];
        }
        if(Keyboard.isPressed(VK_LEFT) || Keyboard.isPressed(VK_A)){
            position.add(-10,0);
            image = directionImage[1];
        }
    }
}
