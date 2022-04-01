/**
 * This class is a panel containing all the game's menu elements
 */

package menu;

import boid.BoidWindow;
import game.Animation;
import game.GameWindow;
import game.mapEditor.MapEditorWindow;
import util.FontUtil;
import util.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class MenuPanel extends JPanel implements ActionListener, KeyListener {
    
    public static MenuPanel instance;           // instance of the Menu.MenuPanel class
    private boolean init = false;               // true if the instance has been initialized, false otherwise
    private Timer timer;
    private HashMap<String, JButton> buttons;
    private Animation background;
    
    /**
     * Creates a Menu.MenuPanel
     */
    private MenuPanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the Menu.MenuPanel class can be created
     *
     * @return the instance of the Menu.MenuPanel class
     */
    public static MenuPanel get() {
        if (instance == null) {
            instance = new MenuPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the Menu.MenuPanel class
     */
    public MenuPanel init() {
        if (!init) {
            init = true;
            
            addKeyListener(this);
            setFocusable(true);
            
            background = new Animation("resources/images/menuImages/cherryBlossom.png", 576, 324, 128, 1, 12);
            timer = new Timer(1000 / 60, this);
            
            // Setting Layout
            // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
            
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
    
            buttons = new HashMap<>();
            String[] buttonNames = new String[]{"Launch game", "Launch boid", "Map editor", "Credits", "Exit menu"};
    
            Color pink = new Color(240, 98, 146);
            
            for (String s : buttonNames) {
                JButton b = new JButton(s);
                b.setFont(FontUtil.getFrom("resources/fonts/pixelFont.otf", 50));
                b.setMargin(new Insets(5, 0, 7, 0));
                b.setForeground(pink);
                b.addActionListener(this);
                add(b, gbc);
                buttons.put(s, b);
            }
            
            JLabel exitLabel = new JLabel("Press ESC to quit");
            exitLabel.setFont(FontUtil.getFrom("resources/fonts/pixelFont.otf", 40));
            exitLabel.setForeground(pink);
            add(exitLabel, gbc);
            
            timer.start();
        } else {
            System.err.println("The Menu.MenuPanel instance has already been initialized !");
        }
        return MenuPanel.get();
    }
    
    protected void dispose() {
        instance = null;
        timer.stop();
    }
    
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == timer) {
            repaint();
        }
        if (source == buttons.get("Launch boid")) {
            MenuWindow.get().dispose();
            MainWindow.switchTo(BoidWindow.get().init());
        } else if (source == buttons.get("Launch game")) {
            MenuWindow.get().dispose();
            MainWindow.switchTo(GameWindow.get().init());
        } else if (source == buttons.get("Credits")) {
            MenuWindow.get().dispose();
            MainWindow.switchTo(CreditWindow.get().init());
        } else if(source == buttons.get("Map editor")) {
            MenuWindow.get().dispose();
            MainWindow.switchTo(MapEditorWindow.get().init());
        } else if (source == buttons.get("Exit menu")) {
            System.exit(0);
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getCurrentFrame(), 0, 0, getWidth(), getHeight(), null);
    }
    
    
    //==================================================================================================================
    // KeyListener Methods
    //==================================================================================================================
    
    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == VK_ESCAPE) {
            System.out.println("Thanks for playing !");
            System.exit(0);
        }
    }
    
    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
    
    }
}