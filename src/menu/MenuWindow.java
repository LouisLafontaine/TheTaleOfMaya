/**
 * This class is a GUI of the game's menu
 */

package menu;

import util.MainWindow;

import javax.swing.*;

public class MenuWindow extends JFrame {
    
    private static MenuWindow instance;      // instance of the Menu.MenuWindow class
    private boolean init = false;           // true if the instance has been initialized, false otherwise
    
    /**
     * Creates a Menu.MenuWindow
     */
    private MenuWindow() {
    
    }
    
    /**
     * This method ensures that only one instance of the Menu.MenuWindow class can be created
     *
     * @return the instance of the Menu.MenuWindow class
     */
    public static MenuWindow get() {
        if (instance == null) {
            instance = new MenuWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the Menu.MenuWindow class
     */
    public MenuWindow init() {
        if (!init) {
            init = true;
            
            // Main Panel ----------------------------------------------------------------------------------------------
            MenuPanel menuPanel = MenuPanel.get();
            add(menuPanel);
            menuPanel.init();
        } else {
            System.err.println("The MenuWindow instance has already been initialized !");
        }
        return MenuWindow.get();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        instance = null;
        MenuPanel.get().dispose();
    }
}