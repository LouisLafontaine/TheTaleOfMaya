package game;

import menu.MenuWindow;
import util.KeyHandler;
import util.MainWindow;
import util.ReturnToMenu;
import util.Vect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_ESCAPE;


public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Timer timer;
    private TileManager tileManager;
    private Player player;
    private Camera camera;
    private ArrayList<Entity> entities;
    
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
    
            // Camera
            Dimension screenSize = MainWindow.getScreenDimension();
            Vect center = new Vect(screenSize.width/2.0, screenSize.getHeight()/2.0);
            camera = new Camera(center);
    
            // World
            tileManager = TileManager.get().init("resources/maps/map.txt", camera);
    
            // Entities
            Dimension mapDimension = tileManager.getMapDimension();
            double mapCenterX = (mapDimension.height/2.0);
            double mapCenterY = (mapDimension.width/2.0);
            player = Player.get().init(mapCenterX, mapCenterY);
            
            entities = new ArrayList<>();

            Obstacle boulder = new Obstacle(mapCenterX - 2, mapCenterY,"resources/rock.png");
            entities.add(boulder);

            NPC npc = new NPC(mapCenterX - 3, mapCenterY, "resources/npc.png", "bonjour");
            entities.add(npc);
            
            // Keyboard inputs
            KeyHandler keyboard = KeyHandler.get();
            addKeyListener(keyboard);
            setFocusable(true);
            requestFocus();
    
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ReturnToMenu");
            getActionMap().put("ReturnToMenu", new ReturnToMenu(GameWindow.get()));
            
            
            timer = new Timer(33, this);
            timer.start();
        } else {
            System.err.println("The Game. GamePanel instance has already been initialized !");
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
        tileManager.dispose();
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
            
            // Checking collisions with map
            Dimension mapDimension = tileManager.getMapDimension();
            int tileSize = tileManager.getTileSize();
//            outerloop:
//            for(int row = 0; row < mapDimension.height; row++) {
//                for(int col = 0; col < mapDimension.width; col ++) {
//                    int x = col * tileSize;
//                    int y = row * tileSize;
//                    Rectangle r = new Rectangle(x, y, tileSize, tileSize);
//                    if(player.isColliding(r) && tileManager.getCollidable(row, col)) {
//                        player.solveCollision(r);
//                        break outerloop; // TODO
//                    }
//                }
//            }
            // Checking collisions with entities
            for(Entity en : entities) {
                if(player.isColliding(en)) {
                    player.solveCollision(en);
                    if(en instanceof NPC) {
                        NPC npc = (NPC) en;
                        add(npc.dialogueLabel);
                    }
                }
            }
            camera.follow(player);
            repaint();
        }
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Drawing the map
        tileManager.draw(g, true);
        // Drawing the entities
        for(Entity e : entities) {
            e.draw(g, camera);
        }
        // Drawing the player
        player.draw(g, camera);
    }
}
