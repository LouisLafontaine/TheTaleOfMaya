/**
 * This class is a GUI that allows users to visualize boids and experiment with the variables dictating their behaviour
 */

package boid;

import javax.swing.*;
import java.awt.*;

public class BoidWindow extends JFrame {
    
    public static BoidWindow instance;
    private boolean init = false;                   // true if the instance has been initialized, false otherwise
    
    
    /**
     * Creates a Boid.BoidWindow
     */
    private BoidWindow() {
        super("Boid.Boid simulation");
    }
    
    /**
     * This method ensures that only one instance the class can be created
     *
     * @return the instance
     */
    public static BoidWindow get() {
        if(instance == null) {
            instance = new BoidWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance
     */
    public BoidWindow init() {
        if(!init) {
            // Window settings -----------------------------------------------------------------------------------------
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            // Game panel --------------------------------------------------------------------------------------------------
            // panel in which all the boids are drawn
            BoidPanel boidPanel = BoidPanel.get().init();
    
            // Control panel -----------------------------------------------------------------------------------------------
            // panel with sliders and button to change the parameter of boids
            BoidControlPanel controlPanel = BoidControlPanel.get().init();
    
            // Main panel --------------------------------------------------------------------------------------------------
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
    
            boidPanel.setPreferredSize(new Dimension(1670,1080));
            controlPanel.setPreferredSize(new Dimension(250,1080));
    
            mainPanel.add(boidPanel, BorderLayout.CENTER);
            mainPanel.add(controlPanel, BorderLayout.EAST);
            add(mainPanel);
    
            init = true;
        } else {
            System.err.println("The Boid.BoidWindow instance has already been initialized !");
        }
        return BoidWindow.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
    @Override
    public void dispose() {
        super.dispose();
        instance = null;
        BoidPanel.get().dispose();
        BoidControlPanel.get().dispose();
    }
}
