import java.awt.*;

public class Boid {
    Point pos;
    Vector2D velocity;
    Vector2D acceleration;
    
    public Boid(int x, int y, Vector2D velocity, Vector2D acceleration) {
        this.pos = new Point(x,y);
        this.velocity = new Vector2D(velocity);
        this.acceleration = new Vector2D(acceleration);
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.white);
        int width = 10;
        int height = 10;
        g.fillOval(pos.x-width/2,pos.y-height/2,width,height);
    }
    
    public void update() {
        pos.translate(velocity);
        velocity.add(acceleration);
    }
}
