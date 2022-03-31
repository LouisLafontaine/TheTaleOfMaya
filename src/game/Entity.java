package game;

import util.ImageUtil;
import util.MainWindow;
import util.Vect;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Entity {
    protected Vect pos;
    protected Vect screenPos;
    protected Rectangle boundsArea;
    protected int boundsX1;
    protected int boundsY1;
    protected int boundsX2;
    protected int boundsY2;
    protected BufferedImage image;
    protected boolean collisionOn = false;

    public Entity(double x, double y, String imagePath) {
        pos = new Vect(x, y);
        image = ImageUtil.getFrom(imagePath);
        boundsArea= new Rectangle(100, 100);





    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int centerX = (int) (screenPos.x - boundsArea.width / 2);
        int centerY = (int) (screenPos.y - boundsArea.height / 2);
        g2d.drawImage(image, centerX, centerY, boundsArea.width, boundsArea.height, null);
    }

    public void showBoundary(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.setColor(Color.green);
        g2d.drawRect(boundsX1, boundsY1, boundsX2 - boundsX1, boundsY2 - boundsY1);
        g2d.setStroke(old);
    }
}
