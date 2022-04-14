/**
 * This class is a panel containing all the game's menu elements
 */

package menu;

import boid.BoidWindow;
import util.Animation;
import game.GameWindow;
import game.mapEditor.MapEditorWindow;
import jaco.mp3.player.MP3Player;
import util.FontUtil;
import util.MainWindow;
import util.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class MenuPanel extends JPanel implements ActionListener {
    
    public static MenuPanel instance;           // instance of the Menu.MenuPanel class
    private boolean init = false;               // true if the instance has been initialized, false otherwise
    private Timer timer;
    private HashMap<String, JButton> buttons;
    private Animation background;

    MP3Player MainMenuTheme = new Sound("resources/sounds/musics/Title Theme.mp3"); // background music

    /**
     * Creates a Menu.MenuPanel
     */
    private MenuPanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the Menu.MenuPanel class can be created
     *
     * @return the instance of the Menu.MenuPanel class
     */
    public static MenuPanel get() {
        if (instance == null) {
            instance = new MenuPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the Menu.MenuPanel class
     */
    public MenuPanel init() {
        if (!init) {
            init = true;
    
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ReturnToMenu");
            getActionMap().put("ReturnToMenu", new ExitGame());
            
            background = new Animation("images/menuImages/cherryBlossom.png", 576, 324, 128, 1, 12);
            timer = new Timer(1000 / 60, this);


            MainMenuTheme.play();

            // Setting Layout
            // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
            
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
    
            buttons = new HashMap<>();
            String[] buttonNames = new String[]{"Launch game", "Launch boid", "Map editor", "Credits", "Exit menu"};
    
            Color pink = new Color(240, 98, 146);
            
            for (String s : buttonNames) {
                JButton b = new JButton(s);
                b.setFont(FontUtil.getFrom("fonts/pixelFont.otf", 50));
                b.setMargin(new Insets(5, 0, 7, 0));
                b.setForeground(pink);
                b.addActionListener(this);
                add(b, gbc);
                buttons.put(s, b);
            }
            
            JLabel exitLabel = new JLabel("Press ESC to quit");
            exitLabel.setFont(FontUtil.getFrom("fonts/pixelFont.otf", 40));
            exitLabel.setForeground(pink);
            add(exitLabel, gbc);
            
            timer.start();
        } else {
            System.err.println("The Menu.MenuPanel instance has already been initialized !");
        }
        return MenuPanel.get();
    }
    
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
        Object source = e.getSource();
        if (source == timer) {
            repaint();
        }
        if (source == buttons.get("Launch boid")) {
            MainMenuTheme.stop();
            MenuWindow.get().dispose();
            MainWindow.switchTo(BoidWindow.get().init());
        } else if (source == buttons.get("Launch game")) {
            MainMenuTheme.stop();
            MenuWindow.get().dispose();
            MainWindow.switchTo(GameWindow.get().init());
        } else if (source == buttons.get("Credits")) {
            MainMenuTheme.stop();
            MenuWindow.get().dispose();
            MainWindow.switchTo(CreditWindow.get().init());
        } else if(source == buttons.get("Map editor")) {
            MainMenuTheme.stop();
            MenuWindow.get().dispose();
            MainWindow.switchTo(MapEditorWindow.get().init());
        } else if (source == buttons.get("Exit menu")) {
            System.exit(0);
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getCurrentFrame(), 0, 0, getWidth(), getHeight(), null);
    }
    
    
    private class ExitGame extends AbstractAction {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Thanks for playing !");
            System.exit(0);
        }
    }
}