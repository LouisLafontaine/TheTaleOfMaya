package game;

import util.KeHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private TileManager tileManager;
    private Player player;
    private Timer timer;
    
    // Screen settings
    public final static int tileRes = 16;
    public final static int scale = 16;
    public final static int tileSize = tileRes * scale;
    
    
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
    public void init() {
        if (!init) {
            init = true;
            
            tileManager = TileManager.get();
            tileManager.loadMap("resources/maps/map.txt");
    
            // Entities displayed
            double x = (tileManager.map[0].length/2.0) * tileManager.tileSize;
            double y = (tileManager.map.length/2.0) * tileManager.tileSize;
            player = Player.get(x, y);
            

            // Keyboard inputs
            KeHandler keyboard = KeHandler.get();
            addKeyListener(keyboard);
            setFocusable(true);
            
            timer = new Timer(33, this);
            timer.start();
        } else {
            System.err.println("The Game.GamePanel instance has already been initialized !");
        }
    }
    
    /**
     * Resets the instance and sets init to false
     */
    protected void reset() {
        if (init) {
            instance = null;
            init = false;
        }
    }
    
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            player.update();
            repaint();
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
//        g.drawImage(background,0,0, getWidth(), getHeight(), null);
//        boulder.draw(g);
        tileManager.draw(g2);
        player.draw(g);
    }
}
