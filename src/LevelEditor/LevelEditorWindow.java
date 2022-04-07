package LevelEditor;

import javax.swing.*;

public class LevelEditorWindow extends JFrame{
    
    public static LevelEditorWindow instance;
    private boolean init = false;
    
    /**
     * Creates a LevelEditorWindow
     */
    private LevelEditorWindow() {
        super();
    }
    
    /**
     * This method ensures that only one instance of the LevelEditorWindow class can be created
     *
     * @return the instance of the LevelEditorWindow class
     */
    public static LevelEditorWindow get() {
        if(instance == null) {
            instance = new LevelEditorWindow();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the LevelEditorWindow class
     */
    public LevelEditorWindow init() {
        if(!init) {
            init = true;
            
            // Window settings -----------------------------------------------------------------------------------------
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            // Game panel --------------------------------------------------------------------------------------------------
            add(LevelEditorPanel.get().init());
            
        } else {
            System.err.println("The LevelEditorWindow instance has already been initialized !");
        }
        return LevelEditorWindow.get();
    }
    
    /**
     * Resets the instance and sets init to false
     */
    @Override
    public void dispose() {
        super.dispose();
        instance = null;
        LevelEditorPanel.get().dispose();
    }
}
