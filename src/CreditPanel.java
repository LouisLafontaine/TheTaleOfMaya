/**
 * This class is a panel containing all the game's credits elements
 */

import javax.swing.*;
import java.awt.*;

public class CreditPanel extends JPanel {

    public static CreditPanel instance;       // instance of the CreditPanel class
    private boolean init = false;           // true if the instance has been initialized, false otherwise

    /**
     * Creates a CreditPanel
     */
    private CreditPanel() {

    }

    /**
     * This method ensures that only one instance of the CreditPanel class can be created
     *
     * @return the instance of the CreditPanel class
     */
    public static CreditPanel get() {
        if(instance == null) {
            instance = new CreditPanel();
        }
        return instance;
    }

    /**
     * Initializes the instance of the CreditPanel class
     */
    public void init() {
        if(!init) {
            init = true;

        } else {
            System.err.println("The CreditPanel instance has already been initialized !");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
