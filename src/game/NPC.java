package game;


public class NPC extends Entity {
    public DialogPanel dialogPanel;
    public NPC(double x, double y, String imagePath, String text){
        super(x,y, imagePath);
        this.dialogPanel = new DialogPanel(text);
    }
}
