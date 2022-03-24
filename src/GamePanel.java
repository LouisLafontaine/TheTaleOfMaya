import util.Keyboard;
import util.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Player player;
    private Timer timer;
    private BufferedImage background;
    
    /**
     * Creates a GamePanel
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

            background = Image.getFrom("resources/images/topdown level.png");

            player = new Player(background.getWidth() / 2.0, background.getHeight() / 2.0,"resources/images/character.png");
            
            setFocusable(true);
            Keyboard keyboard = Keyboard.get();
            addKeyListener(keyboard);
            

            GameWindow.get().setSize(background.getWidth(), background.getHeight());
            
            timer = new Timer(33, this);
            timer.start();
            
            init = true;
        } else {
            System.err.println("The GamePanel instance has already been initialized !");
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
//        g.setColor(Color.black);
//        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(background,0,0,null);
        player.draw(g);
    }
}
