package game;

import util.ImageUtil;
import util.Vect;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected Vect pos;
    protected Vect screenPos;
    protected Rectangle bounds;
    protected BufferedImage image;

    public Entity(double x, double y, String imagePath) {
        pos = new Vect(x,y);
        image = ImageUtil.getFrom(imagePath);
        bounds = new Rectangle(100,100);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int centerX = (int)(screenPos.x - bounds.width/2);
        int centerY = (int)(screenPos.y - bounds.height/2);
        g2d.drawImage(image,centerX ,centerY, bounds.width, bounds.height, null);
    }

    public void showBoundary(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.setColor(Color.green);
        g2d.drawRect((int)(screenPos.x - bounds.width/2.0),(int)(screenPos.y - bounds.height/2.0), bounds.width, bounds.height);
        g2d.setStroke(old);
    }
}