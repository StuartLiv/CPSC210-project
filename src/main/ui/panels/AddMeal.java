package ui.panels;

import model.Meal;
import model.exceptions.InvalidInputException;
import ui.GraphicalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMeal extends JPanel {
    private static final String[] columnNames = {"Recipe", "Serving Size", "Date: YYYY-MM-DD", "Time: HH:MM"};
    String[][] data;
    JTable table;
    DefaultTableModel tableModel;
    private final GraphicalUI gui;

    //MODIFIES: this
    //EFFECTS: Constructs addMealPanel
    public AddMeal(GraphicalUI ui, String[][] inputData) {
        super(new BorderLayout());
        this.gui = ui;

        data = inputData;

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        updatePanel();
        table.setBounds(30, 0, 200, 300);
        JScrollPane sp = new JScrollPane(table);

        add(new JLabel("Enter your meals:"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(new OptionButtons(), BorderLayout.SOUTH);

    }

    //EFFECTS: updates comboBoxes
    public void updatePanel() {
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new RecipeBox(gui)));
    }

    //Button panel
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
                    tableModel.addRow(new String[]{"Select Recipe", "", "", ""});
                    break;
                case "Remove":
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                    break;
                case "Done":
                    saveTableData();
                case "Quit":
                    tableModel.setNumRows(0);
                    tableModel.addRow(new String[]{"Select Recipe", "", "", ""});
                    gui.doCommand();
                    break;

            }
        }
    }


    //MODIFIES: this.ui.profile.tracker
    //EFFECTS: saves entered data to user profile
    private void saveTableData() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                gui.getProfile().addMeal(new Meal(gui.getProfile().findRecipe((String) tableModel.getValueAt(i, 0)),
                                Integer.parseInt((String) tableModel.getValueAt(i, 1)),
                                (String) tableModel.getValueAt(i, 2), (String) tableModel.getValueAt(i, 3)));
            } catch (InvalidInputException e) {
                System.out.println("yo");
                //meal is invalid, not added;
            }
        }
    }
}
