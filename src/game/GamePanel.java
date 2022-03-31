package game;

import util.MainWindow;
import menu.MenuWindow;
import util.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Timer timer;
    private TileManager tileManager;
    private Player player;
    
    // Screen settings
    public final int tileRes = 16;
    public final int scale = 16;
    public final int tileSize = tileRes * scale;
    
    
    // World settings
    
    /**
     * Creates a Game.GamePanel
     */
    private GamePanel() {
    
    }
    
    /**
     * This method ensures that only one instance the class can be created
     *
     * @return the instance
     */
    public static GamePanel get() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance
     */
    public GamePanel init() {
        if (!init) {
            init = true;
            
            addKeyListener(this);
            setFocusable(true);
            
            tileManager = TileManager.get();
            tileManager.loadMap("resources/maps/map.txt");
    
            // Entities displayed
            double x = (tileManager.map[0].length/2.0) * tileManager.tileSize;
            double y = (tileManager.map.length/2.0) * tileManager.tileSize;
            player = Player.get(x, y);
            

            // Keyboard inputs
            KeyHandler keyboard = KeyHandler.get();
            addKeyListener(keyboard);
            
            timer = new Timer(33, this);
            timer.start();
        } else {
            System.err.println("The Game.GamePanel instance has already been initialized !");
        }
        return GamePanel.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
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
        if (e.getSource() == timer) {
            keyInput();
            player.update();
            repaint();
        }
    }
    
    private void keyInput() {
        if(KeyHandler.isPressed(VK_ESCAPE)) {
            dispose();
            MainWindow.switchTo(MenuWindow.get());
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileManager.draw(g);
        player.draw(g);
        player.showBoundary(g);
    }
    
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
            GameWindow.get().dispose();
            MainWindow.switchTo(MenuWindow.get().init());
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
