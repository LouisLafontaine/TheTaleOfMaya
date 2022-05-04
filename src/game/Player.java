package game;

import org.w3c.dom.css.Rect;
import util.Animation;
import util.KeyHandler;
import util.Vect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


import static java.awt.event.KeyEvent.*;


public class Player extends Entity {
    
    public static Player instance;
    private boolean init = false;
    private BufferedImage attackingImage;
    private final HashMap<String, Animation> directionImage = new HashMap<>();
    public int state = 0;
    public int lastMovement = 1;
    public Rectangle rangeBounds;
    public boolean isAttacking = false;
    public boolean isCollidingWithNPC = false;
    public boolean canCloseBox = false;
    public boolean readyToTalk = false;
    public boolean hasTalked = false;



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
            rangeBounds = new Rectangle(TileManager.get().getTileSize(), TileManager.get().getTileSize());
            
            image = image.getSubimage(0,0,120,130);

            Animation attack = new Animation("resources/images/playerImages/frontattack.png",32,64,4,1,12);
    
            Animation up = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 7, 12);
            Animation down = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 5, 12);
            Animation right = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 8, 12);
            Animation left = new Animation("resources/images/playerImages/zelda.png", 120, 130, 10, 6, 12);

            directionImage.put("attack",attack);

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
        if(!isAttacking) {
            g2d.drawImage(image, (int) c.getCenter().x, (int) c.getCenter().y, tileSize, tileSize, null);
        }
        else if(isAttacking){
            g2d.drawImage(image, (int) c.getCenter().x, (int) c.getCenter().y, tileSize, 2*tileSize, null);
        }
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
        boolean enter = KeyHandler.isPressed(VK_ENTER);

        boolean attack = KeyHandler.isPressed(VK_SPACE);
        
        if(up) {
            lastMovement = 0;
            pos.add(0, -10);
            image = directionImage.get("up").getCurrentFrame();
            readyToTalk = false;
            isAttacking = false;
        } else if(down) {
            lastMovement = 1;
            pos.add(0,10);
            image = directionImage.get("down").getCurrentFrame();
            readyToTalk = false;
            isAttacking = false;
        } else if(right) {
            lastMovement = 2;
            pos.add(10,0);
            image = directionImage.get("right").getCurrentFrame();
            readyToTalk = false;
            isAttacking = false;
        } else if(left) {
            lastMovement = 3;
            pos.add(-10,0);
            image = directionImage.get("left").getCurrentFrame();
            readyToTalk = false;
            isAttacking = false;
        } else if(enter){
            interact();
        }
        else if(attack){
            image = directionImage.get("attack").getCurrentFrame();
            isAttacking = true;
        }
        if(isCollidingWithNPC){
            readyToTalk = true;
        }
    }

//    public void attack(Monster e) {
//        if (isInRange(e)){
//            e.health -= 1;
//        }
//    }

    public void updateDuringCollision(){
        boolean enter = KeyHandler.isPressed(VK_ENTER);

        if(!KeyHandler.isPressed(VK_ENTER)){
            canCloseBox = true;
        }

        if(enter){
            interact();
        }
    }

    public void interact(){

        if (state == 0 && readyToTalk){
            state = 1; // switching to talking state
        }

        else if (state == 1 && canCloseBox) {
            state = 0; // switching back to playing state
            hasTalked = true;
            canCloseBox = false;
            readyToTalk = false;
        }
    }
    public Rectangle getRange(){
        int tileSize = TileManager.get().getTileSize();
        switch (lastMovement) {
            case 0 : {
                rangeBounds.x = ((int) pos.x);
                rangeBounds.y = ((int) pos.y) - tileSize;
                break;
            }
            case 1 : {
                rangeBounds.x = ((int) pos.x);
                rangeBounds.y = ((int) pos.y) + tileSize;
                break;
            }
            case 2 : {
                rangeBounds.x = ((int) pos.x) + tileSize;
                rangeBounds.y = (int) pos.y;
                break;
            }
            case 3 :{
                rangeBounds.x = ((int) pos.x) - tileSize;
                rangeBounds.y = (int) pos.y;
                break;
            }
        }
        return rangeBounds;
    }

    public void showRange(Graphics g, Camera c, Color co,Entity e) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        Rectangle r = getRange();
        int screenX = (int) (r.x - c.getPos().x + c.getCenter().x);
        int screenY = (int) (r.y - c.getPos().y + c.getCenter().y);
        g2d.setStroke(new java.awt.BasicStroke(3));
        if(!isInRange(e)) {
            g2d.setColor(co);
        } else if (isInRange(e)) {
            g2d.setColor(Color.red);
        }
        g2d.drawRect(screenX , screenY, r.width, r.height);
        g2d.setStroke(old);
    }
    public boolean isInRange(Entity e){
        return(getRange().intersects(e.getBounds()));
    }
}
