import util.SoundMP3;

/**
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        MenuWindow menuWindow = MenuWindow.get();
        menuWindow.init();
        SoundMP3 sound = new SoundMP3("resources/sound/Fantasy8Bit.mp3");
        sound.play();
    }
}