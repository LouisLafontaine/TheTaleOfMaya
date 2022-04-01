package game.mapEditor;

import javax.swing.*;

public class MapEditorWindow extends JFrame {
    
    public static MapEditorWindow instance;
    private boolean init = false;
    private MapEditorPanel mapEditorPanel;
    
    /**
     * Creates a MapEditorWindow
     */
    private MapEditorWindow() {
        super();
    }
    
    /**
     * This method ensures that only one instance of the MapEditorWindow class can be created
     *
     * @return the instance of the MapEditorWindow class
     */
    public static MapEditorWindow get() {
        if(instance == null) {
            instance = new MapEditorWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the MapEditorWindow class
     */
    public MapEditorWindow init() {
        if(!init) {
            init = true;
            
            // Window settings -----------------------------------------------------------------------------------------
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            // Game panel --------------------------------------------------------------------------------------------------
            mapEditorPanel = MapEditorPanel.get().init();
            add(mapEditorPanel);
            
        } else {
            System.err.println("The MapEditorWindow instance has already been initialized !");
        }
        return MapEditorWindow.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
    @Override
    public void dispose() {
        super.dispose();
        instance = null;
        mapEditorPanel.dispose();
    }
}
