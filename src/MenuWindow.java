/**
 * This class is a GUI of the game's menu
 */

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    
    public static MenuWindow instance;  // instance of the MenuWindow class
    private static GraphicsDevice gd;   // for fullscreen support, https://docs.oracle.com/javase/6/docs/api/java/awt/GraphicsDevice.html#setDisplayMode(java.awt.DisplayMode)
    private boolean init = false;       // true if the instance has been initialized, false otherwise
    
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
    
            setVisible(true);
    
            // Window settings -----------------------------------------------------------------------------------------
            gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                // full-screen mode
                gd.setFullScreenWindow(this);
            } else {
                // non-full-screen mode
                setLocation(200, 200);
                setSize(500, 200);
                setAlwaysOnTop(true);
                setVisible(true);
                System.err.println("!!! Full-screen not supported");
            }
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            System.err.println("The MenuWindow instance has already been initialized !");
        }
    }
    
    protected void display() {
        gd.setFullScreenWindow(this);
    }
    
    protected void conceal() {
        gd.setFullScreenWindow(null);
        setVisible(false);
    }
}