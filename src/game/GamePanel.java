package game;

import jaco.mp3.player.MP3Player;
import menu.MenuWindow;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class GamePanel<collisionSound> extends JPanel implements ActionListener, KeyListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Timer timer;
    private TileManager tileManager;
    private Player player;
    private Camera camera;
    private ArrayList<Entity> entities;
    private int tileSize;
    private int playingState = 0; // State of the game, determines whether the player is talking to an NPC or not
    private int talkingState = 1;
    public String speaking = ""; // String of the speaking NPC
    private NPC talkingNPC = null; // The NPC that is talking

    MP3Player BgMusic = new Sound("resources/sounds/musics/ritovillage.mp3"); // Background music

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

            // Music
            BgMusic.play();

            // Camera
            Dimension screenSize = MainWindow.getScreenDimension();
            tileSize = TileManager.get().getTileSize();
            Vect center = new Vect(screenSize.width/2.0 - tileSize/2.0, screenSize.getHeight()/2.0 - tileSize/2.0);
            camera = new Camera(center);
    
            // World
            tileManager = TileManager.get().init("maps/map.txt", camera);
    
            // Entities
            Dimension mapDimension = tileManager.getMapDimension();
            double mapCenterX = (mapDimension.height/2.0);
            double mapCenterY = (mapDimension.width/2.0);
            player = Player.get().init(mapCenterX, mapCenterY);
            
            entities = new ArrayList<>();
          
            Obstacle boulder = new Obstacle(mapCenterX - 2, mapCenterY,"resources/rock.png");
            entities.add(boulder);

            NPC npc = new NPC(mapCenterX - 3, mapCenterY, "resources/npc.png");
            entities.add(npc);
            
            // Keyboard inputs
            KeyHandler keyboard = KeyHandler.get();
            addKeyListener(keyboard);
            setFocusable(true);
            
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
            keyInput(); // listen for key input

            if(player.state == playingState) {
                player.update();

                // Checking collisions with map
                Dimension mapDimension = tileManager.getMapDimension();
                int tileSize = tileManager.getTileSize();
                outerloop:
                for (int row = 0; row < mapDimension.height; row++) {
                    for (int col = 0; col < mapDimension.width; col++) {
                        int x = col * tileSize;
                        int y = row * tileSize;
                        Rectangle r = new Rectangle(x, y, tileSize, tileSize);
                        if (player.isColliding(r) && tileManager.getCollidable(row, col)) {
                            player.solveCollision(r);
                            break outerloop; // TODO
                        }
                    }
                }
                // Checking collisions with entities
                for (Entity en : entities) {
                    if (player.isColliding(en)) {
                        if (en instanceof NPC) {
                            talkingNPC = (NPC) en;
                            speaking = talkingNPC.speak(); // makes the NPC speak upon collision between the player and the player
                            player.state = talkingState; // switching to talking state
                        }
                        player.solveCollision(en);
                    }
                }
                camera.follow(player);
                repaint();
            }
        else if(player.state == talkingState){
                repaint();
            }
        }
    }
    
    private void keyInput() { //TODO handle this
        if(player.state == talkingState) {
            if(((KeyHandler.isPressed(VK_S) || KeyHandler.isPressed(VK_D) || KeyHandler.isPressed(VK_A) || KeyHandler.isPressed(VK_DOWN) || KeyHandler.isPressed(VK_RIGHT) || KeyHandler.isPressed(VK_LEFT)) && player.lastMovement == 0)
            || (KeyHandler.isPressed(VK_W) || KeyHandler.isPressed(VK_D) || KeyHandler.isPressed(VK_A) || KeyHandler.isPressed(VK_UP) || KeyHandler.isPressed(VK_RIGHT) || KeyHandler.isPressed(VK_LEFT)) && player.lastMovement == 1
            || (KeyHandler.isPressed(VK_S) || KeyHandler.isPressed(VK_W) || KeyHandler.isPressed(VK_A) || KeyHandler.isPressed(VK_DOWN) || KeyHandler.isPressed(VK_UP) || KeyHandler.isPressed(VK_LEFT)) && player.lastMovement == 2
            || (KeyHandler.isPressed(VK_S) || KeyHandler.isPressed(VK_D) || KeyHandler.isPressed(VK_W) || KeyHandler.isPressed(VK_DOWN) || KeyHandler.isPressed(VK_RIGHT) || KeyHandler.isPressed(VK_UP)) && player.lastMovement == 3){
                if(talkingNPC.dialogueNum < talkingNPC.dialogues.size()-1){
                    talkingNPC.dialogueNum++; // going to the next dialogue
                }
                player.state = playingState; // switching back to playing state when moving away from NPC, with the movement towards the NPC blocked
            }
        }
        if(KeyHandler.isPressed(VK_ESCAPE)) {
            BgMusic.stop();
            GameWindow.get().dispose();
            MainWindow.switchTo(MenuWindow.get().init());
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Drawing the map
        tileManager.draw(g);

        // Drawing the entities
        for (Entity e : entities) {
            e.draw(g, camera);
        }
        // Drawing the player
        player.draw(g, camera);
        player.showBoundary(g, camera, Color.green);

        if(player.state == talkingState){
            int x = tileSize*2;
            int y = tileSize;
            int width = tileSize*18 ;
            int height = tileSize*3;
            Color c = new Color(64,108,228,175);
            g.setColor(c);
            g.fillRoundRect(x,y,width,height,35,35); // dialogue frame

            Color c2 = new Color(255,255,255);
            g.setColor(c2);
            g.drawRoundRect(x,y,width,height,35,35); // frame border

            x += tileSize;
            y += tileSize;
            FontUtil font;
            // g.setFont(FontUtil.getFrom("resources/fonts/pixelFont.otf",30));
            g.setFont(g.getFont().deriveFont(Font.PLAIN,30));
            for(String line : speaking.split("\n")){
                g.drawString(line,x,y); // text on dialogue frame
                y += 40;
            }
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
            BgMusic.stop();
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
