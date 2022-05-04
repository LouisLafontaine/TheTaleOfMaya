/**
 * This class is a panel containing all the game's credits elements
 */

package menu;


import util.MainWindow;
import util.ReturnToMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class CreditPanel extends JPanel {

    public static CreditPanel instance;       // instance of the Menu.CreditPanel class
    private boolean init = false;           // true if the instance has been initialized, false otherwise

    /**
     * Creates a Menu.CreditPanel
     */
    private CreditPanel() {

    }

    /**
     * This method ensures that only one instance of the Menu.CreditPanel class can be created
     *
     * @return the instance of the Menu.CreditPanel class
     */
    public static CreditPanel get() {
        if(instance == null) {
            instance = new CreditPanel();
        }
        return instance;
    }

    /**
     * Initializes the instance of the Menu.CreditPanel class
     */
    public CreditPanel init() {
        if(!init) {
            init = true;
    
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ReturnToMenu");
            getActionMap().put("ReturnToMenu", new ReturnToMenu(CreditWindow.get()));
    
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            JLabel[] labels = new JLabel[10];
            
            labels[0] = new JLabel("Crédits :");
            labels[1] = new JLabel("Ce jeu a été programmé avec amour et tendresse par Louis LAFONTAINE, Mélanie PHE, Hugo SAYSANA.");
            labels[2] = new JLabel("Musiques et bruitages provenant de YouTube : ...");
            labels[3] = new JLabel("Carte du jeu réalisée par nos soins.");
            labels[4] = new JLabel( "Animations des attaques réalisées par nos soins.");
            labels[5] = new JLabel("Dans le cas où votre inquiétude pour Maya et Isaac vous empêcherait de dormir paisiblement,");
            labels[6] = new JLabel("sachez qu'ils sont désormais très heureux et en sécurité sur leur planète d'origine, à l'abri des boids.");
            labels[7] = new JLabel("Spritesheet provenant de Google : ...");
            labels[8] = new JLabel("Aucun villageois n'a été blessé pendant la réalisation du jeu.");
            labels[9] = new JLabel("press ESC to return to the menu");
            System.out.println(labels[0]);
            for(int i = 0; i < labels.length ; i++) {
                labels[i].setForeground(Color.white);
                add(labels[i], gbc);
            }
        } else {
            System.err.println("The Menu.CreditPanel instance has already been initialized !");
        }
        return instance;
    }
    
    protected void dispose() {
        instance = null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
