package ui;

import model.Ingredient;
import model.Meal;
import model.Profile;
import model.Recipe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class GraphicalUI extends AbstractUI {
    private JFrame frame;
    private MainPanel panel;

    //EFFECTS: runs tracker
    //Close frame call used from Stack Overflow Comment:
    //Link: https://stackoverflow.com/a/1235994
    public GraphicalUI() {
        super();

    }

    @Override
    //MODIFIES: this
    //EFFECTS: runs the ui, and program
    protected void runUI() {
        logo();
        init();
        doCommand();
    }

    //EFFECTS: shows logo
    private void logo() {
        JFrame f = new JFrame("Macronutrient Tracker");
        f.add(new JLabel(new ImageIcon("./data/free-logo.png")));
        f.setSize(640, 360);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        try {
            sleep(3000);
        } catch (Exception ignore) {
            //ignore
        }
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }

    //MODIFIES: this
    //EFFECTS: initializes gui
    protected void init() {
        source = "./data/" + chooseProfile() + "/";
        try {
            profile = new Profile(source);
        } catch (IOException | RuntimeException e) {
            init();
        }
        frame = new JFrame("Macronutrient Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        panel = new MainPanel(this);
        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: uses pop up to get file source
    protected String chooseProfile() {
        return JOptionPane.showInputDialog("What user profile would you like to load?");
    }

    //EFFECTS: returns profile
    public Profile getProfile() {
        return profile;
    }

    //EFFECTS: returns frame
    public JFrame getFrame() {
        return frame;
    }

    //EFFECTS: saves data based on yes/no pop up
    public void saveState() {
        if (JOptionPane.showConfirmDialog(frame,
                "Would you like to save the application?", "Save application?", JOptionPane.YES_NO_OPTION) == 0) {
            try {
                saveData();
            } catch (IOException e) {
                System.out.println("Unexpected file name error, data could not be saved");
            }
        }
        printLog();
    }

    //EFFECTS: shows command window, and sets up button listeners
    public void doCommand() {
        frame.revalidate();
        frame.repaint();
        panel.setPanel("command");
    }

    //EFFECTS: shows pop up window for actions
    protected String getAction() {
        String[] possibleValues = {"Add", "Edit", "Delete", "Print"};
        int selectedValue = JOptionPane.showOptionDialog(frame, "Select an action", "Action Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, possibleValues, possibleValues[0]);
        return possibleValues[selectedValue].toLowerCase(Locale.ROOT).substring(0, 1);
    }

    //MODIFIES: this
    //EFFECTS: addIngredients entered by user as JTextFields
    protected void addIngredients() {
        panel.setPanel("add ingredient");
    }

    //MODIFIES: this
    //EFFECTS: removes selected ingredient
    protected void removeIngredient() {
        Object[] ingredients = profile.getIngredientList().stream()
                .map(Ingredient::getIngredientName).toArray();
        String toDelete = (String) JOptionPane.showInputDialog(frame, "Choose an ingredient to delete",
                "Remove Ingredient", JOptionPane.INFORMATION_MESSAGE, null, ingredients, ingredients[0]);
        profile.deleteIngredient(toDelete);
    }

    //MODIFIES: this
    //EFFECTS: edits selected ingredient
    protected void editIngredient() {
        Object[] ingredients = profile.getIngredientList().stream()
                .map(Ingredient::getIngredientName).toArray();
        String toEdit = (String) JOptionPane.showInputDialog(frame, "Choose an ingredient to edit",
                "Edit Ingredient", JOptionPane.INFORMATION_MESSAGE, null, ingredients, ingredients[0]);

        Object[][] rows = {profile.findIngredient(toEdit).getFields().toArray(new String[6])};
        String[] columnNames = { "Name", "Serving", "Calories", "Protein", "Carbs", "Fat"};
        DefaultTableModel table = new DefaultTableModel(rows, columnNames);
        JOptionPane.showMessageDialog(null, new JScrollPane(new JTable(table)));
        ArrayList<String> ingredientFields = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            ingredientFields.add((String) table.getValueAt(0, j));
        }
        try {
            profile.addIngredient(new Ingredient(ingredientFields.toArray(new String[6])));
            profile.deleteIngredient(toEdit);
        } catch (Exception e) {
            //ingredient not added
        }
    }

    //EFFECTS: shows all ingredients
    protected void showIngredients() {
        panel.setPanel("show ingredient");
    }

    //MODIFIES: this
    //EFFECTS: add entered recipe to recipe book
    protected void addRecipes() {
        panel.setPanel("add recipe");
    }

    //MODIFIES: this
    //EFFECTS: removes recipe from profile
    protected void removeRecipe() {
        Object[] recipes = profile.getRecipeBook().stream()
                .map(Recipe::getName).toArray();
        String toDelete = (String) JOptionPane.showInputDialog(frame, "Choose an recipe to delete",
                "Remove Recipe", JOptionPane.INFORMATION_MESSAGE, null, recipes, recipes[0]);
        profile.deleteRecipe(toDelete);
    }

    //MODIFIES: this
    //EFFECTS: edits recipes in profile
    protected void editRecipe() {
        panel.setPanel("edit recipe");
    }

    //MODIFIES: this
    //EFFECTS: shows recipes in profile
    protected void showRecipes() {
        panel.setPanel("show recipes");
    }

    //MODIFIES: this
    //EFFECTS: adds meal to profile
    protected void addMeals() {
        panel.setPanel("add meal");
    }

    //MODIFIES: this
    //EFFECTS: removes meal from profile
    protected void removeMeal() {
        Object[] meals = profile.getTracker().stream()
                .map(Meal::getName).toArray();
        String toDelete = (String) JOptionPane.showInputDialog(frame, "Choose an meal to delete",
                "Remove Meal", JOptionPane.INFORMATION_MESSAGE, null, meals, meals[0]);
        profile.deleteRecipe(toDelete);
    }

    //MODIFIES: this
    //EFFECTS: edits meals in profile
    protected void editMeal() {
        Object[] meals = profile.getTracker().stream()
                .map(Meal::getName).toArray();
        String toEdit = (String) JOptionPane.showInputDialog(frame, "Choose a meal to edit",
                "Edit Meal", JOptionPane.INFORMATION_MESSAGE, null, meals, meals[0]);
        Meal m = profile.findMeal(toEdit);
        Object[][] rows = {{m.getRecipe().getName(), m.getMass(), m.getDateString(), m.getTimeString()}};
        String[] columnNames = {"Recipe", "Serving Size", "Date: YYYY-MM-DD", "Time: HH:MM"};
        DefaultTableModel table = new DefaultTableModel(rows, columnNames);
        JOptionPane.showMessageDialog(null, new JScrollPane(new JTable(table)));

        try {
            profile.addMeal(new Meal(profile.findRecipe((String) table.getValueAt(0, 0)),
                    Integer.parseInt((String) table.getValueAt(0, 1)),
                    (String) table.getValueAt(0, 2), (String) table.getValueAt(0, 3)));
            profile.deleteMeal(m);
        } catch (Exception e) {
            //ingredient not added
        }
    }

    //MODIFIES: this
    //EFFECTS: shows meals in profile
    protected void showMeals() {
        panel.setPanel("show meal");
    }

    //EFFECTS: shows statistics about profile
    protected void doStats() {
        panel.setPanel("show stats");
    }
}
