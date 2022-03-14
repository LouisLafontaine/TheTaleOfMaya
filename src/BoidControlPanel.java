/**
 * This class is a panel with sliders and button to change the parameters of boids
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoidControlPanel extends JPanel implements ActionListener, ChangeListener {
    
    public static BoidControlPanel instance;
    private Timer timer;
    
    private JLabel numberOfBoidsLabel;              // label displaying the number of boids displayed on screen
    private JLabel perceptionRadiusLabel;           // label displaying current perception radius of all boids
    private JLabel maxVelocityLabel;                // label displaying max velocity of all boids
    private JLabel maxAccelerationLabel;            // label displaying max acceleration of all boids
    private JLabel cohesionStrengthLabel;           // label displaying cohesion strength of all boids
    private JLabel alignmentStrengthLabel;          // label displaying alignement strength of all boids
    private JLabel separationStrengthLabel;         // label displaying separation strength of all boids
    private JLabel fpsLabel;                        // label displaying the current fps of the boid simulation of all boids
    
    private JSlider perceptionRadiusSlider;         // slider to modify current perception radius of all boids
    private JSlider maxVelocitySlider;              // slider to modify current max velocity of all boids
    private JSlider maxAccelerationSlider;          // slider to modify current max acceleration of all boids
    private JSlider cohesionStrengthSlider;         // slider to modify current cohesion strength of all boids
    private JSlider alignmentStrengthSlider;        // slider to modify alignment strength of all boids
    private JSlider separationStrengthSlider;       // slider to modify current separation strength of all boids
    
    private JButton addBoidsButton;                 // button to add boids to the flock
    private JButton removeBoidsButton;              // button to remove boids from the flock
    private JButton drawPerceptionRadiusButton;     // button to draw the boids's current perception radius
    
    private final int SLIDER_SCALE = 100;           // increases sliders resolution
    
    private boolean init = false;                   // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a ControlPanel
     */
    private BoidControlPanel() {
    
    }
    
    /**
     * This method ensures that only one instance the class can be created
     *
     * @return the instance of the ControlPanel class
     */
    public static BoidControlPanel get() {
        if(instance == null) {
            instance = new BoidControlPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance
     */
    public void init() {
        if(!init) {
            setBackground(Color.white);
    
            numberOfBoidsLabel = new JLabel("number of boids : " + BoidPanel.get().getDefaultNumberOfBoids());
            perceptionRadiusLabel = new JLabel("perception radius : " + Boid.perceptionRadius);
            maxVelocityLabel = new JLabel("max velocity : " + Boid.maxVelocity);
            maxAccelerationLabel = new JLabel("max acceleration : " + Boid.maxAcceleration);
            cohesionStrengthLabel = new JLabel("cohesion strength : " + Boid.cohesionStrength);
            alignmentStrengthLabel = new JLabel("alignment strength : " + Boid.alignmentStrength);
            separationStrengthLabel = new JLabel("separation strength : " + Boid.separationStrength);
            fpsLabel = new JLabel(String.valueOf(BoidPanel.get().getFps()));
    
            // sets the max value of the slider as slider range * starting value
            final int SLIDER_RANGE = 3;
            int s = SLIDER_RANGE * SLIDER_SCALE;
            perceptionRadiusSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) (s*Boid.perceptionRadius), (int)(SLIDER_SCALE *Boid.perceptionRadius));
            maxVelocitySlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.maxVelocity), (int)(SLIDER_SCALE *Boid.maxVelocity));
            maxAccelerationSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.maxAcceleration), (int)(SLIDER_SCALE *Boid.maxAcceleration));
            cohesionStrengthSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.cohesionStrength), (int)(SLIDER_SCALE *Boid.cohesionStrength));
            alignmentStrengthSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.alignmentStrength), (int)(SLIDER_SCALE *Boid.alignmentStrength));
            separationStrengthSlider = new JSlider(JSlider.HORIZONTAL, 0, (int)(s*Boid.separationStrength), (int)(SLIDER_SCALE *Boid.separationStrength));
    
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
        } else {
            System.err.println("The GameControlPanel instance has already been initialized !");
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
     * Invoked when the target of the listener has changed its state.
     * Main use is handling sliders in the control panel
     *
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == perceptionRadiusSlider) {
            perceptionRadiusLabel.setText("Perception radius : " + (double)perceptionRadiusSlider.getValue()/ SLIDER_SCALE);
            Boid.perceptionRadius = (double)perceptionRadiusSlider.getValue()/ SLIDER_SCALE;
        } else if(e.getSource() == maxVelocitySlider) {
            maxVelocityLabel.setText("Max velocity : " + (double)maxVelocitySlider.getValue()/ SLIDER_SCALE);
            Boid.maxVelocity = (double)maxVelocitySlider.getValue()/ SLIDER_SCALE;
        } else if(e.getSource() == maxAccelerationSlider) {
            maxAccelerationLabel.setText("Max acceleration : " + (double)maxAccelerationSlider.getValue()/ SLIDER_SCALE);
            Boid.maxAcceleration = (double)maxAccelerationSlider.getValue()/ SLIDER_SCALE;
        } else if(e.getSource() == cohesionStrengthSlider) {
            cohesionStrengthLabel.setText("Cohesion strength : " + (double)cohesionStrengthSlider.getValue()/ SLIDER_SCALE);
            Boid.cohesionStrength = (double)cohesionStrengthSlider.getValue()/ SLIDER_SCALE;
        } else if(e.getSource() == alignmentStrengthSlider) {
            alignmentStrengthLabel.setText("Alignment strength : " + (double)alignmentStrengthSlider.getValue()/ SLIDER_SCALE);
            Boid.alignmentStrength = (double)alignmentStrengthSlider.getValue()/ SLIDER_SCALE;
        } else if(e.getSource() == separationStrengthSlider) {
            separationStrengthLabel.setText("Separation strength : " + (double)separationStrengthSlider.getValue()/ SLIDER_SCALE);
            Boid.separationStrength = (double)separationStrengthSlider.getValue()/ SLIDER_SCALE;
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
            BoidPanel.get().addBoids(50);
            numberOfBoidsLabel.setText("number of boids : " + BoidPanel.get().getNumberOfBoids());
        } else if (e.getSource() == removeBoidsButton) {
            BoidPanel.get().removeBoids(50);
            numberOfBoidsLabel.setText("number of boids : " + BoidPanel.get().getNumberOfBoids());
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
            fpsLabel.setText("fps : " + BoidPanel.get().getFps());
        }
    }
}
