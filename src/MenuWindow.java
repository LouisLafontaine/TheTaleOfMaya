/**
 * This class is a GUI of the game's menu
 */

import javax.swing.*;

public class MenuWindow extends JFrame {
    
    public static MenuWindow instance;  // instance of the MenuWindow class
    private boolean init = false;               // true if the instance has been initialized, false otherwise
    
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
            // Window settings ---------------------------------------------------------------------------------------------
            setLocation(200, 200);
            setSize(500, 200);
            setAlwaysOnTop(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
            // Main Panel --------------------------------------------------------------------------------------------------
            MenuPanel menuPanel = MenuPanel.get();
            add(menuPanel);
    
            setVisible(true);
            
            // initialization must happen after the component has been added and setVisible(true)
            menuPanel.init();
            
            init = true;
        } else {
            System.err.println("The MenuWindow instance has already been initialized !");
        }
    }
}