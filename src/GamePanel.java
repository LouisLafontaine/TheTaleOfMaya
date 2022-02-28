/**
 * This class is the panel in which all the boids handled and drawn on the screen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {
    
    LinkedList<Boid> boids;         // list of all the boids
    Timer timer;                    // timer of the game loop
    int numberOfBoids = 400;        // initial numbers of boids to create
    int desiredFps = 60;            // the desired number of fps to run the simulation
    long previousTime = 0;          // system time at previous frame in ms
    long currentTime = 0;           // system time at current frame in ms
    long elapsedTime = 0;           // elapsed time between the two last frame in ms
    int frameCounter = 0;           // counts the number of frames elapsed, reset when > fpsAvg
    int frameAverage = 10;          // number of frames to average the fps on
    int avgTimeSum = 0;             // stores the sum of the elapsed times between frames to calculate the average fps
    int fpsAvg;                     // the current average fps
    
    /**
     * Creates a panel in which all the boids are drawn and populates it with random boids
     * @param width  width of the area in which to create the initial boids
     * @param height  height of the area in which to create the initial boids
     */
    public GamePanel(int width, int height) {
        
        boids = new LinkedList<>();
        
        // Making numberOfBoids boids with random speeds and accelerations between -1 and 1
        for (int i = 0; i < numberOfBoids; i++) {
            boids.add(Boid.random(width, height));
        }
        
        // Game loop timer
        timer = new Timer(1000/desiredFps, this);
        timer.start();
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
