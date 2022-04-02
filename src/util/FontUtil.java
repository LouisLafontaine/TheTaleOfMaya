package util;

import java.awt.*;
import java.io.File;

public class FontUtil {
    
    public static Font getFrom(String fontPath, int size) {
        Font maFont;
        try {
            File fontFile = new File("resources/" + fontPath);
            maFont  = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return maFont.deriveFont(Font.PLAIN,(float)size);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "\nat \" resources/" + fontPath + "\"");
            return new Font("Arial", Font.BOLD, size);
        }
    }
}