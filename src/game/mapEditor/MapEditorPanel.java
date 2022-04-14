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


public class MapEditorPanel extends JPanel {
    
    public static MapEditorPanel instance;
    private boolean init = false;
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
    }
}
