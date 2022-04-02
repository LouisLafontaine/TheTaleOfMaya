package game;

import util.FontUtil;
import util.MainWindow;

import javax.swing.*;
import java.awt.*;


public class DialogueLabel extends JLabel {
    
    public DialogueLabel(String dialogue){
        super(dialogue);
        setLocation((int) (MainWindow.getScreenDimension().width /2.0), 200);
        setSize(400,100);
        setForeground(Color.red);
        setFont(new Font("Serif", Font.PLAIN, 30));
    }
}
