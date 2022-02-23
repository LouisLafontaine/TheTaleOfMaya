import javax.swing.*;

public class GameWindow extends JFrame {
    
    public GameWindow() {
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
    
        setLocation(200, 200);
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
