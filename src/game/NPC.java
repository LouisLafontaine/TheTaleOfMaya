package game;


import java.util.ArrayList;

public class NPC extends Entity {
    ArrayList<String> dialogues = new ArrayList<>();
    public int dialogueno = 0;
    
    public NPC(double x, double y, String imagePath){
        super(x,y, imagePath);
        dialogues.add("Hello");
        dialogues.add("Maya! Te voilà enfin réveillée! Vite! il faut aller sauver ton petit frère.\nDes monstres l'ont capturé et se sont dirigés vers le nord! Tu peux utiliser\nton épée grâce au bouton ___");
    }

    public String speak(){
        return dialogues.get(dialogueno);
    }

}
