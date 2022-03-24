import util.Image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Entity {
    protected Vector2D pos;
    protected final BufferedImage image;
    protected Rectangle hitbox;

    public Entity(double x, double y,String file) {
        pos = new Vector2D(x,y);
        image = Image.getFrom(file);
        hitbox = new Rectangle(image.getWidth(),image.getHeight());
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.scale(0.75, 0.75);
        g2d.drawImage(image, (int)(pos.x-image.getWidth()/2.0), (int)(pos.y-image.getHeight()/2.0), null);
        showBoundaries(g);
        g2d.transform(old);
    }

    public void showBoundaries(Graphics g) {
        g.drawRect((int)(pos.x-image.getWidth()/2),(int)(pos.y-image.getHeight()/2),hitbox.width,hitbox.height);
    }
}
