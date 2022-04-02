package game;

import util.Vect;

public class Camera {
    private Vect center = new Vect();
    private Vect pos = new Vect();
    
    public Camera() {
        center = new Vect();
        pos = new Vect();
    }
    
    public Camera(Vect center) {
        this.center.copy(center);
        this.pos = new Vect();
    }
    
    public Camera(Vect center, Vect pos) {
        this.center = center;
        this.pos = pos;
    }
    
    public void follow(Entity e) {
        this.pos.copy(e.pos);
    }
    
    public void move(Vect v) {
        pos.add(v);
    }
    
    public void move(double x, double y) {
        pos.add(x, y);
    }
    
    public Vect getCenter() {
        return new Vect(center);
    }
    
    public Vect getPos() {
        return new Vect(pos);
    }
}
