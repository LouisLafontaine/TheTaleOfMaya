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
import static java.awt.event.KeyEvent.VK_SHIFT;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Timer timer;
    private TileManager tileManager;
    private Player player;
    private Obstacle boulder;
    
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
            
            tileManager = TileManager.get();
            tileManager.loadMap("resources/maps/map.txt");
    
            // Entities
            double mapCenterX = (tileManager.map[0].length/2.0);
            double mapCenterY = (tileManager.map.length/2.0);
            player = Player.get(mapCenterX, mapCenterY);
            boulder = new Obstacle(mapCenterX - 2, mapCenterY,"resources/rock.png");
            
            
            // Keyboard inputs
            KeyHandler keyboard = KeyHandler.get();
            addKeyListener(keyboard);
            setFocusable(true);
            
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
        player.dispose();
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
            int[][] map = TileManager.get().map;
            int tileSize = TileManager.get().getTileSize();
            outerloop:
            for(int row = 0; row < map.length; row++) {
                for(int col = 0; col < map[0].length; col ++) {
                    int x = col * tileSize;
                    int y = row * tileSize;
                    Rectangle r = new Rectangle(x, y, tileSize, tileSize);
                    if(player.isColliding(r) && TileManager.get().getCollidable(row, col)) {
                        player.solveCollision(r);
                        break outerloop; // TODO
                    }
                }
            }
            repaint();
        }
    }
    
    private void keyInput() { //TODO handle this
        if(KeyHandler.isPressed(VK_ESCAPE)) {
            GameWindow.get().dispose();
            MainWindow.switchTo(MenuWindow.get().init());
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileManager.draw(g);
        boulder.draw(g);
        player.draw(g);
        if(player.isColliding(boulder)) {
            player.showBoundary(g, Color.red);
            boulder.showBoundary(g, Color.red);
        } else {
            player.showBoundary(g, Color.green);
            boulder.showBoundary(g, Color.green);
        }
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
        } else if(e.getKeyCode() == VK_ESCAPE && e.getKeyCode() == VK_SHIFT) {
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
