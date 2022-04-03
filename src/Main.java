/**
 * This is the main class
 */

import util.MainWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println(122 % 20);
//        MP3Player sound = new Sound("resources/sounds/musics/Title Theme.mp3");
//        sound.play();
        MainWindow.get().init();
        
    }
}