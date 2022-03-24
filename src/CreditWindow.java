/**
 * This class is a GUI of the game's credits
 */

import javax.swing.*;

public class CreditWindow extends JFrame {
    public static CreditWindow instance;  // instance of the CreditWindow class
    private boolean init = false;       // true if the instance has been initialized, false otherwise

    /**
     * Creates a CreditWindow
     */
    private CreditWindow() {
        super("Credit Window");
    }

    /**
     * This method ensures that only one instance of the CreditWindow class can be created
     *
     * @return the instance of the CreditWindow class
     */
    public static CreditWindow get() {
        if (instance == null) {
            instance = new CreditWindow();
        }
        return instance;
    }

    /**
     * Initializes the instance of the CreditWindow class
     */
    public void init() {
        if(!init) {
            init = true;

            // Main Panel ----------------------------------------------------------------------------------------------
            CreditPanel creditPanel = CreditPanel.get();
            add(creditPanel);

            creditPanel.init();

            // Window settings -----------------------------------------------------------------------------------------
            setExtendedState(JFrame.MAXIMIZED_BOTH);   // fullscreen
            setUndecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        } else {
            System.err.println("The CreditWindow instance has already been initialized !");
        }
    }
}
