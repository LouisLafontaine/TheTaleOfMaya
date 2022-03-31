package game;

import util.ImageUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class Animation implements ActionListener {
    BufferedImage spriteSheet;
    Timer timer;
    int frameRate;
    int spriteWidth;
    int spriteHeight;
    int nOfFrames;
    int pointer;
    
    public Animation(String filename, int spriteWidth, int spriteHeight, int nOfFrames, int row, int frameRate) {
        BufferedImage temp = ImageUtil.getFrom(filename);
        this.spriteSheet = temp.getSubimage(0, (row-1) * spriteHeight, nOfFrames * spriteWidth, spriteHeight);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.nOfFrames = nOfFrames;
        this.frameRate = frameRate;
        this.timer = new Timer(1000/frameRate, this);
        timer.start();
    }
    
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        pointer += 1;
        pointer %= nOfFrames;
    }
    
    
    public BufferedImage getCurrentFrame(){
        return spriteSheet.getSubimage(pointer * spriteWidth, 0, spriteWidth, spriteHeight);
    }
    
    public void setFrame(int i) {
        if(i < nOfFrames - 1) {
            pointer = i * spriteWidth;
        } else {
            System.err.println("cannot set frame");
        }
    }
}