/**
 * This class describes a boid on a 2D plane
 */

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class Boid {
    Vector2D position;                                      // the boid's position
    Vector2D velocity;                                      // the boid's velocity vector
    Vector2D acceleration;                                  // the boid's acceleration vector
    
    static double maxVelocity;                              // maximum velocity of all boids
    static double maxAcceleration;                          // maximum acceleration of all boids
    static double perceptionRadius;                         // perception radius of all boids
    static double cohesionStrength;                         // strength of the cohesion rule of all boids
    static double alignmentStrength;                        // strength of the alignment rule of all boids
    static double separationStrength;                       // strength of the separation rule of all boids
    
    static boolean drawPerceptionRadius = false;            // true if the perception radius of all boids is drawn
    
    private static final double MAX_VELOCITY = 4;                   // default maximum velocity of all boids
    private static final double MAX_ACCELERATION = 0.2;             // default maximum acceleration of all boids
    private static final double PERCEPTION_RADIUS = 20;             // default perception radius of all boids
    private static final double COHESION_STRENGTH = 1;              // default strength of the cohesion rule of all boids
    private static final double ALIGNMENT_STRENGTH = 1;             // default strength of the alignment rule of all boids
    private static final double SEPARATION_STRENGTH = 1;           // default strength of the separation rule of all boids
    private static final boolean DRAW_PERCEPTION_RADIUS = false;    // perception radius, true if drawn
    
    Color color;                                            // the boid's color
    
    /**
     * Creates a boid with a random color
     * @param position  the boid's position
     * @param velocity  the boid's velocity
     * @param acceleration  the boids's acceleration
     */
    public Boid(Vector2D position, Vector2D velocity, Vector2D acceleration) {
        this.position = new Vector2D(position);
        this.velocity = new Vector2D(velocity);
        this.acceleration = new Vector2D(acceleration);
        color = new Color((int)(Math.random()*100 +155),(int)(Math.random()*100 +155),(int)(Math.random()*100 +155));
    }
    
    protected static void setDefaultParameters() {
        maxVelocity = MAX_VELOCITY;
        maxAcceleration = MAX_ACCELERATION;
        perceptionRadius = PERCEPTION_RADIUS;
        cohesionStrength = COHESION_STRENGTH;
        alignmentStrength = ALIGNMENT_STRENGTH;
        separationStrength = SEPARATION_STRENGTH;
        drawPerceptionRadius = DRAW_PERCEPTION_RADIUS;
    }
    
    /**
     * Creates a boid with random position, velocity, acceleration and color, within a defined area
     * @param x  width of the area for the random position
     * @param y  height of the area for the random position
     * @return a Boid
     */
    public static Boid random(int x, int y){
        Vector2D randomPosition = new Vector2D(Math.random() * x,Math.random() * y);
        Vector2D randomVelocity = Vector2D.random(Boid.maxVelocity);
        Vector2D randomAcceleration = Vector2D.random(Boid.maxAcceleration);
        return new Boid(randomPosition,randomVelocity, randomAcceleration);
    }
    
    /**
     * Draws the boid on the screen and its perception radius if perceptionRadius is true
     * @param g  a Graphics object to draw on the screen
     */
    protected void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform initial = g2d.getTransform();
    
        double headingAngle = Math.atan2(velocity.y, velocity.x);  // direction in which the boid is currently heading
        
        g2d.translate((int)(position.x),(int)(position.y));
        g2d.rotate(headingAngle);
        
        // Drawing the boid here
        g2d.setColor(color);
        g2d.fillPolygon(new int[]{-4,4,-4}, new int[]{-3,0,3}, 3);
        
        g2d.setTransform(initial);
        
        if(drawPerceptionRadius) {
            g2d.setColor(Color.green);
            g2d.drawOval(
                    (int)(position.x-perceptionRadius/2),
                    (int)(position.y-perceptionRadius/2),
                    (int)perceptionRadius,
                    (int)perceptionRadius);
        }
    }
    
    /**
     * Updates the position and velocity of the boid
     */
    protected void update() {
        velocity.limit(maxVelocity);
        position.add(velocity);
        velocity.add(acceleration);
    }
    
    /**
     * Flocks the boid by applying 3 rules : cohesion, alignment, separation
     * @param boids  list of all the boids
     */
    protected void flock(LinkedList<Boid> boids) {
        int perceivedNeighbors = 0;
        Vector2D cohesion = new Vector2D();
        Vector2D alignment = new Vector2D();
        Vector2D separation = new Vector2D();
        for(Boid other : boids) {
            double distance = position.dist(other.position);
            if(other != this && distance < perceptionRadius) {
                cohesion.add(other.position);
                alignment.add(other.velocity);
                Vector2D repelForce = Vector2D.sub(position, other.position);
                repelForce.div(distance * distance); // repel force is inversely proportional to the distance
                separation.add(repelForce);
                perceivedNeighbors++;
            }
        }
        if(perceivedNeighbors > 0) {
            cohesion.div(perceivedNeighbors); // to get the average vector
            alignment.div(perceivedNeighbors); // to get the average vector
            
            cohesion.sub(position);
            alignment.sub(velocity);
        }
        
        cohesion.mult(cohesionStrength);
        alignment.mult(alignmentStrength);
        separation.mult(separationStrength*70);
    
        acceleration.set(0,0); // so that acceleration does not accumulate over time
        acceleration.add(cohesion);
        acceleration.add(alignment);
        acceleration.add(separation);
        
        acceleration.limit(maxAcceleration);
    }
    
    /**
     * Keeps the boid on screen when it leaves it by looping its position as if the screen space was a torus
     * However the distance calculations don't take into account the torus space
     * @param width  width of the screen space
     * @param height  height of the screen space
     */
    protected void loopEdges(int width, int height){
        if(!(width == 0 && height == 0)) {  // necessary because at the first timer event JPanel getWidth / getHeight returns 0,0...
            if(position.x < 0) position.x = width + position.x % width;
            else if(position.x > width) position.x %= width;
            if(position.y < 0) position.y = height + position.y % width;
            else if(position.y > height) position.y %= height;
        }
    }
}