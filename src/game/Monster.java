package game;

public class Monster extends Entity{
    public int health;

    public Monster(double x, double y, String imagePath) {
        super(x, y, imagePath);
        this.health = 3;
    }
}
