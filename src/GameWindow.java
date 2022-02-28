/**
 * This class is a GUI that allows users to visualize boids and experiment with the variables dictating their behaviour
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class GameWindow extends JFrame implements ActionListener, ChangeListener {
    
    GamePanel gamePanel;                    // panel in which all the boids are drawn
    
    JLabel numberOfBoidsLabel;              // label displaying the number of boids displayed on screen
    JLabel perceptionRadiusLabel;           // label displaying boids's current perception radius
    JLabel maxVelocityLabel;                // label displaying boids's current max velocity
    JLabel maxAccelerationLabel;            // label displaying boids's current max acceleration
    JLabel cohesionStrengthLabel;           // label displaying boids's current cohesion strength
    JLabel alignmentStrengthLabel;          // label displaying boids's current alignement strength
    JLabel separationStrengthLabel;         // label displaying boids's current separation strength
    JLabel fpsLabel;                        // label displaying the current fps of the boid simulation
    
    JSlider perceptionRadiusSlider;         // slider to modify boids's current perception radius
    JSlider maxVelocitySlider;              // slider to modify boids's current max velocity
    JSlider maxAccelerationSlider;          // slider to modify boids's current max acceleration
    JSlider cohesionStrengthSlider;         // slider to modify boids's current cohesion strength
    JSlider alignmentStrengthSlider;        // slider to modify boids's alignment strength
    JSlider separationStrengthSlider;       // slider to modify boids's current separation strength
    
    JButton addBoidsButton;                 // button to add boids to the flock
    JButton removeBoidsButton;              // button to remove boids from the flock
    JButton drawPerceptionRadiusButton;     // button to draw the boids's current perception radius
    
    int sliderScale = 100;                  // increases sliders resolution
    int sliderRange = 3;                    // sets the max value of the slider as slider range * starting value
    int gamePanelDesiredWidth = 800;        // gamePanel's desired width, sets the area in which the first boids are spawned
    int gamePanelDesiredHeight = 600;       // gamePanel's desired height, sets the area in which the first boids are spawned
    
    Timer timer;
    
    
    public GameWindow() {
        super("The Tale Of Maya - a boid adventure");
        
        // Window settings ---------------------------------------------------------------------------------------------
        setLocation(200, 200);
        setSize(1000, 600);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Game panel --------------------------------------------------------------------------------------------------
        gamePanel = new GamePanel(gamePanelDesiredWidth, gamePanelDesiredHeight);
        timer = new Timer(33, this);
        addActionListener(this);
        
        // Control panel -----------------------------------------------------------------------------------------------
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.white);
        
        numberOfBoidsLabel = new JLabel("number of boids : " + gamePanel.boids.size());
        perceptionRadiusLabel = new JLabel("perception radius : " + Boid.perceptionRadius);
        maxVelocityLabel = new JLabel("max velocity : " + Boid.maxVelocity);
        maxAccelerationLabel = new JLabel("max acceleration : " + Boid.maxAcceleration);
        cohesionStrengthLabel = new JLabel("cohesion strength : " + Boid.cohesionStrength);
        alignmentStrengthLabel = new JLabel("alignment strength : " + Boid.alignmentStrength);
        separationStrengthLabel = new JLabel("separation strength : " + Boid.separationStrength);
        fpsLabel = new JLabel(String.valueOf(gamePanel.fpsAvg));
        
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
        
        controlPanel.add(numberOfBoidsLabel);
        controlPanel.add(addBoidsButton);
        controlPanel.add(removeBoidsButton);
        
        controlPanel.add(perceptionRadiusLabel);
        controlPanel.add(perceptionRadiusSlider);
        controlPanel.add(drawPerceptionRadiusButton);
        
        controlPanel.add(cohesionStrengthLabel);
        controlPanel.add(cohesionStrengthSlider);
        
        controlPanel.add(alignmentStrengthLabel);
        controlPanel.add(alignmentStrengthSlider);
        
        controlPanel.add(separationStrengthLabel);
        controlPanel.add(separationStrengthSlider);
        
        controlPanel.add(maxVelocityLabel);
        controlPanel.add(maxVelocitySlider);
        
        controlPanel.add(maxAccelerationLabel);
        controlPanel.add(maxAccelerationSlider);
    
        controlPanel.add(fpsLabel);
        
        // Main panel --------------------------------------------------------------------------------------------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        gamePanel.setPreferredSize(new Dimension(gamePanelDesiredWidth,gamePanelDesiredHeight));
        controlPanel.setPreferredSize(new Dimension(200,600));
        
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);
        
        add(mainPanel);
        
        setVisible(true);
        timer.start();
    }
    
    /**
     * Invoked when an action occurs.
     * Main use is handling buttons in the control panel
     * @param e  the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addBoidsButton) {
            gamePanel.addBoids(50);
            numberOfBoidsLabel.setText("number of boids : " + gamePanel.boids.size());
        } else if (e.getSource() == removeBoidsButton) {
            gamePanel.removeBoids(50);
            numberOfBoidsLabel.setText("number of boids : " + gamePanel.boids.size());
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
            fpsLabel.setText("fps : " + gamePanel.fpsAvg);
        }
    }
    
    /**
     * Invoked when the target of the listener has changed its state.
     * Main use is handling sliders in the control panel
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
}
