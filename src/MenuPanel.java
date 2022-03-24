/**
 * This class is a panel containing all the game's menu elements
 */

import util.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel implements ActionListener {
    
    public static MenuPanel instance;       // instance of the MenuPanel class
    private JButton launchBoidButton;       // button to launch the boid simulation
    private JButton launchGameButton;       // button to launch the game
    private JButton exitMenuButton;         // button to exit the menu
    private JButton creditsButton;          // button to show the credits
    private boolean init = false;           // true if the instance has been initialized, false otherwise
    private BufferedImage menubackground;
    
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
            init = true;
            
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

            menubackground = Image.getFrom("resources/images/menubg.png");
            
            launchBoidButton = new JButton("Launch boid");
            launchGameButton = new JButton("Launch game");
            exitMenuButton = new JButton("Exit menu");
            creditsButton = new JButton("Credits");
    
            launchBoidButton.addActionListener(this);
            launchGameButton.addActionListener(this);
            exitMenuButton.addActionListener(this);
            creditsButton.addActionListener(this);
    
            add(launchBoidButton);
            add(launchGameButton);
            add(exitMenuButton);
            add(creditsButton);
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
            MenuWindow.get().setVisible(false);
            BoidWindow boidWindow = BoidWindow.get();
            boidWindow.init();
        } else if(e.getSource() == launchGameButton) {
            MenuWindow.get().setVisible(false);
            GameWindow gameWindow = GameWindow.get();
            gameWindow.init();
        } else if(e.getSource() == exitMenuButton) {
            String title = "Quitting the menu";
            String message = "Are you sure you want to leave the game ?";
            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if(e.getSource() == creditsButton){
            CreditWindow.get().setVisible(false);
            CreditWindow creditWindow = CreditWindow.get();
            creditWindow.init();
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(menubackground, 0, 0, null);
    }
}