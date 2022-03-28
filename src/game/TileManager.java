package game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileManager {
    public static TileManager instance;
    ArrayList<Tile> tiles;
    int tileSize = 100;
    int[][] map = new int[4][4];
    int maxScreenRow;
    int maxScreenCol;
    
    private TileManager() {
        Tile tile1 = new Tile("resources/images/worldTiles/sand.png", false);
        Tile tile2 = new Tile("resources/images/worldTiles/water.png", false);
        
        tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        
        maxScreenRow = 20;
        maxScreenCol = 30;
    }
    
    public static TileManager get() {
        if(instance == null) {
            instance = new TileManager();
        }
        return instance;
    }
    
    public void draw(Graphics2D g) {
        int rows = GamePanel.get().getWidth() / tileSize;
        int columns = GamePanel.get().getHeight() / tileSize;
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] == 0) {
                    g.drawImage(tiles.get(0).image, i*tileSize, j*tileSize, tileSize,tileSize, null);
                } else {
                    g.drawImage(tiles.get(1).image, i*tileSize, j*tileSize, tileSize,tileSize, null);
                }
            }
        }
    }
    
    public void loadMap() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/maps/map.txt"));
            String s = br.readLine();
            for(int i=0; i<4; i++) {
                String[] numbers = s.split(" ");
                for(int j=0; j<4; j++) {
                    map[i][j] = Integer.parseInt(numbers[j]);
                }
                s = br.readLine();
            }
            
            br.close();
    
            for(int i=0; i<map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.print("\n");
            }
            
        } catch(Exception e) {
            System.err.println("couldn't load map");
        }
       
    }
}
