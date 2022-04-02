/**
 * This class is a panel containing all the game's credits elements
 */

package menu;


import util.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class CreditPanel extends JPanel implements KeyListener {

    public static CreditPanel instance;       // instance of the Menu.CreditPanel class
    private boolean init = false;           // true if the instance has been initialized, false otherwise

    /**
     * Creates a Menu.CreditPanel
     */
    private CreditPanel() {

    }

    /**
     * This method ensures that only one instance of the Menu.CreditPanel class can be created
     *
     * @return the instance of the Menu.CreditPanel class
     */
    public static CreditPanel get() {
        if(instance == null) {
            instance = new CreditPanel();
        }
        return instance;
    }

    /**
     * Initializes the instance of the Menu.CreditPanel class
     */
    public CreditPanel init() {
        if(!init) {
            init = true;
            
            addKeyListener(this);
            setFocusable(true);
    
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            JLabel label1 = new JLabel("Made with love");
            JLabel label2 = new JLabel("press ESC to return to the menu");
            label1.setForeground(Color.white);
            label2.setForeground(Color.white);
            add(label1, gbc);
            add(label2, gbc);
            
        } else {
            System.err.println("The Menu.CreditPanel instance has already been initialized !");
        }
        return instance;
    }
    
    protected void dispose() {
        instance = null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == VK_ESCAPE) {
            CreditWindow.get().dispose();
            MainWindow.switchTo(MenuWindow.get().init());
        }
    }
    
    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
    
    }
}
