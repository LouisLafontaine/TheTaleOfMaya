package LevelEditor;


import javax.swing.*;

public class LevelEditorPanel extends JPanel {
    
    public static LevelEditorPanel instance;
    private boolean init = false;
    
    // World settings
    
    /**
     * Creates a LevelEditorPanel
     */
    private LevelEditorPanel() {
    
    }
    
    /**
     * This method ensures that only one instance the class can be created
     *
     * @return the instance
     */
    public static LevelEditorPanel get() {
        if (instance == null) {
            instance = new LevelEditorPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance
     */
    public LevelEditorPanel init() {
        if (!init) {
            init = true;
            
        } else {
            System.err.println("The LevelEditorPanel instance has already been initialized !");
        }
        return LevelEditorPanel.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
    protected void dispose() {
        instance = null;
    }
}
