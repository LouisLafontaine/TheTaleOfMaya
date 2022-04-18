package game;


import util.FontUtil;

import java.awt.*;
import java.util.ArrayList;

public class NPC extends Entity {
    ArrayList<String> dialogues = new ArrayList<>(); // array containing every dialogue for an NPC
    public int dialogueNum = 0; // index of the dialogue to load

    public String speaking = ""; // current loaded dialogue
    
    public NPC(double x, double y, String imagePath){
        super(x,y, imagePath);
        dialogues.add("Hello");
        dialogues.add("Maya! Te voilà enfin réveillée! Vite! il faut aller sauver ton petit frère.\nDes monstres l'ont capturé et se sont dirigés vers le nord! Tu peux utiliser\nton épée grâce au bouton ___");
    }

    public void speak(){
        this.speaking = dialogues.get(dialogueNum);
    }

    public void drawDialogueBox(Graphics g, TileManager t) {
        int tileSize = t.getTileSize();
        int x = tileSize*2;
        int y = tileSize;
        int width = tileSize*18 ;
        int height = tileSize*3;
        Color c = new Color(64,108,228,175);
        g.setColor(c);
        g.fillRoundRect(x,y,width,height,35,35); // dialogue frame

        Color c2 = new Color(255,255,255);
        g.setColor(c2);
        g.drawRoundRect(x,y,width,height,35,35); // frame border

        x += tileSize;
        y += tileSize;
        FontUtil font;
        // g.setFont(FontUtil.getFrom("resources/fonts/pixelFont.otf",30));
        g.setFont(g.getFont().deriveFont(Font.PLAIN,30));
        for(String line : this.speaking.split("\n")){
            g.drawString(line,x,y); // text on dialogue frame
            y += 40;
        }
    }

}
