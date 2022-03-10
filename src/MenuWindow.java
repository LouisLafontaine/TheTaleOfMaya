import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuWindow extends JFrame implements WindowListener {
    
    public static MenuWindow instance;
    
    private MenuPanel menuPanel;

    /**
     * Creates a new MenuWindow
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
        if(instance == null) {
            instance = new MenuWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the MenuWindow class
     */
    public void init() {
        // Window settings ---------------------------------------------------------------------------------------------
        addWindowListener(this);
        setLocation(200,200);
        setSize(1000,600);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Main Panel --------------------------------------------------------------------------------------------------
        menuPanel = MenuPanel.get();
        add(menuPanel);
    
        setVisible(true);
    }
    
    /**
     * Invoked the first time a window is made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {
        menuPanel.init();
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
