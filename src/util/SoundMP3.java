package util;

import jaco.mp3.player.MP3Player;

import java.io.File;

public class SoundMP3 {
    MP3Player sound;
    
    public SoundMP3(String filename) {
        try {
            File file = new File(filename);
            sound = new MP3Player(file);
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }
    }
    
    public void play() {
        sound.play();
    }
    
    public void pause() {
        sound.pause();
    }
    
    
}
