public class Point {
    int x;
    int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public double distanceTo(Point p) {
        return Math.sqrt(Math.pow(x-p.x, 2) + Math.pow(y-p.y, 2));
    }
    
    public void moveTo(Point p) {
        x = p.x;
        y = p.y;
    }
    
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void translate(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }
}