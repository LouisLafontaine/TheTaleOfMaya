package game;


public class NPC extends Entity {
    
    public DialogueLabel dialogueLabel;
    
    public NPC(double x, double y, String imagePath, String text){
        super(x,y, imagePath);
        this.dialogueLabel = new DialogueLabel(text);
    }
}
