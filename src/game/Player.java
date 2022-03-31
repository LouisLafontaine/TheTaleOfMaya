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
    public Vect screenPos;
    
    private Player(double x, double y) {
        super(x, y, "resources/images/playerImages/zelda.png");
        image = image.getSubimage(0,0,120,130);
        
        

        Dimension screenSize = MainWindow.getScreenDimension();
        int tileSize = TileManager.get().getTileSize();
        screenPos = new Vect(screenSize.width/2.0 - tileSize/2.0, screenSize.getHeight()/2.0 - tileSize/2.0);

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
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int tileSize = TileManager.get().getTileSize();
        g2d.drawImage(image, (int) screenPos.x, (int) screenPos.y, tileSize, tileSize, null);
    }
    
    @Override
    public Rectangle getBounds() {
        Rectangle r = super.getBounds();
        int tileSize = TileManager.get().getTileSize();
        r.x = (int) (pos.x + 0.2 * tileSize);
        r.y = (int) (pos.y + 0.4 * tileSize);
        r.width = (int) (0.6 * tileSize);
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
