import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameWindow extends JFrame implements WindowListener {
    
    public static GameWindow instance;
    private GamePanel gamePanel;
    private boolean init = false;
    
    /**
     * Creates a GameWindow
     */
    private GameWindow() {
        super("The Tale Of Maya - a boid adventure");
    }
    
    /**
     * This method ensures that only one instance of the GameWindow class can be created
     *
     * @return the instance of the GameWindow class
     */
    public static GameWindow get() {
        if(instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the GameWindow class
     */
    public void init() {
        if(!init) {
            // Window settings ---------------------------------------------------------------------------------------------
            addWindowListener(this);
            setExtendedState(JFrame.MAXIMIZED_BOTH);   // fullscreen
            setUndecorated(true);
            setAlwaysOnTop(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            
            // Game panel --------------------------------------------------------------------------------------------------
            gamePanel = GamePanel.get();
            add(gamePanel);
            
            
            setVisible(true);
            
            // initialization must happen after the component has been added and setVisible(true)
            gamePanel.init();
            
            init = true;
        } else {
            System.err.println("The GameWindow instance has already been initialized !");
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
        MenuWindow.get().setVisible(true);
        
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
