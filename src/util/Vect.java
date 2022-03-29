package util;

/**
 * This class describes either a 2 dimensional euclidean vector or a point in euclidean space
 * inspired by the Vector class of the p5.js JavaScript library
 */
public class Vect {
    public double x; // the x component of the vector or point
    public double y; // the y component of the vector or point
    
    /**
     * Creates a null vector
     */
    public Vect() {
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Creates a vector with specified x and y components
     * @param x  the x component of the vector
     * @param y  the y component of the vector
     */
    public Vect(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Creates a vector based on another vector
     * @param v  other vector to copy
     */
    public Vect(Vect v) {
        this.x = v.x;
        this.y = v.y;
    }
    
    /**
     * @return the x and y components of the vector as a String
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Sets the vector to given x and y
     * @param x  the desired x component
     * @param y  the desired y component
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Adds each component of the vector to each component of the parameter vector
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }
    
    /**
     * Adds each component of the vector to each component of the parameter vector
     */
    public void add(Vect v) {
        x += v.x;
        y += v.y;
    }
    
    /**
     * Subtracts each component of the vector by each component of the parameter vector
     */
    public void sub(Vect v) {
        x -= v.x;
        y -= v.y;
    }
    
    /**
     * Creates a vector that is the subtraction component by component of v1-v2
     * @param v1  a vector to be subtracted
     * @param v2  a vector to subtract
     * @return  v1 - v2
     */
    public static Vect sub(Vect v1, Vect v2){
        Vect r = new Vect();
        r.x = v1.x - v2.x;
        r.y = v1.y - v2.y;
        return r;
    }
    
    /**
     * Multiplies each component of the vector by the scalar parameter m
     */
    public void mult(double m) {
        x *= m;
        y *= m;
    }
    
    public static Vect mult(Vect v, double m) {
        Vect r = new Vect();
        r.x = v.x * m;
        r.y = v.y * m;
        return r;
    }
    
    /**
     * Divides each component of the vector by the scalar parameter d
     */
    public void div(double d) {
        x /= d;
        y /= d;
    }
    
    /**
    * Calculates the euclidean distance between two points (considering a vector as a point)
    */
    public double dist(Vect v) {
        return Math.sqrt(Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2));
    }
    
    /**
     * Calculates the magnitude of the vector
     */
    public double mag() {
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }
    
    /**
     * Sets the magnitude of the vector to the scalar parameter
     */
    public void setMag(double desiredMag) {
        mult(desiredMag / mag());
    }
    
    /**
     * Limits the magnitude of the vector by the scalar parameter
     */
    public void limit(double max) {
        double mag = mag();
        if(mag > max) {
            setMag(max);
        }
    }
    
    /**
     * Returns a new vector with a random magnitude and angle
     * @param range  the range around 0 for the x and y component
     * @return v a vector with random components within the range
     */
    public static Vect random(double range){
        Vect v = new Vect();
        v.x = Math.random()*range-range/2;
        v.y = Math.random()*range-range/2;
        return v;
    }
}