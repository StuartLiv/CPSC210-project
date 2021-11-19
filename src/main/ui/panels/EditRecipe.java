package ui.panels;

import model.Ingredient;
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

public class EditRecipe extends JPanel {
    private static final String[] columnNames = {"Ingredient", "Mass of Ingredient"};
    String[][] data;
    JTable table;
    String name = "";
    DefaultTableModel tableModel = new DefaultTableModel();
    private final GraphicalUI gui;

    //MODIFIES: this
    //EFFECTS: Constructs addRecipePanel
    public EditRecipe(GraphicalUI ui) {
        super(new BorderLayout());
        this.gui = ui;

        ArrayList<Portion> portions = new ArrayList<>();
        try {
            portions.add(new Portion(new Ingredient("Null", 0, 0, 0, 0, 0), 100));
            writeRecipe(new Recipe(portions, "Null"));
        } catch (Exception e) {
            //ignore
        }
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new IngredientBox(gui)));
        table.setBounds(30, 0, 200, 300);
        JScrollPane sp = new JScrollPane(table);

        add(new JLabel("Edit recipe portions in the table below"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(new OptionButtons(), BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: write current recipe to panel
    public void writeRecipe(Recipe recipe) {
        name = recipe.getName();
        ArrayList<Portion> portions = recipe.getIngredients();
        data = new String[portions.size()][];
        for (int i = 0; i < portions.size(); i++) {
            data[i] = new String[]{portions.get(i).getIngredient().getIngredientName(),
                    String.valueOf(portions.get(i).getMass())};
        }
        tableModel.setDataVector(data, columnNames);
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
        gui.getProfile().deleteRecipe(name);
    }
}
