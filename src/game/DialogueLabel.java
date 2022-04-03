package game;

import util.FontUtil;
import util.MainWindow;
import util.ImageUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class DialogueLabel extends JLabel {

    public DialogueLabel(String dialogue) {
        super(dialogue);
        setLocation((int) (MainWindow.getScreenDimension().width / 2.0), 200);
        setSize(500, 200);
        BufferedImage dialogFrame = ImageUtil.getFrom("resources/images/dialogFrame.png");
        this.setIcon(new ImageIcon(dialogFrame));
        setForeground(Color.black);
        setFont(new Font("Serif", Font.PLAIN, 30));
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setFocusable(true);
    }
}