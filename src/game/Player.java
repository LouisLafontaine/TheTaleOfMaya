package game;

import util.KeyHandler;
import util.MainWindow;
import util.Vect;

import java.awt.*;
import java.util.HashMap;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    
    public static Player instance;
    private final HashMap<String, Animation> directionImage = new HashMap<>();
    
    private Player(double x, double y) {
        super(x, y, "resources/images/playerImages/zelda.png");
        image = image.getSubimage(0,0,120,130);

        Dimension screenSize = MainWindow.getScreenDimension();
        screenPos = new Vect(screenSize.width/2.0, screenSize.getHeight()/2.0);

        boundsX1 = (int) (screenPos.x - boundsArea.width / 4.0);
        boundsY1 = (int) (screenPos.y);
        boundsX2 = (int) (screenPos.x + boundsArea.width / 4.0);
        boundsY2 = (int) (screenPos.y + boundsArea.width / 2.0);

        Animation up = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 7, 12);
        Animation down = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 5, 12);
        Animation right = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 8, 12);
        Animation left = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 6, 12);
        
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
        boolean up = KeyHandler.isPressed(VK_UP) || KeyHandler.isPressed(VK_W);
        boolean down = KeyHandler.isPressed(VK_DOWN)|| KeyHandler.isPressed(VK_S);
        boolean right = KeyHandler.isPressed(VK_RIGHT) || KeyHandler.isPressed(VK_D);
        boolean left = KeyHandler.isPressed(VK_LEFT) || KeyHandler.isPressed(VK_A);
        
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
