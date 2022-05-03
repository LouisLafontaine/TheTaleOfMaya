package game;
import java.awt.*;

public class gui {
    public static String ins = "";
    public boolean display = true;
    public gui(){
        ins = "Utilisez les touches ZQSD ou les touches \ndirectionnelles pour vous déplacer \n\nAppuyez sur ENTREE étant en collision \navec un PNJ pour lui parler \n\nAppuyez sur ESPACE pour attaquer\n\nAppuyez sur E fermer cette fenêtre";
    }

    public static void draw(Graphics g, TileManager t){
        int tileSize = t.getTileSize();
        int x = tileSize;
        int y = 5*tileSize;
        int width = 6*tileSize;
        int height = 3*tileSize;

        Color c = new Color(64,108,228,175);;
        g.setColor(c);
        g.fillRoundRect(x,y,width,height,35,35);

        Color c2 = new Color(255,255,255);
        g.setColor(c2);
        g.drawRoundRect(x,y,width,height,35,35); // frame border

        x += 25;
        y += 30;

        g.setFont(g.getFont().deriveFont(Font.PLAIN,18));
        for(String line : ins.split("\n")){
            g.drawString(line,x,y); // text on dialogue frame
            y += 19;
        }
    }

}
