import util.Keyboard;
import util.Image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import static java.awt.event.KeyEvent.*;

public class Player extends Entity{

    public Player(double x,double y,String file){
        super.Entity(x,y,file);
    }
    

    
    public void update() {
        movement();
    }
    
    public void movement() {
        if(Keyboard.isPressed(VK_UP) || Keyboard.isPressed(VK_W)) {
            pos.add(0,-10);
        }
        if(Keyboard.isPressed(VK_DOWN) || Keyboard.isPressed(VK_S)) {
            pos.add(0,10);
        }
        if(Keyboard.isPressed(VK_RIGHT) || Keyboard.isPressed(VK_D)) {
            pos.add(10,0);
        }
        if(Keyboard.isPressed(VK_LEFT) || Keyboard.isPressed(VK_A)){
            pos.add(-10,0);
        }
    }

    public boolean isColliding() {

    }
}
