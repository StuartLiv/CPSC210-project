package ui.panels;

import model.Ingredient;
import ui.GraphicalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShowIngredient extends JPanel {
    private static final String[] columnNames = { "Name", "Serving", "Calories", "Protein", "Carbs", "Fat"};
    private JTable table;
    private final GraphicalUI ui;
    private String[][] data;
    DefaultTableModel tableModel = new DefaultTableModel();

    // Constructor
    //MODIFIES: this
    //EFFECTS: Constructs addIngredientPanel
    public ShowIngredient(GraphicalUI ui) {
        super(new BorderLayout());
        this.ui = ui;

        updatePanel();

        table.setBounds(30, 40, 200, 500);
        JScrollPane sp = new JScrollPane(table);

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> ui.doCommand());
        add(new JLabel("These are the current known ingredients (units in grams):"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(quit, BorderLayout.SOUTH);
    }

    public void updatePanel() {
        ArrayList<Ingredient> ingredients = ui.getProfile().getIngredientList();
        data = new String[ingredients.size()][];
        for (int i = 0; i < ingredients.size(); i++) {
            data[i] = ingredients.get(i).getFields().toArray(new String[6]);
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
