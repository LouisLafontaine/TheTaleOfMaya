import util.Keyboard;
import util.Image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import static java.awt.event.KeyEvent.*;

public class Player {
    private Vector2D pos;
    private final BufferedImage image;
    
    
    
    public Player(double x, double y) {
        pos = new Vector2D(x,y);
        image = Image.getFrom("resources/images/character.png");
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.scale(0.75, 0.75);
        g2d.drawImage(image, (int)(pos.x-image.getWidth()/2.0), (int)(pos.y-image.getHeight()/2.0), null);
        g2d.transform(old);
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
}
