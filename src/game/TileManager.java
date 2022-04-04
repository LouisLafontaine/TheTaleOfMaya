package game;

import util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TileManager {
    public static TileManager instance;
    private boolean init = false;
    private final ArrayList<Tile> tiless;
    private ArrayList<int[][]> maps;
    private final int tileRes = 16;
    private double scale = 4;
    private int tileSize = (int) (tileRes * scale);
    private int[][] map;
    private Dimension mapDimension;
    private Camera camera;
    private int mapWidth;
    private int mapHeight;
    BufferedImage mapImage;
    HashMap<Integer, Tile> tiles;
    
    private TileManager() {
        tiless = new ArrayList<>();
    }
    
    public static TileManager get() {
        if(instance == null) {
            instance = new TileManager();
        }
        return instance;
    }
    
    public TileManager init(String mapPath, Camera camera) {
        if(!init) {
            init = true;
            this.camera = camera;
            maps = new ArrayList<>();
            loadMap(mapPath);
            loadMap("resources/maps/world.tmx", "resources/images/worldTiles/world.png");
        } else {
            System.err.println("The TileManager instance has already been initialized !");
        }
        return TileManager.get();
    }
    
    public void dispose() {
        instance = null;
    }
    
    public void draw(Graphics g) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                int worldX = (j * tileSize);
                int worldY = (i * tileSize);
                int screenX = (int) (worldX - camera.getPos().x + camera.getCenter().x);
                int screenY = (int) (worldY -  camera.getPos().y + camera.getCenter().y);
                // if statement to not draw tiles outside the screen
                if((worldX > camera.getPos().x - camera.getCenter().x - tileSize)
                        && (worldX < camera.getPos().x + camera.getCenter().x + tileSize)
                        && (worldY > camera.getPos().y - camera.getCenter().y - tileSize)
                        && (worldY < camera.getPos().y + camera.getCenter().y + tileSize)) {
                    g.drawImage(tiless.get(map[i][j]).image, screenX, screenY, tileSize,tileSize, null);
                }
            }
        }
    }
    
    
    public void draw(Graphics g, boolean t) {
        for(int[][] m : maps) {
            for(int i = 0 ; i < mapHeight ; i++) {
                for(int j = 0 ; j < mapWidth ; j++) {
                    int worldX = (j * tileSize);
                    int worldY = (i * tileSize);
                    int screenX = (int) (worldX - camera.getPos().x + camera.getCenter().x);
                    int screenY = (int) (worldY -  camera.getPos().y + camera.getCenter().y);
                    // if statement to not draw tiles outside the screen
                    if((worldX > camera.getPos().x - camera.getCenter().x - tileSize)
                            && (worldX < camera.getPos().x + camera.getCenter().x + tileSize)
                            && (worldY > camera.getPos().y - camera.getCenter().y - tileSize)
                            && (worldY < camera.getPos().y + camera.getCenter().y + tileSize)) {
                        g.drawImage(tiles.get(m[i][j]).image, screenX, screenY, null);
                    }
                }
            }
        }
    }
    
    private void loadMap(String mapPath) {
        try {
            mapDimension = mapSize(mapPath);
            map = new int[mapDimension.height][mapDimension.width];
            BufferedReader br = new BufferedReader(new FileReader(mapPath));
            String s;
            while(!(s = br.readLine()).equals("--")) {
                String[] tileInfo = s.split(" ");
                tiless.add(new Tile(tileInfo[1], Boolean.parseBoolean(tileInfo[0])));
            }
            s = br.readLine();
            for(int i = 0; i < mapDimension.height ; i++) {
                String[] numbers = s.split(" ");
                for(int j=0 ; j < numbers.length ; j++) {
                    map[i][j] = Integer.parseInt(numbers[j]);
                }
                s = br.readLine();
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("Couldn't load map at \"" + mapPath + "\"");
        }
    }
    
    /**
     *
     * @param mapPath
     * @param mapImagePath
     */
    public void loadMap(String mapPath, String mapImagePath) {
        mapImage = ImageUtil.getFrom(mapImagePath);
        String s = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapPath));
            br.readLine();
            s = br.readLine();
            
            int i1 = s.indexOf("width") + 7;
            int i2 = s.indexOf("\"", i1);
            mapWidth = Integer.parseInt(s.substring(i1, i2));

            i1 = s.indexOf("height=") + 8;
            i2 = s.indexOf("\"", i1);
            mapHeight = Integer.parseInt(s.substring(i1, i2));
            
            i1 = s.indexOf("tilewidth=") + 11;
            i2 = s.indexOf("\"", i1);
            
//            tileRes = Integer.parseInt(s.substring(i1, i2));
            tileSize = 64;
            
            while(!s.contains("encoding")) {
                s = br.readLine();
            }
            s = br.readLine();
            
            
            int mapCounter = 0;
            while(s != null && !s.contains("</map>")){ // We read all the map data
                maps.add(new int[mapHeight][mapWidth]);
                for(int i=0; i<mapHeight; i++) {
                    String[] numbers = s.split(",");
                    for(int j=0; j<numbers.length; j++) {
                        maps.get(mapCounter)[i][j] = (Integer.parseInt(numbers[j]));
                    }
                    s = br.readLine();
                }
                mapCounter++;
                // We go the next map
                while(s != null && !s.contains("encoding")) {
                    s = br.readLine();
                }
                s = br.readLine();
            }
            // We close the reader
            br.close();
            
            // We initialize the tiles hashmap
            tiles = new HashMap<>();
            int mapW = mapImage.getWidth()/16;
            for(int[][] m : maps) { // we repeat for every layer of the map
                for(int i = 0 ; i < m.length ; i++) {
                    for(int j = 0 ; j < m[i].length ; j++) {
                        if(m[i][j] != 0) {
                            int px = (m[i][j] - 1) % mapW;
                            int py = (m[i][j] - 1 ) / mapW;
                            px *= 16;
                            py *= 16;
                            BufferedImage tempImage = mapImage.getSubimage(px, py, 16, 16);
                            BufferedImage resizedImage = new BufferedImage(tileSize, tileSize, tempImage.getType());
                            Graphics g = resizedImage.getGraphics();
                            g.drawImage(tempImage, 0,0, tileSize, tileSize, null);
                            g.dispose();
                            Tile tempTile = new Tile(resizedImage, false); //TODO collisions
                            tiles.put(m[i][j], tempTile);
                        } else {
                            tiles.put(m[i][j], new Tile());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Couldn't load map at \"" + mapPath + "\"");
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
    
    public boolean getCollidable(int row, int col) {
        return tiless.get(map[row][col]).collision;
    }
    
    public Dimension getMapDimension() {
        return mapDimension;
    }
    
    public void zoom(double d) {
        if(scale + d > 0) {
            scale += d;
            tileSize = (int) (scale * tileRes);
        }
    }
    
    public int getMapWidth() {
        return mapWidth;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
}