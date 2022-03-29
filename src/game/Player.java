package game;

import util.KeHandler;
import util.Vect;

import java.awt.*;
import java.util.HashMap;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    
    static Player instance;
    private final HashMap<String, Animation> directionImage = new HashMap<>();
    Animation up;
    Animation down;
    Animation right;
    Animation left;
    
    private Player(double x, double y) {
        super(x, y, "resources/images/playerImages/zelda.png");
        screenPos = new Vect(GameWindow.get().getWidth()/2.0, GameWindow.get().getHeight()/2.0);
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
    
    public static Player get(double x, double y) {
        if(instance == null) {
            instance = new Player(x,y);
        }
        return instance;
    }
    
    public static Player get() {
        if(instance == null) {
            instance = new Player(0,0);
            System.err.println("Player wasn't initialized");
        }
        return instance;
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
            pos.add(0, -10);
            image = directionImage.get("up").getCurrentFrame();
        } else if(down) {
            pos.add(0,10);
            image = directionImage.get("down").getCurrentFrame();
        } else if(right) {
            pos.add(10,0);
            image = directionImage.get("right").getCurrentFrame();
        } else if(left) {
            pos.add(-10,0);
            image = directionImage.get("left").getCurrentFrame();
        }
    }
}
