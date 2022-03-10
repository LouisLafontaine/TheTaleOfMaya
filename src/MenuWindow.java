import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame implements ActionListener {
    GameWindow gameWindow;

    /**
     *
     */
    public MenuWindow() {
        // Window settings
        setLocation(200,200);
        setSize(1000,1000);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
