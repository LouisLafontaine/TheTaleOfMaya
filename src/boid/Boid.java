/**
 * This class describes a boid on a 2D plane
 */

package boid;

import game.Camera;
import util.Vect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.LinkedList;

public class Boid extends game.Entity {
    Vect position;                                      // the boid's pos
    Vect velocity;                                      // the boid's velocity vector
    Vect acceleration;                                  // the boid's acceleration vector
    
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
    
    public Boid() {
        super(0, 0, null);
        this.position = new Vect();
        this.velocity = new Vect();
        this.acceleration = new Vect();
        color = Color.black;
    }
    
    /**
     * Creates a boid with a random color
     * @param position  the boid's pos
     * @param velocity  the boid's velocity
     * @param acceleration  the boids's acceleration
     */
    public Boid(Vect position, Vect velocity, Vect acceleration, String imagePath) {
        super(position.x, position.y, imagePath);
        this.position = new Vect(position);
        this.velocity = new Vect(velocity);
        this.acceleration = new Vect(acceleration);
        color = new Color((int)(Math.random()*100 +155),(int)(Math.random()*100 +155),(int)(Math.random()*100 +155));
    }
    
    
    public static void setDefaultParameters() {
        maxVelocity = MAX_VELOCITY;
        maxAcceleration = MAX_ACCELERATION;
        perceptionRadius = PERCEPTION_RADIUS;
        cohesionStrength = COHESION_STRENGTH;
        alignmentStrength = ALIGNMENT_STRENGTH;
        separationStrength = SEPARATION_STRENGTH;
        drawPerceptionRadius = DRAW_PERCEPTION_RADIUS;
    }
    
    /**
     * Creates a boid with random pos, velocity, acceleration and color, within a defined area
     * @param x  width of the area for the random pos
     * @param y  height of the area for the random pos
     * @return a Boid.Boid
     */
    public static Boid random(int x, int y){
        Vect randomPosition = new Vect(Math.random() * x,Math.random() * y);
        Vect randomVelocity = Vect.random(Boid.maxVelocity);
        Vect randomAcceleration = Vect.random(Boid.maxAcceleration);
        return new Boid(randomPosition,randomVelocity,randomAcceleration,"resources/images/slime.png");
    }
    
    public static Boid random(Rectangle spawnArea){
        Vect randomPosition = new Vect((Math.random() * spawnArea.width) + spawnArea.x,(Math.random() * spawnArea.height) + spawnArea.y);
        Vect randomVelocity = Vect.random(Boid.maxVelocity);
        Vect randomAcceleration = Vect.random(Boid.maxAcceleration);
        return new Boid(randomPosition,randomVelocity,randomAcceleration,"resources/images/slime.png");
    }
    
    /**
     * Draws the boid on the screen and its perception radius if perceptionRadius is true
     * @param g  a Graphics object to draw on the screen
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform initial = g2d.getTransform();
    
        double headingAngle = Math.atan2(velocity.y, velocity.x);  // direction in which the boid is currently heading
        
        g2d.translate(position.x,position.y);
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
    
    public void draw(Graphics g, Camera camera) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform initial = g2d.getTransform();
    
        double headingAngle = Math.atan2(velocity.y, velocity.x);  // direction in which the boid is currently heading
    
        int screenX = (int) (position.x - camera.getPos().x + camera.getCenter().x);
        int screenY = (int) (position.y - camera.getPos().y + camera.getCenter().y);
    
        g2d.translate(screenX, screenY);
        g2d.rotate(headingAngle);
    
        // Drawing the boid here
        g2d.drawImage(image, - image.getWidth()/2, - image.getHeight()/2, null);
    
        g2d.setTransform(initial);
    
        if(true) {
            g2d.setColor(Color.green);
            g2d.drawOval(
                    (int)(screenX-perceptionRadius/2),
                    (int)(screenY-perceptionRadius/2),
                    (int)perceptionRadius,
                    (int)perceptionRadius);
        }
    }
    
    /**
     * Updates the pos and velocity of the boid
     */
    public void update() {
        velocity.limit(maxVelocity);
        position.add(velocity);
        velocity.add(acceleration);
    }
    
    /**
     * Flocks the boid by applying 3 rules : cohesion, alignment, separation
     * @param boids  list of all the boids
     */
    public void flock(LinkedList<Boid> boids) {
        int perceivedNeighbors = 0;
        Vect cohesion = new Vect();
        Vect alignment = new Vect();
        Vect separation = new Vect();
        for(Boid other : boids) {
            double distance = position.dist(other.position);
            if(other != this && distance < perceptionRadius) {
                cohesion.add(other.position);
                alignment.add(other.velocity);
                Vect repelForce = Vect.sub(position, other.position);
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
     * Keeps the boid on screen when it leaves it by looping its pos as if the screen space was a torus
     * However the distance calculations don't take into account the torus space
     * @param width  width of the screen space
     * @param height  height of the screen space
     */
    public void loopEdges(int width, int height){
        if(!(width == 0 && height == 0)) {  // necessary because at the first timer event JPanel getWidth / getHeight returns 0,0...
            if(position.x < 0) position.x = width + position.x % width;
            else if(position.x > width) position.x %= width;
            if(position.y < 0) position.y = height + position.y % width;
            else if(position.y > height) position.y %= height;
        }
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}