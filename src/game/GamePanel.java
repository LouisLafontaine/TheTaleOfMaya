package game;

import jaco.mp3.player.MP3Player;
import menu.MenuWindow;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class GamePanel extends JPanel implements ActionListener {
    
    public static GamePanel instance;
    private boolean init = false;
    private Timer timer;
    private TileManager tileManager;
    private Player player;
    private Camera camera;
    private ArrayList<Entity> entities;
    private final int playingState = 0; // State of the game, determines whether the player is talking to an NPC or not
    private final int talkingState = 1;
    private NPC talkingNPC = null; // The NPC that is currently talking
    private MP3Player[] sounds = new MP3Player[5];
    private gui gui;

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

            // GUI
            gui = new gui();

            // Music
            BgMusic.play();

            // Sounds
            sounds[0] = new Sound("resources/sounds/ambientSound/collision.mp3");

            // Camera
            Dimension screenSize = MainWindow.getScreenDimension();
            Vect center = new Vect(screenSize.width/2.0, screenSize.getHeight()/2.0);
            camera = new Camera(center);
    
            // World
            tileManager = TileManager.get().init("resources/maps/myMap.tmx", "resources/images/worldTiles/world.png", camera);
    
            // Entities
            double mapCenterX = (tileManager.getMapWidth()/2.0);
            double mapCenterY = (tileManager.getMapHeight()/2.0);
            player = Player.get().init(mapCenterX, mapCenterY);
            
            entities = new ArrayList<>();
          
            Obstacle boulder = new Obstacle(mapCenterX - 2, mapCenterY,"resources/images/rock.png");
            entities.add(boulder);

            NPC Darunia = new NPC(mapCenterX - 3, mapCenterY, "resources/images/npc.png", "Darunia Reyfiel");
            entities.add(Darunia);
            Darunia.dialogues.add("Oh Maya, te voilà enfin ! Ton frère Isaac s'est fait enlever par l'horrible Barrish \npendant la nuit. Il détient ton frère dans son antre secrète, et prévoit de lui \nvoler tout son élixir de jouvence. Tu dois aller le sauver !");
            Darunia.dialogues.add("Maya, prépare-toi à combattre les sbires de Barrish. Ce sont des entités \nredoutables qui se déplacent en groupe, comme des oiseaux migrateurs.\nUtilise la touche ESPACE pour les attaquer !");
            Darunia.dialogues.add("Moi? Une pierre qui parle dis-tu? Je vais t'épargner toute mon histoire. \nDans tous les cas, tu n'auras pas le temps de m'écouter.");
            Darunia.dialogues.add("Je n'ai plus rien à t'apprendre. Va! Ton frère t'attend!");



            // Keyboard inputs
            KeyHandler keyboard = KeyHandler.get();
            addKeyListener(keyboard);
            setFocusable(true);
    
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ReturnToMenu");
            getActionMap().put("ReturnToMenu", new ReturnToMenu(GameWindow.get()));
            
            // Game loop timer
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
                for (Entity en : entities) {
                    if (player.isColliding(en)) {
                        sounds[0].play();
                        if (en instanceof NPC) {
                            talkingNPC = (NPC) en;
                            player.isCollidingWithNPC = true;
                            if(player.hasTalked){
                                talkingNPC.nextDialogue();
                                player.hasTalked = false;
                            }
                            talkingNPC.loadDialogue();
                        }
                        player.solveCollision(en);
                    }
//                    if(player.isInRange(en)){
//                        if(en instanceof Monster){
//                            player.readyToAttack = true;
//                            if(player.isAttacking){
//                                player.attack((Monster) en);
//                            }
//                        }
//                    }
                    else {
                        player.isCollidingWithNPC = false;
                        player.readyToAttack = false;
                    }
                }
                camera.follow(player);
                repaint();
            }
        else if(player.state == talkingState){
                player.updateDuringCollision();
                repaint();
            }
        }
    }
    
    private void keyInput() { //TODO handle this
        if(KeyHandler.isPressed(VK_E)){
            gui.display = false;
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
        if(gui.display) {
            gui.draw(g, tileManager);
        }
        // Drawing the entities
        for (Entity e : entities) {
            e.draw(g, camera);
//            if(e instanceof Monster){
//                ((Monster) e).drawHP(g);
//            }
            player.showRange(g, camera, Color.blue, e);
        }

        // Drawing the player
        player.draw(g, camera);
        player.showBoundary(g, camera, Color.green);



        if(player.state == talkingState){
            talkingNPC.drawDialogueBox(g, tileManager);
        }
    
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
