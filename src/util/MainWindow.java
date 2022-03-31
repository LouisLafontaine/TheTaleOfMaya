package util;

import menu.MenuWindow;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    
    public static MainWindow instance;
    private static GraphicsDevice gd;
    
    private MainWindow() {
    
    }
    
    public void init() {
        // Window settings -----------------------------------------------------------------------------------------
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            switchTo(MenuWindow.get().init());
        } else {
            System.err.println("Full screen not supported !\nPlease try to run our game on an another screen or computer");
            System.exit(0);
        }
        if(gd.isDisplayChangeSupported()) {
            try {
                DisplayMode dm = gd.getDisplayMode();
                gd.setDisplayMode(new DisplayMode(1920,1080,dm.getBitDepth(),dm.getRefreshRate()));
            } catch (Exception e) {
                System.err.println("Warning 1920 x 1080 not supported by your screen, there might be displaying issues\nDisplay mode set to " + gd.getDisplayMode());
            }
        }
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static MainWindow get() {
        if(instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }
    
    public static void switchTo(JFrame f) {
        gd.setFullScreenWindow(f);
    }
    
    public static Dimension getScreenDimension() {
        return new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    }
}
