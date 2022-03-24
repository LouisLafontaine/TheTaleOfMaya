/**
 * This class is a GUI of the game's menu
 */

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    
    public static MenuWindow instance;      // instance of the MenuWindow class
    private boolean init = false;           // true if the instance has been initialized, false otherwise
    protected static GraphicsDevice gd;
    
    /**
     * Creates a MenuWindow
     */
    private MenuWindow() {
        super("Menu Window");
    }
    
    /**
     * This method ensures that only one instance of the MenuWindow class can be created
     *
     * @return the instance of the MenuWindow class
     */
    public static MenuWindow get() {
        if (instance == null) {
            instance = new MenuWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the MenuWindow class
     */
    public void init() {
        if(!init) {
            init = true;
            
            // Main Panel ----------------------------------------------------------------------------------------------
            MenuPanel menuPanel = MenuPanel.get();
            add(menuPanel);
            
            menuPanel.init();
    
            // Window settings -----------------------------------------------------------------------------------------
            gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                setUndecorated(true);
                gd.setFullScreenWindow(this);
            } else {
                setSize(600,600); // to still see the window even if there is a problem
                System.err.println("Full screen not supported");
            }
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } else {
            System.err.println("The MenuWindow instance has already been initialized !");
        }
    }
}