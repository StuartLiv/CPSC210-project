package ui.panels;

import model.Portion;
import model.Recipe;
import model.exceptions.InvalidInputException;
import model.exceptions.InvalidMassException;
import ui.GraphicalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddRecipe extends JPanel {
    private static final String[] columnNames = {"Ingredient", "Mass of Ingredient"};
    String[][] data;
    JTable table;
    DefaultTableModel tableModel;
    private final GraphicalUI gui;

    //MODIFIES: this
    //EFFECTS: Constructs addRecipePanel
    public AddRecipe(GraphicalUI ui, String[][] inputData) {
        super(new BorderLayout());
        this.gui = ui;

        data = inputData;

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        updatePanel();
        table.setBounds(30, 0, 200, 300);
        JScrollPane sp = new JScrollPane(table);

        add(new JLabel("Write recipe portions in the table below"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(new OptionButtons(), BorderLayout.SOUTH);
    }

    //EFFECTS: updates comboBoxes
    public void updatePanel() {
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new IngredientBox(gui)));
    }

    //Button Panel
    class OptionButtons extends JPanel {

        //EFFECTS: sets up button panel
        public OptionButtons() {
            super(new GridLayout(1,4));
            JButton add = new JButton("Add Row");
            add.addActionListener(new ButtonListener("Add"));
            JButton remove = new JButton("Remove Row");
            remove.addActionListener(new ButtonListener("Remove"));
            JButton done = new JButton("Done");
            done.addActionListener(new ButtonListener("Done"));
            JButton quit = new JButton("Quit");
            quit.addActionListener(new ButtonListener("Quit"));

            add(add);
            add(remove);
            add(done);
            add(quit);
        }
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
                    tableModel.addRow(new String[]{"Select Ingredient", ""});
                    break;
                case "Remove":
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                    break;
                case "Done":
                    saveTableData();
                case "Quit":
                    tableModel.setNumRows(0);
                    tableModel.addRow(new String[]{"Select Ingredient", ""});
                    gui.doCommand();
                    break;

            }
        }
    }

    //MODIFIES: this.ui.profile.recipeBook
    //EFFECTS: saves entered data to user profile
    private void saveTableData() {
        ArrayList<Portion> portions = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                portions.add(new Portion(gui.getProfile().findIngredient((String) tableModel.getValueAt(i, 0)),
                        Integer.parseInt((String) tableModel.getValueAt(i,1))));
            } catch (InvalidMassException e) {
                //portion is invalid, not added;
            }
        }
        try {
            gui.getProfile().addRecipe(new Recipe(portions,
                    JOptionPane.showInputDialog("What would you like to name the recipe?")));
        } catch (InvalidInputException e) {
            throw new RuntimeException();
        }
    }
}
