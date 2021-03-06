package game;

import util.ImageUtil;
import util.Vect;
import util.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Entity {
    
    protected Vect pos;
    private final Rectangle bounds;
    protected BufferedImage image;

    public Entity(double x, double y, String imagePath) { //TODO overload constructor make one without imagePath
        pos = new Vect(x  * TileManager.get().getTileSize(), y  * TileManager.get().getTileSize());
        image = ImageUtil.getFrom(imagePath);
        bounds = new Rectangle(TileManager.get().getTileSize(), TileManager.get().getTileSize());
    }

    public void draw(Graphics g, Camera c) { //TODO don't draw entity if out of screen
        Graphics2D g2d = (Graphics2D) g;
        int tileSize = TileManager.get().getTileSize();
        int screenX = (int) (pos.x - c.getPos().x + c.getCenter().x);
        int screenY = (int) (pos.y - c.getPos().y + c.getCenter().y);
        g2d.drawImage(image, screenX, screenY, tileSize, tileSize, null);
    }

    public void showBoundary(Graphics g, Camera c, Color co) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        Rectangle r = getBounds();
        int screenX = (int) (r.x - c.getPos().x + c.getCenter().x);
        int screenY = (int) (r.y - c.getPos().y + c.getCenter().y);
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.setColor(co);
        g2d.drawRect(screenX , screenY, r.width, r.height);
        g2d.setStroke(old);
    }
    
    public boolean isColliding(Entity e) {
        return(getBounds().intersects(e.getBounds()));
    }
    
    public boolean isColliding(Rectangle r) {
        return getBounds().intersects(r);
    }
    
    
    public void solveCollision(Rectangle r) {
        Rectangle i = getBounds().intersection(r);
        if(i.width < i.height) {
            if(pos.x < r.x) {
                pos.x -= i.width;
            } else {
                pos.x += i.width;
            }
        } else {
            if(pos.y < r.y) {
                pos.y -= i.height;
            } else {
                pos.y += i.height;
            }
        }
    }
    
    public void solveCollision(Entity e) {
        solveCollision(e.getBounds());
    }
    
    public Rectangle getBounds() {
        bounds.x = (int) pos.x;
        bounds.y = (int) pos.y;
        return bounds;
    }
}
