/**
 * This class is a panel containing all the game's credits elements
 */

package Menu;

import javax.swing.*;
import java.awt.*;

public class CreditPanel extends JPanel {

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
    public void init() {
        if(!init) {
            init = true;

        } else {
            System.err.println("The Menu.CreditPanel instance has already been initialized !");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
