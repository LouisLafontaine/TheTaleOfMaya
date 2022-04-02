package game;
import javax.swing.*;

public class DialogPanel extends JFrame {
    public String text;
    private JLabel dialogLabel;
    public DialogPanel(String dialog){
        this.text = dialog;
        setSize(300,300);
        setVisible(false);

        dialogLabel = new JLabel(text);
        this.add(dialogLabel);

    }
}
