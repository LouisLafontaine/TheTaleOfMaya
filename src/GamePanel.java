/**
 * This class is the panel in which all the boids handled and drawn on the screen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    
    LinkedList<Boid> boids;         // list of all the boids
    Timer timer;                    // timer of the game loop
    int initialNumberOfBoids = 400; // initial numbers of boids to create
    int desiredFps = 60;            // the desired number of fps to run the simulation
    long previousTime = 0;          // system time at previous frame in ms
    long currentTime = 0;           // system time at current frame in ms
    long elapsedTime = 0;           // elapsed time between the two last frame in ms
    int frameCounter = 0;           // counts the number of frames elapsed, reset when > fpsAvg
    int frameAverage = 10;          // number of frames to average the fps on
    int avgTimeSum = 0;             // stores the sum of the elapsed times between frames to calculate the average fps
    int fpsAvg;                     // the current average fps
    boolean init = false;                   // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a GamePanel
     */
    private GamePanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the GamePanel class can be created
     *
     * @return the instance of the GamePanel class
     */
    public static GamePanel get() {
        if(instance == null) {
            instance = new GamePanel();
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
            for (int i = 0; i < initialNumberOfBoids; i++) {
                boids.add(Boid.random(getWidth(), getHeight()));
            }
    
            // Game loop timer
            timer = new Timer(1000/desiredFps, this);
            timer.start();
    
            repaint();
    
            init = true;
        }
    }
    
    /**
     * Resets the instance and sets init to false
     */
    protected void reset() {
        instance = null;
        init = false;
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

        currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - previousTime;
        previousTime = currentTime;
        avgTimeSum += elapsedTime;
        frameCounter++;
        if(frameCounter >= frameAverage) {
            avgTimeSum /= frameCounter;
            fpsAvg = 1000 / avgTimeSum;
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
    public void addBoids(int n) {
        for(int i = 0; i < n; i++) {
            boids.add(Boid.random(getWidth(), getHeight()));
        }
    }
    
    /**
     * Removes n boids from the flock
     * @param n  number of boids to remove from the flock
     */
    public void removeBoids(int n) {
        if(boids.size() >= n) {
            for(int i = 0; i < n; i++) {
                boids.removeLast();
            }
        }
    }
}