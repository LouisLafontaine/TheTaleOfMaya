package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import Menu.MenuWindow;

public class GameWindow extends JFrame implements WindowListener {
    
    public static GameWindow instance;
    private boolean init = false;
    GraphicsDevice gd;
    
    private GamePanel gamePanel;
    
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
    public void init() {
        if(!init) {
            init = true;
            
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
            
            
            // Game panel --------------------------------------------------------------------------------------------------
            gamePanel = GamePanel.get();
            add(gamePanel);
            gamePanel.init();
            
        } else {
            System.err.println("The Game.GameWindow instance has already been initialized !");
        }
    }
    
    /**
     * Resets the instance and sets init to false
     */
    protected void reset() {
        if(init) {
            instance = null;
            init = false;
            gamePanel.reset();
        }
    }
    
    /**
     * Invoked the first time a window is made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {
    
    }
    
    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
    
    }
    
    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {
        reset();
        MenuWindow.gd.setFullScreenWindow(MenuWindow.get());
        
    }
    
    /**
     * Invoked when a window is changed from a normal to a
     * minimized state. For many platforms, a minimized window
     * is displayed as the icon specified in the window's
     * iconImage property.
     *
     * @param e the event to be processed
     * @see Frame#setIconImage
     */
    @Override
    public void windowIconified(WindowEvent e) {
    
    }
    
    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
    
    }
    
    /**
     * Invoked when the Window is set to be the active Window. Only a Frame or
     * a Dialog can be the active Window. The native windowing system may
     * denote the active Window or its children with special decorations, such
     * as a highlighted title bar. The active Window is always either the
     * focused Window, or the first Frame or Dialog that is an owner of the
     * focused Window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {
    
    }
    
    /**
     * Invoked when a Window is no longer the active Window. Only a Frame or a
     * Dialog can be the active Window. The native windowing system may denote
     * the active Window or its children with special decorations, such as a
     * highlighted title bar. The active Window is always either the focused
     * Window, or the first Frame or Dialog that is an owner of the focused
     * Window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }
}
