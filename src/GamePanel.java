import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {
    
    LinkedList<Boid> boids;
    Timer timer;
    
    public GamePanel() {
        
        boids = new LinkedList<>();
        int numberOfBoids = 20;
        
        // Making numberOfBoids boids with random speeds and accelerations between -1 and 1
        for (int i = 0; i < numberOfBoids; i++) {
            Vector2D randomVelocity = new Vector2D(Math.random()*2-1, Math.random()*2-1);
            Vector2D randomAcceleration = new Vector2D(Math.random()*2-1, Math.random()*2-1);
            boids.add(new Boid(300,300,randomVelocity, randomAcceleration));
        }
        
        
        timer = new Timer(33, this);
        timer.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Background
        g.setColor(Color.black);
        g.fillRect(0, 0,getWidth(), getHeight());
        
        // Boids
        for(Boid b : boids) {
            b.draw(g);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for(Boid b : boids) {
            b.update();
        }
        repaint();
    }
}
