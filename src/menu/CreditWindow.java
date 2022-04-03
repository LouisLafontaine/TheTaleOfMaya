/**
 * This class is a GUI of the game's credits
 */

package menu;

import com.sun.tools.javac.Main;
import util.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class CreditWindow extends JFrame {
    
    public static CreditWindow instance;  // instance of the Menu.CreditWindow class
    private boolean init = false;       // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a Menu.CreditWindow
     */
    private CreditWindow() {
        super("Credit Window");
    }

    /**
     * This method ensures that only one instance of the Menu.CreditWindow class can be created
     *
     * @return the instance of the Menu.CreditWindow class
     */
    public static CreditWindow get() {
        if (instance == null) {
            instance = new CreditWindow();
        }
        return instance;
    }

    /**
     * Initializes the instance of the Menu.CreditWindow class
     */
    public CreditWindow init() {
        if(!init) {
            init = true;
            
            // Window settings -----------------------------------------------------------------------------------------
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            // Main Panel ----------------------------------------------------------------------------------------------
            CreditPanel creditPanel = CreditPanel.get();
            add(creditPanel);
            creditPanel.init();
        } else {
            System.err.println("The Menu.CreditWindow instance has already been initialized !");
        }
        return instance;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        instance = null;
        CreditPanel.get().dispose();
    }
}
