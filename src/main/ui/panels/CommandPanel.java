package ui.panels;

import ui.GraphicalUI;

import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class CommandPanel extends JPanel {

    public CommandPanel(GraphicalUI ui) {
        super(new GridLayout(6,1));

        JButton[] buttons = new JButton[5];

        add(new JLabel("Select what you would like to do:", JLabel.CENTER));
        String[] b = new String[]{"Ingredients", "Recipes", "Meals", "Statistics", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new MenuButtonAction(b[i], ui));
            buttons[i].setSize(80, 80);
            add(buttons[i]);
        }
    }

    static class MenuButtonAction extends AbstractAction {
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
                ui.saveState();
                JFrame frame = ui.getFrame();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
            ui.processCommand(buttonName.toLowerCase(Locale.ROOT).substring(0, 1));
        }
    }
}
