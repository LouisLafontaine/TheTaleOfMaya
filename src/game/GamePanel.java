package game;

import util.KeHandler;
import util.Img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private TileManager tileManager;
    private Player player;
    private Timer timer;
    private BufferedImage background;
    private Rock boulder;
    
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
            
            // Background
            background = Img.load("resources/images/topdown level.png");
            
            // Entities displayed
            player = new Player(GameWindow.get().getWidth() / 2.0, GameWindow.get().getHeight() / 2.0);
//            boulder = new Rock(200,200,"resources/images/rock.png");
            
            tileManager = TileManager.get();
            tileManager.loadMap();
            

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
