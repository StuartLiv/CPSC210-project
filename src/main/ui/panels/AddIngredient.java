package ui.panels;

import model.Ingredient;
import ui.GraphicalUI;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Add ingredient panel for gui
//JTable structure inspried by GeeksForGeeks JTable tutorial:
//Link: https://www.geeksforgeeks.org/java-swing-jtable/?ref=lbp
public class AddIngredient extends JPanel {
    private static final String[] columnNames = { "Name", "Serving", "Calories", "Protein", "Carbs", "Fat"};
    String[][] data;
    JTable table;
    DefaultTableModel tableModel;
    private final JPanel addIngredientPanel;
    private final GraphicalUI ui;

    // Constructor
    //MODIFIES: this
    //EFFECTS: Constructs addIngredientPanel
    public AddIngredient(String[][] inputData, GraphicalUI ui) {
        this.ui = ui;
        addIngredientPanel = new JPanel(new BorderLayout());

        data = inputData;

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        JButton done = new JButton("Done");
        done.addActionListener(new ButtonListener("Done"));
        JButton quit = new JButton("Quit");
        quit.addActionListener(new ButtonListener("Quit"));
        JButton add = new JButton("Add Ingredient");
        add.addActionListener(new ButtonListener("Add Ingredient"));

        buttonPanel.add(done);
        buttonPanel.add(quit);
        buttonPanel.add(add);
        addIngredientPanel.add(sp, BorderLayout.CENTER);
        addIngredientPanel.add(buttonPanel, BorderLayout.PAGE_END);
    }

    // Temp Driver  method
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Add Ingredient");
        String[][] data = new String[][]{{"", "", "", "", "", ""}};
        frame.add(new AddIngredient(data, null).addIngredientPanel);
        frame.setSize(500, 200);
        frame.setVisible(true);
    }

    //Listener for AddIngredient buttons
    class ButtonListener implements ActionListener {
        String name;

        //MODIFIES: this
        public ButtonListener(String name) {
            this.name = name;
        }

        @Override
        //EFFECT: performs button action from clikced button
        public void actionPerformed(ActionEvent e) {
            switch (name) {
                case "Add Ingredient":
                    tableModel.addRow(new String[]{"", "", "", "", "", ""});
                    System.out.println("Add");
                    break;
                case "Done":
                    saveTableData();
                    System.out.println("Done");
                case "Quit":
                    data = new String[][]{{"", "", "", "", "", ""}};
                    System.out.println("Quit");
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
