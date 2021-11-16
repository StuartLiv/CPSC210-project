package ui;

import javax.swing.*;
import java.awt.*;

public class ConfigurePanel extends JPanel {
    private CardLayout layout;
    private JPanel commandPanel;
    private JPanel addField;

    public ConfigurePanel(GraphicalUI ui) {
        layout = new CardLayout();
        commandPanel(ui);
//        addfield();
//        editfield();
//        removefield();
    }

    public void setPanel(String name) {
        removeAll();
        repaint();
        revalidate();
        if (name.equals("command")) {
            add(commandPanel);
        }
        repaint();
        revalidate();
    }

    private void commandPanel(GraphicalUI ui) {
        JButton[] buttons = new JButton[5];
        commandPanel = new JPanel(new GridLayout(6,1));
        commandPanel.add(new JLabel("Select what you would like to do:", JLabel.CENTER));
        String[] b = new String[]{"Ingredients", "Recipes", "Meals", "Statistics", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new MenuButtonAction(b[i], ui));
            buttons[i].setSize(80, 80);
            commandPanel.add(buttons[i]);
        }

    }
}
