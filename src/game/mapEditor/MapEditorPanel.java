package game.mapEditor;

import game.Camera;
import game.TileManager;
import menu.MenuWindow;
import util.MainWindow;
import util.Vect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class MapEditorPanel extends JPanel implements ActionListener, KeyListener {
    
    public static MapEditorPanel instance;
    private boolean init = false;
    
    Timer timer;
    TileManager tileManager;
    Camera camera;
    
    
    // World settings
    
    /**
     * Creates a MapEditorPanel
     */
    private MapEditorPanel() {
    
    }
    
    /**
     * This method ensures that only one instance the class can be created
     *
     * @return the instance
     */
    public static MapEditorPanel get() {
        if (instance == null) {
            instance = new MapEditorPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance
     */
    public MapEditorPanel init() {
        if (!init) {
            init = true;
            
            // Camera
            Dimension screenSize = MainWindow.getScreenDimension();
            int tileSize = TileManager.get().getTileSize();
            Vect center = new Vect(screenSize.width/2.0 - tileSize/2.0, screenSize.getHeight()/2.0 - tileSize/2.0);
            camera = new Camera(center);
    
            // World
            tileManager = TileManager.get().init("resources/maps/world.tmx", "resources/images/worldTiles/world.png", camera);
            
            addKeyListener(this);
            setFocusable(true);
            
            timer = new Timer(1000/60, this);
            timer.start();
            
        } else {
            System.err.println("The MapEditorPanel instance has already been initialized !");
        }
        return MapEditorPanel.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
    protected void dispose() {
        instance = null;
        tileManager.dispose();
    }
    
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer) {
            repaint();
        }
    }
    
    public void paintComponent(Graphics g) {
        tileManager.draw(g);
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
        int key = e.getKeyCode();
        if(key == VK_UP) {
            camera.move(0,-10);
        } else if(key == VK_DOWN) {
            camera.move(0,10);
        } else if(key == VK_RIGHT) {
            camera.move(10,0);
        } else if(key == VK_LEFT) {
            camera.move(-10, 0);
        }
        
        if(key == VK_ESCAPE) {
            MapEditorWindow.get().dispose();
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
