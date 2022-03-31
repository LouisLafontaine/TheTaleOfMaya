package game;

import util.MainWindow;

import javax.swing.*;

public class GameWindow extends JFrame {
    
    public static GameWindow instance;
    private boolean init = false;
    
    /**
     * Creates a Game.GameWindow
     */
    private GameWindow() {
        super();
    }
    
    /**
     * This method ensures that only one instance of the Game.GameWindow class can be created
     *
     * @return the instance of the Game.GameWindow class
     */
    public static GameWindow get() {
        if(instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the Game.GameWindow class
     */
    public GameWindow init() {
        if(!init) {
            init = true;
            
            // Window settings -----------------------------------------------------------------------------------------
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            // Game panel --------------------------------------------------------------------------------------------------
            add(GamePanel.get().init());
            
        } else {
            System.err.println("The GameWindow instance has already been initialized !");
        }
        return GameWindow.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
    @Override
    public void dispose() {
        super.dispose();
        instance = null;
        GamePanel.get().dispose();
    }
}
