import menu.MenuWindow;
import jaco.mp3.player.MP3Player;
import util.Sound;

/**
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        MenuWindow menuWindow = MenuWindow.get();
        menuWindow.init();
        MP3Player sound = new Sound("resources/sounds/Fantasy8Bit.mp3");
        sound.play();
    }
}