/**
 * This class is a panel containing all the game's menu elements
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {
    
    public static MenuPanel instance;       // instance of the MenuPanel class
    private JButton launchBoidButton;       // button to launch the boid simulation
    private JButton launchGameButton;       // button to launch the game
    private boolean init = false;                   // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a MenuPanel
     */
    private MenuPanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the MenuPanel class can be created
     *
     * @return the instance of the MenuPanel class
     */
    public static MenuPanel get() {
        if(instance == null) {
            instance = new MenuPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the MenuPanel class
     */
    public void init() {
        if(!init) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(Color.red);
            
            launchBoidButton = new JButton("Launch boid");
            launchGameButton = new JButton("Launch game");
    
            launchBoidButton.addActionListener(this);
            launchGameButton.addActionListener(this);
    
            add(launchBoidButton);
            add(launchGameButton);
            
            init = true;
        } else {
            System.err.println("The MenuPanel instance has already been initialized !");
        }
    }
    
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == launchBoidButton) {
            MenuWindow.get().conceal();
            BoidWindow boidWindow = BoidWindow.get();
            boidWindow.init();
        } else if(e.getSource() == launchGameButton) {
            MenuWindow.get().conceal();
            GameWindow gameWindow = GameWindow.get();
            gameWindow.init();
        }
    }
}