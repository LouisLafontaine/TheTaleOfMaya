/**
 * This class is the panel in which all the boids handled and drawn on the screen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class BoidPanel extends JPanel implements ActionListener {
    
    public static BoidPanel instance;
    
    private LinkedList<Boid> boids;                         // list of all the boids
    private Timer timer;                                    // timer of the game loop
    private static final int DEFAULT_NUMBER_OF_BOIDS = 400; // initial numbers of boids to create
    private long previousTime = 0;                          // system time at previous frame in ms
    private int frameCounter = 0;                           // counts the number of frames elapsed, reset when > fps
    private final int FRAME_AVERAGE = 10;                   // number of frames to average the fps on
    private int avgTimeSum = 0;                             // stores the sum of the elapsed times between frames to calculate the average fps
    private int fps;                                        // the current average fps (averaged on FRAME_AVERAGE)
    private boolean init = false;                           // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a GamePanel
     */
    private BoidPanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the GamePanel class can be created
     *
     * @return the instance of the GamePanel class
     */
    public static BoidPanel get() {
        if(instance == null) {
            instance = new BoidPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the GamePanel class
     */
    public void init() {
        if(!init) {
            Boid.setDefaultParameters();
            boids = new LinkedList<>();
    
            // Making numberOfBoids boids with random speeds and accelerations between -1 and 1
            for (int i = 0; i < DEFAULT_NUMBER_OF_BOIDS; i++) {
                boids.add(Boid.random(getWidth(), getHeight()));
            }
    
            // Game loop timer
            // the desired number of fps to run the simulation
            int desiredFps = 60;
            timer = new Timer(1000/ desiredFps, this);
            timer.start();
    
            repaint();
    
            init = true;
        } else {
            System.err.println("The BoidPanel instance has already been initialized !");
        }
    }
    
    /**
     * Resets the instance and sets init to false
     */
    protected void reset() {
        if(init) {
            instance = null;
            init = false;
            timer.stop();
        }
    }
    
    /**
     * Draws the boids on the screen
     * @param g  a Graphics object to draw on the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.setColor(Color.black);
        g.fillRect(0, 0,getWidth(), getHeight());

        // Boids
        for(Boid b : boids) {
            b.draw(g);
        }
    
        // system time at current frame in ms
        long currentTime = System.currentTimeMillis();
        // elapsed time between the two last frame in ms
        long elapsedTime = currentTime - previousTime;
        previousTime = currentTime;
        avgTimeSum += elapsedTime;
        frameCounter++;
        if(frameCounter >= FRAME_AVERAGE) {
            avgTimeSum /= frameCounter;
            fps = 1000 / avgTimeSum;
            frameCounter = 0;
        }
    }
    
    /**
     * Invoked when an action occurs.
     * Main use is running the game loop at each timer event
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for(Boid b : boids) {
            b.flock(boids);
            b.update();
            b.loopEdges(getWidth(),getHeight());
        }
        repaint();
    }
    
    /**
     * Adds n boids to the flock
     * @param n  number of boids to add to the flock
     */
    protected void addBoids(int n) {
        for(int i = 0; i < n; i++) {
            boids.add(Boid.random(getWidth(), getHeight()));
        }
    }
    
    /**
     * Removes n boids from the flock
     * @param n  number of boids to remove from the flock
     */
    protected void removeBoids(int n) {
        if(boids.size() >= n) {
            for(int i = 0; i < n; i++) {
                boids.removeLast();
            }
        }
    }
    
    public int getFps() {
        return fps;
    }
    
    public int getDefaultNumberOfBoids() {
        return DEFAULT_NUMBER_OF_BOIDS;
    }
    
    public int getNumberOfBoids() {
        return boids.size();
    }
}