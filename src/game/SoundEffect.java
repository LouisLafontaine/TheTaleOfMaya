package game;
import util.Sound;

public class SoundEffect{
    Sound[] soundList = new Sound[10];

    public SoundEffect(){
        soundList[0] = new Sound("resources/sounds/ambientSound/Collision.mp3");
    }
}
