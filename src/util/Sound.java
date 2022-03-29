/**
 * Users guide (from the library website : http://jacomp3player.sourceforge.net/guide.html
 * Complete doc : http://jacomp3player.sourceforge.net/guide/javadocs/jaco/mp3/player/MP3Player.html
 */

package util;

import jaco.mp3.player.MP3Player;
import java.io.File;

public class Sound extends MP3Player {
    
    public Sound(String soundPath) {
        try {
            File file = new File(soundPath);
            addToPlayList(file);
        } catch(Exception e) {
            System.err.print(e.getMessage());
        }
    }
}