package ui.panels;

import model.Ingredient;
import ui.GraphicalUI;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Add ingredient panel for gui
//JTable structure inspired by GeeksForGeeks JTable tutorial:
//Link: https://www.geeksforgeeks.org/java-swing-jtable/?ref=lbp
public class AddIngredient extends JPanel {
    private static final String[] columnNames = { "Name", "Serving", "Calories", "Protein", "Carbs", "Fat"};
    String[][] data;
    JTable table;
    DefaultTableModel tableModel;
    private final GraphicalUI ui;

    //MODIFIES: this
    //EFFECTS: Constructs addIngredientPanel
    public AddIngredient(GraphicalUI ui, String[][] inputData) {
        super(new BorderLayout());
        this.ui = ui;

        data = inputData;

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setBounds(30, 0, 200, 300);
        JScrollPane sp = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new GridLayout(1,4));
        JButton add = new JButton("Add Row");
        add.addActionListener(new ButtonListener("Add"));
        JButton remove = new JButton("Remove Row");
        remove.addActionListener(new ButtonListener("Remove"));
        JButton done = new JButton("Done");
        done.addActionListener(new ButtonListener("Done"));
        JButton quit = new JButton("Quit");
        quit.addActionListener(new ButtonListener("Quit"));

        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(done);
        buttonPanel.add(quit);
        add(new JLabel("Write new ingredient declarations in the table below"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //Listener for AddIngredient buttons
    class ButtonListener implements ActionListener {
        String name;

        //MODIFIES: this
        public ButtonListener(String name) {
            this.name = name;
        }

        @Override
        //EFFECT: performs button action from clicked button
        public void actionPerformed(ActionEvent e) {
            switch (name) {
                case "Add":
                    tableModel.addRow(new String[]{"", "", "", "", "", ""});
                    break;
                case "Remove":
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                    break;
                case "Done":
                    saveTableData();
                case "Quit":
                    tableModel.setNumRows(0);
                    tableModel.addRow(new String[]{"", "", "", "", "", ""});
                    ui.doCommand();
                    break;

            }
        }
    }

    //MODIFIES: this.ui.profile.ingredientList
    //EFFECTS: saves entered data to user profile
    private void saveTableData() {
        ArrayList<String> ingredientFields;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            ingredientFields = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                ingredientFields.add((String) tableModel.getValueAt(i, j));
            }
            try {
                ui.getProfile().addIngredient(new Ingredient(ingredientFields.toArray(new String[6])));
            } catch (Exception ignore) {
                //Ingredient not added
            }
        }
    }
}
