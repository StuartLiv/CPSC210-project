package ui;

import ui.panels.AddIngredient;
import ui.panels.CommandPanel;
import ui.panels.ShowIngredient;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private final GraphicalUI ui;
    private Map<String, JPanel> panels;

    public MainPanel(GraphicalUI ui) {
        this.ui = ui;
        mapInit();
        setLayout(cardLayout);
        panelsInit();
    }

    //MODIFIES: this
    //EFFECTS: initialises hashmap of panel names and JPanels
    private void mapInit() {
        panels = new HashMap<>();
        panels.put("command", new CommandPanel(ui));
        panels.put("add ingredient", new AddIngredient(ui, new String[][]{{"", "", "", "", "", ""}}));
        panels.put("show ingredient", new ShowIngredient(ui));
    }

    //EFFECTS: add panels in map to MainPanel
    // Uses map iteration from GeeksForGeeks
    //Link: https://www.geeksforgeeks.org/iterate-map-java/
    private void panelsInit() {
        for (Map.Entry<String,JPanel> panel : panels.entrySet()) {
            add(panel.getKey(), panel.getValue());
        }
    }

    //EFFECTS: sets showing panel to one with given name
    //Card Layout Switching inspired by Stack Overflow answer
    //Link: https://stackoverflow.com/a/10823614
    public void setPanel(GraphicalUI ui, String name) {
        ui.maintainSorted();
        updatePanels();
        revalidate();
        repaint();
        cardLayout = (CardLayout) getLayout();
        cardLayout.show(this, name);
    }

    //EFFECTS: updates panels in, where appropriate
    private void updatePanels() {
        ((ShowIngredient) panels.get("show ingredient")).updatePanel();
    }
}
