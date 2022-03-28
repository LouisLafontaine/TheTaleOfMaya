package game;

import util.KeHandler;

import java.awt.*;
import java.util.HashMap;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    private final HashMap<String, Animation> directionImage = new HashMap<>();
    Animation up;
    Animation down;
    Animation right;
    Animation left;
    
    public Player(double x, double y) {
        super(x, y, "resources/images/playerImages/zelda.png");
        image = image.getSubimage(0,0,120,130);
        
        up = new Animation("resources/images/playerImages/zelda.png",120,130,10,7,10);
        down = new Animation("resources/images/playerImages/zelda.png",120,130,10,5,10);
        right = new Animation("resources/images/playerImages/zelda.png",120,130,10,8,10);
        left = new Animation("resources/images/playerImages/zelda.png",120,130,10,6,10);
        
        directionImage.put("up", up);
        directionImage.put("down", down);
        directionImage.put("right", right);
        directionImage.put("left", left);
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
        
        if(up) {
            position.add(0, -10);
            image = directionImage.get("up").getCurrentFrame();
        } else if(down) {
            position.add(0,10);
            image = directionImage.get("down").getCurrentFrame();
        } else if(right) {
            position.add(10,0);
            image = directionImage.get("right").getCurrentFrame();
        } else if(left) {
            position.add(-10,0);
            image = directionImage.get("left").getCurrentFrame();
        }
    }
}
