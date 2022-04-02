/**
 * This is the main class
 */

import util.MainWindow;
import util.Sound;

public class Main {
    public static void main(String[] args) {
        Sound sound = new Sound("sounds/musics/xDeviruchi/Title Theme.mp3");
        sound.play();
        MainWindow.get().init();
    }
}