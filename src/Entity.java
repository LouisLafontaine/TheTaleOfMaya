import util.Image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected Vector2D pos;
    protected Rectangle boundary;
    protected final BufferedImage image;

    public Entity(double x, double y,String file) {
        pos = new Vector2D(x,y);
        image = Image.getFrom(file);
        boundary = new Rectangle(image.getWidth(),image.getHeight());
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.scale(0.75, 0.75);
        g2d.drawImage(image, (int)(pos.x-image.getWidth()/2.0), (int)(pos.y-image.getHeight()/2.0), null);
        showBoundary(g2d);
        g2d.transform(old);
    }

    public void showBoundary(Graphics2D g2d) {
        g2d.drawRect((int)(pos.x-image.getWidth()/2),(int)(pos.y-image.getHeight()/2), boundary.width, boundary.height);
    }
}