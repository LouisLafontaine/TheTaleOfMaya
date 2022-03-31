package game;

import util.Vect;

public class Obstacle extends Entity {

    public Obstacle(double x, double y, String imagePath){
        super(x,y, imagePath);
        int tileSize = GamePanel.get().tileSize;
        screenPos = new Vect(x*tileSize, y*tileSize);

        boundsX1 = (int) (screenPos.x - boundsArea.width / 4.0);
        boundsY1 = (int) (screenPos.y);
        boundsX2 = (int) (screenPos.x + boundsArea.width / 3.0);
        boundsY2 = (int) (screenPos.y + boundsArea.width / 2.0);
    }

}
