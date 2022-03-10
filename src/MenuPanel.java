import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {
    
    public static MenuPanel instance;
    
    private JButton launchGameButton;
    
    /**
     * Creates a MenuPanel
     */
    private MenuPanel() {
    
    }
    
    /**
     * This method ensures that only one instance of the MenuPanel class can be created
     *
     * @return the instance of the MenuPanel class
     */
    public static MenuPanel get() {
        if(instance == null) {
            instance = new MenuPanel();
        }
        return instance;
    }
    
    /**
     * Initializes the instance of the MenuPanel class
     */
    public void init() {
        setLayout(null);
        launchGameButton = new JButton("Launch game");
        launchGameButton.setBounds(getWidth()/2 - 50, getHeight()/2 - 30,100,60);
        launchGameButton.addActionListener(this);
        add(launchGameButton);
        repaint();
    }
    
    /**
     * Draws on the screen
     *
     * @param g  a Graphics object to draw on the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(0,0, getWidth(), getHeight());
    }
    
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == launchGameButton) {
            new GameWindow();
        }
    }
}
