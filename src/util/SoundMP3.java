package util;

import jaco.mp3.player.MP3Player;

import java.io.File;

public class SoundMP3 {
    
    public static MP3Player getFrom(String filename) {
        MP3Player sound = null;
        try {
            File file = new File(filename);
            sound = new MP3Player(file);
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }
        return sound;
    }
}