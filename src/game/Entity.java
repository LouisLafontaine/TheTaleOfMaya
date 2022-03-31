package game;

import util.ImageUtil;
import util.Vect;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Entity {
    protected Vect pos;
    private final Rectangle bounds;
    protected BufferedImage image;

    public Entity(double x, double y, String imagePath) { //TODO overload constructor make one without imagePath
        pos = new Vect(x  * TileManager.get().getTileSize(), y  * TileManager.get().getTileSize());
        image = ImageUtil.getFrom(imagePath);
        bounds = new Rectangle(100, 100);
    }

    public void draw(Graphics g) { //TODO don't draw entity if out of screen
        Graphics2D g2d = (Graphics2D) g;
        int tileSize = TileManager.get().getTileSize();
        int screenX = (int) (pos.x - Player.get().pos.x + Player.get().screenPos.x);
        int screenY = (int) (pos.y - Player.get().pos.y + Player.get().screenPos.y);
        g2d.drawImage(image, screenX, screenY, tileSize, tileSize, null);
    }

    public void showBoundary(Graphics g, Color c) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        Rectangle r = getBounds();
        int screenX = (int) (r.x - Player.get().pos.x + Player.get().screenPos.x);
        int screenY = (int) (r.y - Player.get().pos.y + Player.get().screenPos.y);
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.setColor(c);
        g2d.drawRect(screenX , screenY, r.width, r.height);
        g2d.setStroke(old);
        g2d.setColor(Color.blue);
    }
    
    public boolean isColliding(Entity e) {
        return(getBounds().intersects(e.getBounds()));
    }
    
    /**
     * // comment : Accurate only if the character is within the map
     *
     * @return
     */
    public boolean isCollidingMap() {
        int tileSize = TileManager.get().getTileSize();
        int col = (int)(pos.x/tileSize);
        int row = (int)(pos.y/tileSize);
        boolean topLeft = TileManager.get().getCollidable(row, col);
        boolean topRight = TileManager.get().getCollidable(row, col + 1);
        boolean downLeft = TileManager.get().getCollidable(row + 1, col);
        boolean downRight = TileManager.get().getCollidable(row + 1, col + 1);
        return topLeft || topRight || downLeft || downRight;
    }
    
    public void solveCollision(Rectangle other) {
        Rectangle r = getBounds();
        if(r.y + r.height > other.y && r.y < other.y && r.x + r.width > other.x && r.x < other.x + other.width) {
            pos.y = other.y - r.height;
        } else if(r.y < other.y + other.height && r.y + r.height > other.y + other.height && r.x + r.width > other.x && r.x < other.x + other.width) {
            pos.y = other.y + other.height;
        } else if(r.x + r.width > other.x && r.x < other.x) {
            pos.x = other.x + r.width;
        } else {
            pos.x = other.x + other.width;
        }
    }
    
    public void solveCollision(int tileRow, int tileColumn) {
        int tileSize = TileManager.get().getTileSize();
        int x = tileSize * tileColumn;
        int y = tileSize * tileRow;
        solveCollision(new Rectangle(x,y,tileSize,tileSize));
    }
    
    public Rectangle getBounds() {
        bounds.x = (int) pos.x;
        bounds.y = (int) pos.y;
        return bounds;
    }
}
