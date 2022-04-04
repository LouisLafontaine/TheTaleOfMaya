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
    private HashMap<Integer, Tile> tiles;
    private ArrayList<int[][]> mapLayers;
    private final double scale = 4;
    private int tileRes;
    private int tileSize;
    private Dimension mapDimension;
    private Camera camera;
    private int mapWidth;
    private int mapHeight;
    
    private TileManager() {
    
    }
    
    public static TileManager get() {
        if(instance == null) {
            instance = new TileManager();
        }
        return instance;
    }
    
    public TileManager init(String mapPath, String mapImagePath, Camera camera) {
        if(!init) {
            init = true;
            
            this.camera = camera;
            mapLayers = new ArrayList<>();
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
        for(int[][] m : mapLayers) {
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
    
    /**
     *
     * @param mapPath
     * @param mapImagePath
     */
    public void loadMap(String mapPath, String mapImagePath) {
        BufferedImage mapImage = ImageUtil.getFrom(mapImagePath);
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
            
            mapDimension = new Dimension(mapWidth, mapHeight);
            
//            tileRes = Integer.parseInt(s.substring(i1, i2));
            tileSize = 64;
            
            while(!s.contains("encoding")) {
                s = br.readLine();
            }
            s = br.readLine();
            
            
            int mapCounter = 0;
            while(s != null && !s.contains("</map>")){ // We read all the map data
                mapLayers.add(new int[mapHeight][mapWidth]);
                for(int i=0; i<mapHeight; i++) {
                    String[] numbers = s.split(",");
                    for(int j=0; j<numbers.length; j++) {
                        mapLayers.get(mapCounter)[i][j] = (Integer.parseInt(numbers[j]));
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
            for(int[][] m : mapLayers) { // we repeat for every layer of the map
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
    
    
    public int getTileSize() {
        return tileSize;
    }
    
    public boolean getCollidable(int row, int col) {
        return false; //TODO fix this
    }
    
    public int getMapWidth() {
        return mapWidth;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
}