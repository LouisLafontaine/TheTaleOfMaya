package game;

import util.KeyHandler;
import util.MainWindow;
import util.ReturnToMenu;
import util.Vect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Timer timer;
    private TileManager tileManager;
    private Player player;
    private Camera camera;
    private ArrayList<Entity> entities;
    
    
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
            tileManager = TileManager.get().init("resources/maps/world.tmx", "resources/images/worldTiles/world.png", camera);
    
            // Entities
            double mapCenterX = (tileManager.getMapWidth()/2.0);
            double mapCenterY = (tileManager.getMapHeight()/2.0);
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
            int tileSize = tileManager.getTileSize();
            outerloop:
            for(int row = 0; row < tileManager.getMapHeight(); row++) {
                for(int col = 0; col < tileManager.getMapWidth(); col ++) {
                    int x = col * tileSize;
                    int y = row * tileSize;
                    Rectangle r = new Rectangle(x, y, tileSize, tileSize);
                    if(player.isColliding(r) && tileManager.getCollidable(row, col)) {
                        player.solveCollision(r);
                        break outerloop; // TODO
                    }
                }
            }
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
        tileManager.draw(g);
        // Drawing the entities
        for(Entity e : entities) {
            e.draw(g, camera);
        }
        // Drawing the player
        player.draw(g, camera);
        player.showBoundary(g, camera, Color.red);
        
        // show tile collision //TODO move this to TileManager
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2));
        for(int i = 0 ; i < tileManager.collisionMap.length; i++) {
            for(int j = 0 ; j < tileManager.collisionMap[i].length ; j ++) {
                int tileSize = tileManager.getTileSize();
                int worldX = (j * tileSize);
                int worldY = (i * tileSize);
                int screenX = (int) (worldX - camera.getPos().x + camera.getCenter().x);
                int screenY = (int) (worldY -  camera.getPos().y + camera.getCenter().y);
                if(tileManager.collisionMap[i][j]) {
                    g.setColor(Color.red);
                    g.drawRect(screenX, screenY, tileSize, tileSize);
                }
            }
        }
        g2d.setStroke(old);
    }
}
