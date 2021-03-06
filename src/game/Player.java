package game;

import util.Animation;
import util.KeyHandler;
import util.Vect;

import java.awt.*;
import java.util.HashMap;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    
    public static Player instance;
    private boolean init = false;
    private final HashMap<String, Animation> directionImage = new HashMap<>();
    public int state = 0;
    public int lastMovement;


    private Player() {
        super(0, 0, "resources/images/playerImages/zelda.png");
    }
    
    
    public static Player get() {
        if(instance == null) {
            instance = new Player();
        }
        return instance;
    }
    
    public Player init(double x, double y) {
        if(!init) {
            init = true;
            
            pos = new Vect(x  * TileManager.get().getTileSize(), y  * TileManager.get().getTileSize());
            
            image = image.getSubimage(0,0,120,130);
    
            Animation up = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 7, 12);
            Animation down = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 5, 12);
            Animation right = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 8, 12);
            Animation left = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 6, 12);
    
            directionImage.put("up", up);
            directionImage.put("down", down);
            directionImage.put("right", right);
            directionImage.put("left", left);
        } else {
            System.err.println("The Player instance has already been initialized !");
        }
        return Player.get();
    }
    
    @Override
    public void draw(Graphics g, Camera c) {
        Graphics2D g2d = (Graphics2D) g;
        int tileSize = TileManager.get().getTileSize();
        g2d.drawImage(image, (int) c.getCenter().x, (int) c.getCenter().y, tileSize, tileSize, null);
    }
    
    public void dispose() {
        instance = null;
    }
    
    @Override
    public Rectangle getBounds() {
        Rectangle r = super.getBounds();
        int tileSize = TileManager.get().getTileSize();
        r.x = (int) (pos.x + 0.15 * tileSize);
        r.y = (int) (pos.y + 0.4 * tileSize);
        r.width = (int) (0.7 * tileSize);
        r.height = (int) (0.6 * tileSize);
        return r;
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
            lastMovement = 0;
            pos.add(0, -10);
            image = directionImage.get("up").getCurrentFrame();
        } else if(down) {
            lastMovement = 1;
            pos.add(0,10);
            image = directionImage.get("down").getCurrentFrame();
        } else if(right) {
            lastMovement = 2;
            pos.add(10,0);
            image = directionImage.get("right").getCurrentFrame();
        } else if(left) {
            lastMovement = 3;
            pos.add(-10,0);
            image = directionImage.get("left").getCurrentFrame();
        }
    }
}
