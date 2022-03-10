/**
 * This class is a panel with sliders and button to change the parameters of boids
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControlPanel extends JPanel implements ActionListener, ChangeListener {
    
    public static GameControlPanel instance;
    Timer timer;
    
    JLabel numberOfBoidsLabel;              // label displaying the number of boids displayed on screen
    JLabel perceptionRadiusLabel;           // label displaying current perception radius of all boids
    JLabel maxVelocityLabel;                // label displaying max velocity of all boids
    JLabel maxAccelerationLabel;            // label displaying max acceleration of all boids
    JLabel cohesionStrengthLabel;           // label displaying cohesion strength of all boids
    JLabel alignmentStrengthLabel;          // label displaying alignement strength of all boids
    JLabel separationStrengthLabel;         // label displaying separation strength of all boids
    JLabel fpsLabel;                        // label displaying the current fps of the boid simulation of all boids
    
    JSlider perceptionRadiusSlider;         // slider to modify current perception radius of all boids
    JSlider maxVelocitySlider;              // slider to modify current max velocity of all boids
    JSlider maxAccelerationSlider;          // slider to modify current max acceleration of all boids
    JSlider cohesionStrengthSlider;         // slider to modify current cohesion strength of all boids
    JSlider alignmentStrengthSlider;        // slider to modify alignment strength of all boids
    JSlider separationStrengthSlider;       // slider to modify current separation strength of all boids
    
    JButton addBoidsButton;                 // button to add boids to the flock
    JButton removeBoidsButton;              // button to remove boids from the flock
    JButton drawPerceptionRadiusButton;     // button to draw the boids's current perception radius
    
    int sliderScale = 100;                  // increases sliders resolution
    int sliderRange = 3;                    // sets the max value of the slider as slider range * starting value
    
    boolean init = false;                   // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a ControlPanel
     */
    private GameControlPanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the Control class can be created
     *
     * @return the instance of the ControlPanel class
     */
    public static GameControlPanel get() {
        if(instance == null) {
            instance = new GameControlPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the ControlPanel class
     */
    public void init() {
        if(!init) {
            setBackground(Color.white);
    
            numberOfBoidsLabel = new JLabel("number of boids : " + GamePanel.get().initialNumberOfBoids);
            perceptionRadiusLabel = new JLabel("perception radius : " + Boid.perceptionRadius);
            maxVelocityLabel = new JLabel("max velocity : " + Boid.maxVelocity);
            maxAccelerationLabel = new JLabel("max acceleration : " + Boid.maxAcceleration);
            cohesionStrengthLabel = new JLabel("cohesion strength : " + Boid.cohesionStrength);
            alignmentStrengthLabel = new JLabel("alignment strength : " + Boid.alignmentStrength);
            separationStrengthLabel = new JLabel("separation strength : " + Boid.separationStrength);
            fpsLabel = new JLabel(String.valueOf(GamePanel.get().fpsAvg));
    
            int s = sliderRange*sliderScale;
            perceptionRadiusSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) (s*Boid.perceptionRadius), (int)(sliderScale*Boid.perceptionRadius));
            maxVelocitySlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.maxVelocity), (int)(sliderScale*Boid.maxVelocity));
            maxAccelerationSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.maxAcceleration), (int)(sliderScale*Boid.maxAcceleration));
            cohesionStrengthSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.cohesionStrength), (int)(sliderScale*Boid.cohesionStrength));
            alignmentStrengthSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.alignmentStrength), (int)(sliderScale*Boid.alignmentStrength));
            separationStrengthSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.separationStrength), (int)(sliderScale*Boid.separationStrength));
    
            perceptionRadiusSlider.addChangeListener(this);
            maxVelocitySlider.addChangeListener(this);
            maxAccelerationSlider.addChangeListener(this);
            cohesionStrengthSlider.addChangeListener(this);
            alignmentStrengthSlider.addChangeListener(this);
            separationStrengthSlider.addChangeListener(this);
    
            addBoidsButton = new JButton("+ 50");
            removeBoidsButton = new JButton("- 50");
            drawPerceptionRadiusButton = new JButton("draw perception radius");
    
            addBoidsButton.setForeground(Color.green);
            removeBoidsButton.setForeground(Color.red);
            drawPerceptionRadiusButton.setForeground(Color.red);
    
            addBoidsButton.addActionListener(this);
            removeBoidsButton.addActionListener(this);
            drawPerceptionRadiusButton.addActionListener(this);
    
            add(numberOfBoidsLabel);
            add(addBoidsButton);
            add(removeBoidsButton);
    
            add(perceptionRadiusLabel);
            add(perceptionRadiusSlider);
            add(drawPerceptionRadiusButton);
    
            add(cohesionStrengthLabel);
            add(cohesionStrengthSlider);
    
            add(alignmentStrengthLabel);
            add(alignmentStrengthSlider);
    
            add(separationStrengthLabel);
            add(separationStrengthSlider);
    
            add(maxVelocityLabel);
            add(maxVelocitySlider);
    
            add(maxAccelerationLabel);
            add(maxAccelerationSlider);
    
            add(fpsLabel);
    
            timer = new Timer(33, this);
            timer.start();
    
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
     * Invoked when the target of the listener has changed its state.
     * Main use is handling sliders in the control panel
     *
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == perceptionRadiusSlider) {
            perceptionRadiusLabel.setText("Perception radius : " + (double)perceptionRadiusSlider.getValue()/sliderScale);
            Boid.perceptionRadius = (double)perceptionRadiusSlider.getValue()/sliderScale;
        } else if(e.getSource() == maxVelocitySlider) {
            maxVelocityLabel.setText("Max velocity : " + (double)maxVelocitySlider.getValue()/sliderScale);
            Boid.maxVelocity = (double)maxVelocitySlider.getValue()/sliderScale;
        } else if(e.getSource() == maxAccelerationSlider) {
            maxAccelerationLabel.setText("Max acceleration : " + (double)maxAccelerationSlider.getValue()/sliderScale);
            Boid.maxAcceleration = (double)maxAccelerationSlider.getValue()/sliderScale;
        } else if(e.getSource() == cohesionStrengthSlider) {
            cohesionStrengthLabel.setText("Cohesion strength : " + (double)cohesionStrengthSlider.getValue()/sliderScale);
            Boid.cohesionStrength = (double)cohesionStrengthSlider.getValue()/sliderScale;
        } else if(e.getSource() == alignmentStrengthSlider) {
            alignmentStrengthLabel.setText("Alignment strength : " + (double)alignmentStrengthSlider.getValue()/sliderScale);
            Boid.alignmentStrength = (double)alignmentStrengthSlider.getValue()/sliderScale;
        } else if(e.getSource() == separationStrengthSlider) {
            separationStrengthLabel.setText("Separation strength : " + (double)separationStrengthSlider.getValue()/sliderScale);
            Boid.separationStrength = (double)separationStrengthSlider.getValue()/sliderScale;
        }
    }
    
    /**
     * Invoked when an action occurs.
     * Main use is handling buttons in the control panel
     * @param e  the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addBoidsButton) {
            GamePanel.get().addBoids(50);
            numberOfBoidsLabel.setText("number of boids : " + GamePanel.get().boids.size());
        } else if (e.getSource() == removeBoidsButton) {
            GamePanel.get().removeBoids(50);
            numberOfBoidsLabel.setText("number of boids : " + GamePanel.get().boids.size());
        } else if (e.getSource() == drawPerceptionRadiusButton) {
            if (!Boid.drawPerceptionRadius) {
                drawPerceptionRadiusButton.setForeground(Color.green);
                drawPerceptionRadiusButton.setText("draw perception radius : on");
                Boid.drawPerceptionRadius = true;
            } else {
                drawPerceptionRadiusButton.setForeground(Color.red);
                drawPerceptionRadiusButton.setText("draw perception radius : off");
                Boid.drawPerceptionRadius = false;
            }
        } else {
            fpsLabel.setText("fps : " + GamePanel.get().fpsAvg);
        }
    }
}
