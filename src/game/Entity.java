package game;

import util.Img;
import util.Vect;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected Vect position;
    protected Rectangle bounds;
    protected BufferedImage image;

    public Entity(double x, double y, String imageFilename) {
        position = new Vect(x,y);
        image = Img.load(imageFilename);
        bounds = new Rectangle(100,100);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int centerX = (int)(position.x- bounds.width/2.0);
        int centerY = (int)(position.y- bounds.height/2.0);
        g2d.drawImage(image,centerX ,centerY, bounds.width, bounds.height, null);
    }

    public void showBoundary(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.setColor(Color.green);
        g2d.drawRect((int)(position.x- bounds.width/2.0),(int)(position.y- bounds.height/2.0), bounds.width, bounds.height);
        g2d.setStroke(old);
    }
}