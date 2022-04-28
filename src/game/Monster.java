package game;

import java.awt.*;
import util.Vect;

public class Monster extends boid.Boid{
    public int health;

    public Monster(double x, double y, String imagePath) {
        super(new Vect(x,y), new Vect(x,y), new Vect(x,y), imagePath);
        this.health = 3;
    }

    public void drawHP(Graphics g){
        if(health < 0){
            System.out.println("test");
        }
        String s = Integer.toString(health);
        double x = this.pos.x;
        double y = this.pos.y - 1;
        g.drawString(s, (int) x, (int) y);
    }
}
