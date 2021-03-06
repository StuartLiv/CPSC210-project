package ui;

import model.Ingredient;
import model.Recipe;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private final GraphicalUI ui;
    private Map<String, JPanel> panels;

    //MODIFIES: this
    //EFFECTS: initialize panel, with cardLayout
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

        panels.put("add recipe", new AddRecipe(ui, new String[][]{{"Select Ingredient",""}}));
        panels.put("edit recipe", new EditRecipe(ui));
        panels.put("show recipes", new ShowRecipe(ui));

        panels.put("add meal", new AddMeal(ui, new String[][]{{"Select Recipe", "", "", ""}}));
        panels.put("show meal", new ShowMeal(ui));

        panels.put("show stats", new ShowStats(ui));
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
    public void setPanel(String name) {
        updatePanels(name);
        cardLayout = (CardLayout) getLayout();
        cardLayout.show(this, name);
    }

    //MODIFIES: this
    //EFFECTS: updates panels, where appropriate
    private void updatePanels(String name) {
        ui.maintainSorted();
        ((ShowIngredient) panels.get("show ingredient")).updatePanel();
        ((AddRecipe) panels.get("add recipe")).updatePanel();
        ((ShowRecipe) panels.get("show recipes")).updatePanel();
        ((AddMeal) panels.get("add meal")).updatePanel();
        ((ShowMeal) panels.get("show meal")).updatePanel();
        ((ShowStats) panels.get("show stats")).updatePanel();
        if (name.equals("edit recipe")) {
            ((EditRecipe) panels.get("edit recipe")).writeRecipe(ui.getProfile().findRecipe((String)
                    JOptionPane.showInputDialog(ui.getFrame(), "Choose an ingredient to edit",
                    "Edit Ingredient", JOptionPane.INFORMATION_MESSAGE, null,
                        ui.profile.getRecipeBook().stream().map(Recipe::getName).toArray(), null)));
        }
        revalidate();
        repaint();
    }
}
