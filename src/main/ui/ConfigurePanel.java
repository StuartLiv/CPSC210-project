package ui;

import javax.swing.*;
import java.awt.*;

public class ConfigurePanel {
    private CardLayout layout;

    public ConfigurePanel(GraphicalUI ui) {
        layout = new CardLayout();
        commandPanel(ui);
//        addfield();
//        editfield();
//        removefield();
    }

    public void setPanel(String name) {
        // TODO
    }

    private void commandPanel(GraphicalUI ui) {
        JButton[] buttons = new JButton[5];
        JPanel panel = new JPanel(new GridLayout(6,1));
        panel.add(new JLabel("Select what you would like to do:", JLabel.CENTER));
        String[] b = new String[]{"Ingredients", "Recipes", "Meals", "Statistics", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new ButtonAction(b[i], ui));
            buttons[i].setSize(80, 80);
            panel.add(buttons[i]);
        }
        //layout.addLayoutComponent();
    }
}
