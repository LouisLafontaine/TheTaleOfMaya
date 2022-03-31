package game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileManager {
    public static TileManager instance;
    private final ArrayList<Tile> tiles;
    private final int tileRes = 32;
    private final int scale = 3;
    private final int tileSize = tileRes * scale;
    int[][] map;
    
    private TileManager() {
        tiles = new ArrayList<>();
    }
    
    public static TileManager get() {
        if(instance == null) {
            instance = new TileManager();
        }
        return instance;
    }
    
    public void draw(Graphics g) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                int worldX = (j * tileSize);
                int worldY = (i * tileSize);
                int screenX = (int) (worldX - Player.get().pos.x + Player.get().screenPos.x);
                int screenY = (int) (worldY -  Player.get().pos.y + Player.get().screenPos.y);
                // if statement to not draw tiles outside the screen
                if((worldX > Player.get().pos.x - Player.get().screenPos.x - tileSize)
                        && (worldX < Player.get().pos.x + Player.get().screenPos.x + tileSize)
                        && (worldY > Player.get().pos.y - Player.get().screenPos.y - tileSize)
                        && (worldY < Player.get().pos.y + Player.get().screenPos.y + tileSize)) {
                    g.drawImage(tiles.get(map[i][j]).image, screenX, screenY, tileSize,tileSize, null);
                }
            }
        }
    }
    
    public void loadMap(String mapPath) {
        try {
            Dimension mapSize = mapSize(mapPath);
            map = new int[mapSize.height][mapSize.width];
            BufferedReader br = new BufferedReader(new FileReader(mapPath));
            String s;
            while(!(s = br.readLine()).equals("--")) {
                String[] tileInfo = s.split(" ");
                tiles.add(new Tile(tileInfo[1], Boolean.parseBoolean(tileInfo[0])));
            }
            s = br.readLine();
            for(int i = 0; i < mapSize.height ; i++) {
                String[] numbers = s.split(" ");
                for(int j=0 ; j < numbers.length ; j++) {
                    map[i][j] = Integer.parseInt(numbers[j]);
                }
                s = br.readLine();
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("Couldn't getFrom map at \"" + mapPath + "\"");
        }
    }
    
    public Dimension mapSize(String mapPath) {
        int mapWidth = 0;
        int mapHeight = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapPath));
            String s = br.readLine();
            while(!(s.equals("--"))) {
                s = br.readLine();
            }
            s = br.readLine();
            for (int i = 0 ; i < s.length() - 1 ; i++) {
                if((s.charAt(i) == ' ') && (s.charAt(i+1) != '\n')) mapWidth++;
            }
            mapWidth += 1; // there is n+1 tiles for n spaces
            
            mapHeight = 1; // 1 because we already called readLine() once to calculate the map width
            while (br.readLine() != null) mapHeight++;
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Couldn't determine map size");
        }
        if(mapWidth == 0 && mapHeight == 0) System.err.println("the map at : \"" + mapPath + "\" is empty !");
        return new Dimension(mapWidth, mapHeight);
    }
    
    public int getTileSize() {
        return tileSize;
    }
}