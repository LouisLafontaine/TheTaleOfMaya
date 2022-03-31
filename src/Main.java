import jaco.mp3.player.MP3Player;
import util.MainWindow;
import util.Sound;

/**
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        MP3Player sound = new Sound("resources/sounds/musics/Title Theme.mp3");
        sound.play();
        MainWindow.get().init();
    }
}