package util;

import menu.MenuWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ReturnToMenu extends AbstractAction {
    JFrame f;
    public ReturnToMenu(JFrame f) {
        this.f = f;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        f.dispose();
        MainWindow.switchTo(MenuWindow.get().init());
    }
}