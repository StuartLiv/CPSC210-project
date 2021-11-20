package ui.panels;

import model.Portion;
import ui.GraphicalUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShowStats extends JPanel {
    private static final String[] columnNames = {"Date", "Mass", "Calories", "Protein", "Carbs", "Fat"};
    private JTable table;
    private final GraphicalUI ui;
    DefaultTableModel tableModel = new DefaultTableModel();

    // Constructor
    //MODIFIES: this
    //EFFECTS: Constructs showStatsPanel
    public ShowStats(GraphicalUI ui) {
        super(new BorderLayout());
        this.ui = ui;

        updatePanel();

        table.setBounds(30, 40, 200, 500);
        JScrollPane sp = new JScrollPane(table);

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> ui.doCommand());
        add(new JLabel("This is your current daily nutrition history (units in grams):"), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(quit, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: updates panel to reflect current ingredientList
    public void updatePanel() {
        ArrayList<Portion> dailyTotals = ui.getFormattedTotals();
        String[][] data = new String[dailyTotals.size()][];
        for (int i = 0; i < dailyTotals.size(); i++) {
            data[i] = dailyTotals.get(i).getIngredient().getFields().toArray(new String[0]);
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
