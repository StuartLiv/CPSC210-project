package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class MenuButtonAction extends AbstractAction {
    String buttonName;
    GraphicalUI ui;

    MenuButtonAction(String buttonName, GraphicalUI ui) {
        super(buttonName);
        this.buttonName = buttonName;
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (buttonName.equals("Quit")) {
            ui.setKeepGoingFalse();
        }
        ui.processCommand(buttonName.toLowerCase(Locale.ROOT).substring(0, 1));
    }
}
