package ui.panels;

import model.Recipe;
import model.exceptions.InvalidInputException;
import ui.GraphicalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShowRecipe extends JPanel {
    private static final String[] columnNames = { "Name", "Mass", "Calories", "Protein", "Carbs", "Fat"};
    private JTable table;
    private final GraphicalUI ui;
    DefaultTableModel tableModel = new DefaultTableModel();

    // Constructor
    //MODIFIES: this
    //EFFECTS: Constructs showRecipePanel
    public ShowRecipe(GraphicalUI ui) {
        super(new BorderLayout());
        this.ui = ui;

        updatePanel();

        table.setBounds(30, 40, 200, 500);
        JScrollPane sp = new JScrollPane(table);

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> ui.doCommand());
        add(new JLabel("These are the current saved recipes (units in grams):"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(quit, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: updates panel to reflect current recipeBook
    public void updatePanel() {
        ArrayList<Recipe> recipes = ui.getProfile().getRecipeBook();
        String[][] data = new String[recipes.size()][];
        for (int i = 0; i < recipes.size(); i++) {
            try {
                data[i] = recipes.get(i).getTotal().getIngredient().getFields().toArray(new String[6]);
            } catch (InvalidInputException e) {
                throw new RuntimeException();
            }
        }
        tableModel.setDataVector(data, columnNames);
        table = new JTable(tableModel) {
            @Override
            //Method from Stack Overflow
            //Link: https://stackoverflow.com/a/3134006
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
