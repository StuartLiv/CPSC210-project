package ui.panels;

import model.Meal;
import ui.GraphicalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShowMeal extends JPanel {
    private static final String[] columnNames = {"Name", "Mass", "Calories", "Protein", "Carbs", "Fat", "Date", "Time"};
    private JTable table;
    private final GraphicalUI ui;
    DefaultTableModel tableModel = new DefaultTableModel();

    // Constructor
    //MODIFIES: this
    //EFFECTS: Constructs showMealPanel
    public ShowMeal(GraphicalUI ui) {
        super(new BorderLayout());
        this.ui = ui;

        updatePanel();

        table.setBounds(30, 40, 200, 500);
        JScrollPane sp = new JScrollPane(table);

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> ui.doCommand());
        add(new JLabel("This is your current meal history (units in grams):"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(quit, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: updates panel to reflect current mealList
    public void updatePanel() {
        ArrayList<Meal> meals = ui.getProfile().getTracker();
        String[][] data = new String[meals.size()][];
        for (int i = 0; i < meals.size(); i++) {
            ArrayList<String> mealData = new ArrayList<>(meals.get(i).getTotal().getIngredient().getFields());
            mealData.add(meals.get(i).getDateString());
            mealData.add(meals.get(i).getTimeString());
            data[i] = mealData.toArray(new String[0]);
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
