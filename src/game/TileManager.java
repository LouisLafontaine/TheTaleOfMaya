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
    private HashMap<Integer, BufferedImage> tiles;
    private ArrayList<int[][]> mapLayers;
    boolean[][] collisionMap;
    private Camera camera;
    private final double SCALE = 4;
    private int tileSize;
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
            loadMap(mapPath, mapImagePath);
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
                        g.drawImage(tiles.get(m[i][j]), screenX, screenY, null);
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
        String s;
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapPath));
            br.readLine(); // skipping first line
            s = br.readLine();
            // Reading map width
            String w = "width=\"";
            int i1 = s.indexOf(w) + w.length();
            int i2 = s.indexOf("\"", i1);
            mapWidth = Integer.parseInt(s.substring(i1, i2));
            // Reading map height
            String h = "height=\"";
            i1 = s.indexOf(h) + h.length();
            i2 = s.indexOf("\"", i1);
            mapHeight = Integer.parseInt(s.substring(i1, i2));
            // Reading tile resolution
            String tw = "tilewidth=\"";
            i1 = s.indexOf(tw) + tw.length();
            i2 = s.indexOf("\"", i1);
            int tileRes = Integer.parseInt(s.substring(i1, i2));
            // Setting tile size (effective size of a tile in px on the screen)
            tileSize = (int) (tileRes * SCALE);
            // Going to the first beginning of the first map (which has to be the collision map)
            while (!s.contains("encoding")) {
                s = br.readLine();
            }
            s = br.readLine();
            // Initializing the collision map
            collisionMap = new boolean[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++) {
                String[] numbers = s.split(",");
                for (int j = 0; j < numbers.length; j++) {
                    if (Integer.parseInt(numbers[j]) != 0) {
                        collisionMap[i][j] = true;
                    } else {
                        collisionMap[i][j] = false;
                    }
                }
                s = br.readLine();
            }
            
            // Going to the next map if there is one (if none then s is null)
            while(s != null && !s.contains("encoding")) {
                s = br.readLine();
            }
            s = br.readLine();
            
            mapLayers = new ArrayList<>();
            
            // Reading map data
            while(s != null && !s.contains("</map>")){ // reading all the map data
                // Initializing the map layers
                int[][] layer = new int[mapHeight][mapWidth];
                for(int i = 0 ; i < mapHeight ; i++) {
                    String[] numbers = s.split(",");
                    for(int j = 0; j < numbers.length ; j++) {
                        layer[i][j] = Integer.parseInt(numbers[j]);
                    }
                    s = br.readLine();
                }
                mapLayers.add(layer);
                
                // Going to the next map if there is one (if none then s is null)
                while(s != null && !s.contains("encoding")) {
                    s = br.readLine();
                }
                s = br.readLine();
            }
            br.close();
            
            // Initializing the tiles hashmap
            tiles = new HashMap<>();
            int mapW = mapImage.getWidth()/16;
            for(int[][] m : mapLayers) { // For every layer of the map
                for(int i = 0 ; i < m.length ; i++) {
                    for(int j = 0 ; j < m[i].length ; j++) {
                        if(m[i][j] != 0) {
                            int px = (m[i][j] - 1) % mapW;
                            int py = (m[i][j] - 1 ) / mapW;
                            px *= 16;
                            py *= 16;
                            BufferedImage tempImage = mapImage.getSubimage(px, py, tileRes, tileRes);
                            BufferedImage resizedImage = new BufferedImage(tileSize, tileSize, tempImage.getType());
                            Graphics g = resizedImage.getGraphics();
                            g.drawImage(tempImage, 0,0, tileSize, tileSize, null);
                            g.dispose();
                            tiles.put(m[i][j], resizedImage);
                        } else {
                            tiles.put(m[i][j], null);
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
        return collisionMap[row][col];
    }
    
    public int getMapWidth() {
        return mapWidth;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
}