package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeHandler implements KeyListener {
    
    private static KeHandler instance;
    private static boolean[] keyPressed = new boolean[KeyEvent.KEY_LAST];
    
    private KeHandler() {
    
    }
    
    public static KeHandler get() {
        if(instance == null) {
            instance = new KeHandler();
        }
        return instance;
    }
    
    public static boolean isPressed(int key) {
        return keyPressed[key];
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
        int key = e.getKeyCode();
        keyPressed[key] = true;
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
        int key = e.getKeyCode();
        keyPressed[key] = false;
    }
}
